package com.example.NutriPal.repository;

import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface DailyRepository extends JpaRepository <DailyLog,Long> {


    Optional<DailyLog> findByCreatedAtAndUserAndLogType(LocalDateTime createdAt, User user, LogType logType);
}
