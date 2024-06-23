package com.example.NutriPal.dto;

import com.example.NutriPal.entity.RecipeSaved;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeWishListDTO {
    private Long recipeId;
    private String image;
    private String title;
    private String itemData;

    public RecipeWishListDTO(RecipeSaved recipeSaved) {
        this.recipeId = recipeSaved.getRecipeId();
        this.image = recipeSaved.getImage();
        this.itemData = recipeSaved.getItemData();
        this.title = recipeSaved.getTitle();
    }
}
