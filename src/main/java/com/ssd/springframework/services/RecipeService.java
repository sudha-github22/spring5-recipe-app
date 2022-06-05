package com.ssd.springframework.services;

import com.ssd.springframework.domain.Recipe;
import java.util.Set;

public interface RecipeService {
    public Set<Recipe> getRecipes();
}
