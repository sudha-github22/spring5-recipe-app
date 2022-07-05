package com.ssd.springframework.controllers;

import com.ssd.springframework.commands.IngredientCommand;
import com.ssd.springframework.commands.RecipeCommand;
import com.ssd.springframework.commands.UnitOfMeasureCommand;
import com.ssd.springframework.services.IngredientService;
import com.ssd.springframework.services.RecipeService;
import com.ssd.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService){
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Listing Ingredients for the recipeId : "+ recipeId);

        //use Command Object to avoid lazy loads while using Thymeleaf
        model.addAttribute("recipe",recipeService.findRecipeCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }



    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){
        log.debug("updating existing ingredient for the recipeId :"+recipeId);

        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                Long.valueOf(id)));
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredientCommand =  ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("Saved Recipe Id :" +savedIngredientCommand.getRecipeId());
        log.debug("Saved Ingredient Id :" +savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" +savedIngredientCommand.getId() +"/show";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String createNewIngredient(@PathVariable String recipeId, Model model){

        //To make sure we have  good id value
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));

        //Need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);
        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());

        return "/recipe/ingredient/ingredientForm";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){

        log.debug("Deleting ingredient id:"+id);

        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
        return "redirect:/recipe/"+ recipeId + "/ingredients";

    }
}
