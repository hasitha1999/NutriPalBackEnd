package com.example.NutriPal.repository;

import com.example.NutriPal.dto.ChartDataDto;
import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public interface DailyRepository extends JpaRepository <DailyLog,Long> {


    Optional<DailyLog> findByCreatedAtAndUserAndLogType(LocalDate createdAt, User user, LogType logType);


    Optional<ArrayList<DailyLog>> findByCreatedAtAfterAndUserAndLogType(LocalDate createdAt, User user, LogType logType);

    Optional<DailyLog> findByCreatedAtAndLogType(LocalDate createdAt,LogType logType);

    @Query("Select new com.example.NutriPal.dto.ChartDataDto(sum(d.inputData), d.logType.type) from DailyLog d where d.createdAt between ?1 and ?2 and d.logType.logTypeId = ?3 and d.user.userId = ?4 group by d.logType.logTypeId")
    ChartDataDto getChartData(LocalDate startDate, LocalDate endDate, Long logType, Long userId);


}
