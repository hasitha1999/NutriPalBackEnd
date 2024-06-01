package com.example.NutriPal.repository;

import com.example.NutriPal.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

}
