package com.ssd.springframework.services;

import com.ssd.springframework.commands.RecipeCommand;
import com.ssd.springframework.domain.Recipe;
import java.util.Set;

public interface RecipeService {
    public Set<Recipe> getRecipes();

    public Recipe findById(Long id);

    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    public RecipeCommand findRecipeCommandById(Long id);
}
