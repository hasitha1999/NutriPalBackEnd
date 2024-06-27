package com.example.NutriPal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWaterLogDto {

    private double waterAmount;
    private boolean isArchived;
    private String dayOfWeek;
}
