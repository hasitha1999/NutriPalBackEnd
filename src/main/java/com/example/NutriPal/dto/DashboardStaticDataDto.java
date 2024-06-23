package com.example.NutriPal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStaticDataDto {

    private double tdee;
    private double bmr;
    private double waterIntake;
    private double totalCalorie;
    private double bmi;


}

