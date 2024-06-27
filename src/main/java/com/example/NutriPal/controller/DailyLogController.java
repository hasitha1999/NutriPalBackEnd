package com.example.NutriPal.controller;

import com.example.NutriPal.dto.DailyLogChartDto;
import com.example.NutriPal.dto.DailyLogDto;
import com.example.NutriPal.dto.DailyLogEatDto;
import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.service.DailyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/daily-log")
public class DailyLogController {

    private final DailyLogService dailyLogService;

    @PostMapping("/createOrUpdateDailyLog")
    public ResponseEntity<DailyLog> createDailyLog(Authentication authentication, @RequestBody DailyLogDto dailyLogDto) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(dailyLogService.createDailyLog(user,dailyLogDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/getDailyLogByCurrentDate/{logType}")
    public ResponseEntity<DailyLogDto> getDailyLogDetailsByCurrentDate(Authentication authentication,@PathVariable  String logType){
        try {
            User user = (User) authentication.getPrincipal();
            LocalDate dateTime = LocalDateTime.now().toLocalDate();
            return ResponseEntity.ok(dailyLogService.getDailyLogDetailsByCurrentDate(user,logType,dateTime));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/getDailyLogDataListByMonth/{logType}")
    public ResponseEntity<DailyLogChartDto> getDailyLogDataListByMonth(Authentication authentication, @PathVariable String logType){
        try {
            User user = (User) authentication.getPrincipal();
            LocalDate dateTime = LocalDateTime.now().toLocalDate().minusMonths(1);
            DailyLogChartDto dailyLogOptional =  dailyLogService.getDailyLogDataList(user,logType,dateTime);
            return ResponseEntity.ok(dailyLogOptional);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
