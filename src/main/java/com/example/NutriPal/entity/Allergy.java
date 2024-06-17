package com.example.NutriPal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long allergyId;

    private String allergyName;

//    @ManyToMany
//    private Set<User> user;

    public Allergy(String allergyName) {
        this.allergyName = allergyName;
    }
}
