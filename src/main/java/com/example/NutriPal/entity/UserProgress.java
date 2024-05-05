package com.example.NutriPal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userProgressId;

    private LocalDate logDate;

    private double waterIntake;
    private double caloriesIntake;
    private double weightRecord;
    private double workoutTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
