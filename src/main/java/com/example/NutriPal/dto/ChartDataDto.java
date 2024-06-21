package com.example.NutriPal.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChartDataDto {

    private double value;
    private String logType;

}
