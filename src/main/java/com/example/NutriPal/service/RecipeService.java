package com.example.NutriPal.service;

import com.example.NutriPal.dto.RecipeWishListDTO;
import com.example.NutriPal.entity.RecipeSaved;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.RecipeSavedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeSavedRepository recipeSavedRepository;
    public RecipeSaved saveRecipe(User user, RecipeWishListDTO recipeWishListDTO){

        RecipeSaved recipeSaved = new RecipeSaved();
//        recipeSaved.setDescription(recipeWishListDTO.getDescription());
        recipeSaved.setUser(user);
        recipeSaved.setRecipeURI(recipeWishListDTO.getRecipieURI());
        return recipeSavedRepository.saveAndFlush(recipeSaved);

    }
    public ArrayList<RecipeWishListDTO> allSavedRecipes(User user){
        Optional<ArrayList<RecipeSaved>> recipeSavedList = recipeSavedRepository.findAllByUser(user);
        ArrayList<RecipeWishListDTO> recipeSavedArrayList = new ArrayList<>();
        if(recipeSavedList.isPresent()){
            for (RecipeSaved recipeSaved:recipeSavedList.get()) {
                recipeSavedArrayList.add(new RecipeWishListDTO(recipeSaved));
            }
        }
        return recipeSavedArrayList;

    }

}
