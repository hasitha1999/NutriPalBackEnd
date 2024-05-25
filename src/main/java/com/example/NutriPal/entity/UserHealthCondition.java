package com.example.NutriPal.entity;

import jakarta.persistence.*;
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
public class UserHealthCondition {

    @ManyToMany
    @JoinTable(
            name = "user_healthCondition",
            joinColumns = @JoinColumn(name = "health_condition_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> userSet;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userHealthConditionId;
}
