package com.example.NutriPal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyLogDto {
    private Long logId;
    private String logType;
    private double userInput;

}
