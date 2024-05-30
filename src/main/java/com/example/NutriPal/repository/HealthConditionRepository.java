package com.example.NutriPal.repository;

import com.example.NutriPal.entity.HealthCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthConditionRepository extends JpaRepository<HealthCondition,Long> {
}
