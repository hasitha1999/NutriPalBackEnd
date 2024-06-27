package com.example.NutriPal.controller;

import com.example.NutriPal.dto.DailyLogDto;
import com.example.NutriPal.dto.DailyLogEatDto;
import com.example.NutriPal.dto.NutritionDetailsRequest;
import com.example.NutriPal.dto.RecipeWishListDTO;
import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.RecipeSaved;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.service.APIService;
import com.example.NutriPal.service.DailyLogService;
import com.example.NutriPal.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {
    private final APIService apiService;
    private final RecipeService recipeService;
    private final DailyLogService dailyLogService;

    @PostMapping("/getNutritionDetails")
    public ResponseEntity<Map> getNutritionDetailsApi(@RequestBody NutritionDetailsRequest nutritionDetailsRequest) {
        try {
            return ResponseEntity.ok(apiService.getNutritionDetails(nutritionDetailsRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/save")
    public ResponseEntity<RecipeSaved> saveRecipe(Authentication authentication, @RequestBody RecipeWishListDTO recipeWishListDTO) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(recipeService.saveRecipe(user,recipeWishListDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/markAsEat")
    public ResponseEntity<?> saveAsEat(Authentication authentication, @RequestBody DailyLogEatDto dailyLogEatDto) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(dailyLogService.createDailyEatLog(user,dailyLogEatDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/removeRecipe")
    public ResponseEntity<RecipeSaved> removeRecipe(@RequestBody RecipeWishListDTO recipeWishListDTO) {
        try {
            recipeService.removeFav(recipeWishListDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/getAllSavedRecipes")
    public ResponseEntity<ArrayList<HashMap<String,RecipeWishListDTO>>> saveRecipe(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(recipeService.allSavedRecipes(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
