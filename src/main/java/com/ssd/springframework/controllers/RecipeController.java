package com.ssd.springframework.controllers;

import com.ssd.springframework.commands.RecipeCommand;
import com.ssd.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe",recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());

        return "recipe/recipeForm";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model){
        model.addAttribute("recipe",recipeService.findRecipeCommandById(Long.valueOf(id)));

        return "recipe/recipeForm";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteAction(@PathVariable Long id){
        log.debug("deleting Id :"+id);

        recipeService.deleteById(id);
        return "redirect:/";
    }
}
