package com.example.NutriPal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeWishListDTO {
    private String recipeId;
    private String recipeName;
    private String description;
    private String imageUrl;
}
