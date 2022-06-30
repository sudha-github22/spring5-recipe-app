package com.ssd.springframework.services;

import com.ssd.springframework.commands.RecipeCommand;
import com.ssd.springframework.converters.RecipeCommandToRecipe;
import com.ssd.springframework.converters.RecipeToRecipeCommand;
import com.ssd.springframework.domain.Recipe;
import com.ssd.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        log.debug("I'm in the service");
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
       Optional<Recipe> recipeOptional = recipeRepository.findById(id);

       if(!recipeOptional.isPresent()){
           throw new RuntimeException("Recipe Not Found!");
       }
        return recipeOptional.get();
    }


    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe =  recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:"+savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findRecipeCommandById(Long id) {
        Recipe recipe = findById(id);
        RecipeCommand recipeCommand =  recipeToRecipeCommand.convert(recipe);
        return recipeCommand;
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete) {
        log.debug("Deleting Id:"+idToDelete);
        recipeRepository.deleteById(idToDelete);
    }
}
