package com.example.NutriPal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HealthConditionType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long healthConditionTypeId;

    @NotBlank(message = "Health condition type is required")
    private String type;

}
