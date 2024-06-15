package com.example.NutriPal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecipeSaved {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recipeSavedId;
    private String recipeURI;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



}
