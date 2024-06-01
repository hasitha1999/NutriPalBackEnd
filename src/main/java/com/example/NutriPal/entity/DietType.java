package com.example.NutriPal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dietId;

    @NotBlank(message = "Diet name is required")
    private String dietName;

    @OneToMany(mappedBy = "dietType")
    Set<UserDietType> userDietTypes;
}
