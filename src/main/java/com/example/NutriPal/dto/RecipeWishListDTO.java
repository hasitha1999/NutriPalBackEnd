package com.example.NutriPal.dto;

import com.example.NutriPal.entity.RecipeSaved;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeWishListDTO {
    private String recipieURI;

    public RecipeWishListDTO(RecipeSaved recipeSaved) {
        this.recipieURI = recipeSaved.getRecipeURI();
    }
}
