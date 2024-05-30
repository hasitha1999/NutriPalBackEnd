package com.example.NutriPal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HealthCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long conditionId;

    private String conditionName;

    @ManyToMany
    Set<User> users;
}
