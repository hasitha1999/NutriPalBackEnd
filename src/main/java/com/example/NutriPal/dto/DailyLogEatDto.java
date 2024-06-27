package com.example.NutriPal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyLogEatDto {
    private double fat;
    private double protein;
    private double carbs;
    private double calorie;
    private LocalDate date;
}
