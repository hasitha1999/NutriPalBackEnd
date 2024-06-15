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

    @ManyToOne
    @JoinColumn(name = "health_Condition_Type_Id", nullable = false)
    private HealthConditionType healthConditionType;

//    @ManyToMany
//    Set<User> user;
}
