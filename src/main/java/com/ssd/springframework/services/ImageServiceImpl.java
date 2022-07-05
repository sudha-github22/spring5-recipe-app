package com.ssd.springframework.services;

import com.ssd.springframework.domain.Recipe;
import com.ssd.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveImageFile(Long recipeId, MultipartFile file)  {

        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            Recipe recipe = recipeOptional.get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i=0;

            for(Byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
        }catch (IOException e){
            log.error("Error occurred"+ e);
            e.printStackTrace();
        }
    }
}
