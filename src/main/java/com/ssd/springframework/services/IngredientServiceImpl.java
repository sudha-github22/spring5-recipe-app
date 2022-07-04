package com.ssd.springframework.services;

import com.ssd.springframework.commands.IngredientCommand;
import com.ssd.springframework.converters.IngredientCommandToIngredient;
import com.ssd.springframework.converters.IngredientToIngredientCommand;
import com.ssd.springframework.domain.Ingredient;
import com.ssd.springframework.domain.Recipe;
import com.ssd.springframework.repositories.RecipeRepository;
import com.ssd.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient){

        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            //Todo impl for error handling
            log.error("Recipe Id Not Found" + recipeId);
        }
        Recipe recipe =  recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
               .filter(ingredient -> ingredient.getId().equals(ingredientId))
               .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            //Todo impl for error handling
            log.error("Ingredient Id Not Found" + ingredientId);
        }
        IngredientCommand ingredientCommand = ingredientCommandOptional.get();

        return ingredientCommand;
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Transactional
    @Override
    public void deleteById(Long recipeId, Long idToDelete) {
        log.debug("Deleting ingredient id" + idToDelete);

        Optional<Recipe> recipeOptional = recipeRepository.findById(Long.valueOf(recipeId));

        if(recipeOptional.isPresent()){
            log.debug("Recipe found!");

            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("Ingredient found!");

                Ingredient ingredient = ingredientOptional.get();
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);

                recipeRepository.save(recipe);

            }else{
                log.debug("Ingredient Id Not Found :" + idToDelete);
            }
        }else{
            log.debug("Recipe Id Not Found :" + recipeId);
        }
    }
}
