package com.example.NutriPal.controller;

import com.example.NutriPal.dto.DailyLogDto;
import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.service.DailyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
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
    @PostMapping("/getDailyLogByCurrentDate")
    public ResponseEntity<DailyLog> getDailyLogDetailsByCurrentDate(Authentication authentication, @RequestBody String logType){
        try {
            User user = (User) authentication.getPrincipal();
            Optional<DailyLog> dailyLogOptional =  dailyLogService.getDailyLogDetailsByCurrentDate(user,logType);
            DailyLog dailyLog;
            if(dailyLogOptional.isPresent()){
                dailyLog = dailyLogOptional.get();
            }else{
                dailyLog = null;
            }
            return ResponseEntity.ok(dailyLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
