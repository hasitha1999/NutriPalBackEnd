package com.example.NutriPal.controller;

import com.example.NutriPal.dto.NutritionDetailsRequest;
import com.example.NutriPal.service.APIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {
    private final APIService apiService;

    @PostMapping("/getNutritionDetails")
    public ResponseEntity<Map> getNutritionDetailsApi(@RequestBody NutritionDetailsRequest nutritionDetailsRequest) {
        try {
            return ResponseEntity.ok(apiService.getNutritionDetails(nutritionDetailsRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
