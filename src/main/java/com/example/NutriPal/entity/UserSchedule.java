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
public class UserSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userScheduleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer assignBy;
}
