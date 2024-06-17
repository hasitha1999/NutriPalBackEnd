package com.example.NutriPal.repository;

import com.example.NutriPal.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergy,Long> {
}
