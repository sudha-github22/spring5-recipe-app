package com.ssd.springframework.services;

import com.ssd.springframework.commands.IngredientCommand;
import com.ssd.springframework.converters.IngredientToIngredientCommand;
import com.ssd.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.ssd.springframework.domain.Ingredient;
import com.ssd.springframework.domain.Recipe;
import com.ssd.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class IngredientServiceImplTest {



     private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository,ingredientToIngredientCommand);
    }

    @Test
    public void testFindByRecipeIdAndIngredientId() throws Exception{
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
       IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L,3L);

        //then
       assertEquals(Long.valueOf(1L),ingredientCommand.getRecipeId());
       assertEquals(Long.valueOf(3L),ingredientCommand.getId());
       verify(recipeRepository,times(1)).findById(anyLong());
    }
}