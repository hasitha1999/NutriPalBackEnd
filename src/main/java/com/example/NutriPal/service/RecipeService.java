package com.example.NutriPal.service;

import com.example.NutriPal.dto.RecipeWishListDTO;
import com.example.NutriPal.entity.RecipeSaved;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.RecipeSavedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeSavedRepository recipeSavedRepository;
    public RecipeSaved saveRecipe(User user, RecipeWishListDTO recipeWishListDTO){

        RecipeSaved recipeSaved = new RecipeSaved();
        recipeSaved.setUser(user);
        recipeSaved.setImage(recipeWishListDTO.getImage());
        recipeSaved.setTitle(recipeWishListDTO.getTitle());
        recipeSaved.setItemData(recipeWishListDTO.getItemData());
        return recipeSavedRepository.saveAndFlush(recipeSaved);

    }
    public ArrayList<HashMap<String,RecipeWishListDTO>> allSavedRecipes(User user){
        Optional<ArrayList<RecipeSaved>> recipeSavedList = recipeSavedRepository.findAllByUser(user);
        ArrayList<HashMap<String,RecipeWishListDTO>> recipeSavedArrayList = new ArrayList<>();
        if(recipeSavedList.isPresent()){
            for (RecipeSaved recipeSaved:recipeSavedList.get()) {
                HashMap<String,RecipeWishListDTO> recipeMap = new HashMap<>();
                recipeMap.put("recipe",new RecipeWishListDTO(recipeSaved));
                recipeSavedArrayList.add(recipeMap);
            }
        }
        return recipeSavedArrayList;

    }
    public void removeFav( RecipeWishListDTO recipeWishListDTO){
        RecipeSaved recipeSaved = recipeSavedRepository.findById(recipeWishListDTO.getRecipeId()).get();
        recipeSavedRepository.delete(recipeSaved);

    }

}
