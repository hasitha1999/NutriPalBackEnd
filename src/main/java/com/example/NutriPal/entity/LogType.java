package com.example.NutriPal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class LogType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logTypeId;
    @NotBlank(message = "Log Type is required")
    private String type;


}
