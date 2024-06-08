package com.example.NutriPal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyLogDto {
    private Long logId;
    private String logType;
    private double userInput;
    private double weight;
    private String gender;
    private String dob;

}
