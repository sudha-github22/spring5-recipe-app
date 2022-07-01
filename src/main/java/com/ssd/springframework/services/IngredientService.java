package com.ssd.springframework.services;

import com.ssd.springframework.commands.IngredientCommand;

public interface IngredientService {

    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

}
