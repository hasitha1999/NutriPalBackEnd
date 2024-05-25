package com.example.NutriPal.repository;

import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.LogType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogTypeRepository  extends JpaRepository<LogType,Long> {

    Optional<LogType> findByType(String Type);
}
