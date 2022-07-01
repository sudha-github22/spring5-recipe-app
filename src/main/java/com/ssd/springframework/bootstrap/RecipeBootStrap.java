package com.ssd.springframework.bootstrap;

import com.ssd.springframework.domain.*;
import com.ssd.springframework.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootStrap implements ApplicationListener<ContextRefreshedEvent> {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootStrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository,UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Loading Bootstrap data..");
        recipeRepository.saveAll(getRecipes());
    }
    private List<Recipe> getRecipes() {
        List<Recipe> recipeList = new ArrayList<>(2);

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if(!cupUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pinchUomOptional = unitOfMeasureRepository.findByDescription("Pinch");
        if(!pinchUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> ounceUomOptional = unitOfMeasureRepository.findByDescription("Ounce");
        if(!ounceUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure ounceUom = ounceUomOptional.get();
        UnitOfMeasure pinchUom = pinchUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        if(!italianCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> fastFoodCategoryOptional = categoryRepository.findByDescription("Fast Food");
        if(!fastFoodCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        //get Category Optionals
        Category americanCategory = americanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category fastFoodCategory = fastFoodCategoryOptional.get();

        //Yummy Guacamole
        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setCookTime(10);
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("1. Cut the avocado: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. " +
                " (See How to Cut and Peel an Avocado.)  Place in a bowl." +
                "\n"+
                "2. Mash the avocado flesh: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n"+
                "3.Add the remaining ingredients to taste:\n" +
                        "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                        "\n" +
                        "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                        "\n" +
                        "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste."+
                "\n"+
                "4.Serve immediately:\n" +
                        "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                        "\n" +
                        "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                        "\n" +
                        "Refrigerate leftover guacamole up to 3 days.\n" +
                        "\n" +
                        "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");


        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");

        //Using setters for JPA bidirectional relationship

//        guacamoleNotes.setRecipe(guacamoleRecipe);

        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.setSource("www.simplyrecipes.com");
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setServings(4);

        //very redundant, use only one method
//        guacamoleRecipe.addIngredient().add(new Ingredient("ripe avocados",new BigDecimal(2),eachUom,guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados",new BigDecimal(2),eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("kosher salt, plus more to taste",new BigDecimal(1),teaSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("fresh lime or lemon juice",new BigDecimal(1),tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion",new BigDecimal(4),tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("serrano (or jalapeño) chilis, stems and seeds removed, minced",new BigDecimal(2),eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped",new BigDecimal(2),tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("Pinch freshly ground black pepper",new BigDecimal(2),dashUom));
        guacamoleRecipe.addIngredient(new Ingredient(" 1/2 ripe tomato, chopped",new BigDecimal(1),eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("Red radish or jicama slices for garnish",new BigDecimal(1),eachUom));

        guacamoleRecipe.getCategories().add(americanCategory);
        guacamoleRecipe.getCategories().add(mexicanCategory);

        //add to return list
        recipeList.add(guacamoleRecipe);

        //Yummy Spicy Grilled Chicken Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacosRecipe.setCookTime(15);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections("1.Prepare a gas or charcoal grill for medium-high, direct heat" +
                "\n"+
                "2.Make the marinade and coat the chicken:\n" +
                        "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                        "\n" +
                        "Set aside to marinate while the grill heats and you prepare the rest of the toppings."+
                "\n"+
                "3.Grill the chicken:\n" +
                        "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes."+
                "\n"+
                "4.Warm the tortillas:\n" +
                "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving."+
                "\n"+
                "5.Assemble the tacos:\n" +
                "Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
//        tacosNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacosNotes);

        tacosRecipe.setSource("www.simplyrecipes.com");
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(6);

        tacosRecipe.addIngredient(new Ingredient("ancho chili powder",new BigDecimal(2),tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("dried oregano",new BigDecimal(1),teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("dried cumin",new BigDecimal(1),teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("sugar",new BigDecimal(1),teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("salt",new BigDecimal(1),teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("clove garlic, finely chopped",new BigDecimal(1),eachUom));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zest",new BigDecimal(1),tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice",new BigDecimal(3),tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("olive oil",new BigDecimal(2),tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs (1 1/4 pounds)",new BigDecimal(5),eachUom));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillas",new BigDecimal(8),eachUom));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula",new BigDecimal(3),ounceUom));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, sliced",new BigDecimal(2),eachUom));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced",new BigDecimal(4),eachUom));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved",new BigDecimal(1),pintUom));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced",new BigDecimal(1),eachUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro",new BigDecimal(1),eachUom));
        tacosRecipe.addIngredient(new Ingredient("1/2 sour cream thinned with 1/4 cup milk",new BigDecimal(1),cupUom));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges",new BigDecimal(1),eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        //add to return list
        recipeList.add(tacosRecipe);

        return recipeList;

    }
}
