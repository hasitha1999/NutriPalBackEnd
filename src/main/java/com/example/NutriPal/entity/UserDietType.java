package com.example.NutriPal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDietType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userDietTypeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "diet_id")
    private DietType dietType;

    private LocalDate startDate;
    private LocalDate endDate;

}
