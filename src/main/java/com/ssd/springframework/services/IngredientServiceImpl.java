package com.ssd.springframework.services;

import com.ssd.springframework.commands.IngredientCommand;
import com.ssd.springframework.converters.IngredientToIngredientCommand;
import com.ssd.springframework.domain.Ingredient;
import com.ssd.springframework.domain.Recipe;
import com.ssd.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand){
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
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
}
