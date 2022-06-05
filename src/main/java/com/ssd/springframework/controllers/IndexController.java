package com.ssd.springframework.controllers;

import com.ssd.springframework.domain.Category;
import com.ssd.springframework.domain.UnitOfMeasure;
import com.ssd.springframework.repositories.CategoryRepository;
import com.ssd.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        Optional<Category> optionalCategory = categoryRepository.findByDescription("Italian");
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription("Pinch");

        System.out.println("Category ID is :"+optionalCategory.get().getId());
        System.out.println("Unit Of Measure ID is :"+optionalUnitOfMeasure.get().getId());

        return "index";
    }
}
