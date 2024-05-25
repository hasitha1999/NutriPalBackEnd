package com.example.NutriPal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserSchedule {
    @ManyToMany
    @JoinTable(
            name = "user_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> userSet;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userScheduleId;

    @NotBlank(message = "Start date is required")
    private LocalDate startDate;

    @NotBlank(message = "End date is required")
    private LocalDate endDate;

    @NotBlank(message = "Assign By is required")
    private Integer assignBy;
}
