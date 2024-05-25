package com.example.NutriPal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HealthCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long healthConditionId;

    @NotBlank(message = "Health condition is required")
    private String condition;

}
