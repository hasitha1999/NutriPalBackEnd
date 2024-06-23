package com.example.NutriPal.controller;

import com.example.NutriPal.dto.ChartDataDto;
import com.example.NutriPal.dto.DashboardStaticDataDto;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.service.DailyLogService;
import com.example.NutriPal.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;


    @PostMapping("/getPieChartData")
    public ResponseEntity<ArrayList<ChartDataDto>> getPieChartData(Authentication authentication){

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(dashboardService.getDashboardChartData(user));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/getGaugeChartData")
    public ResponseEntity<List<Map<String, Object>>> getGaugeChartData(Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok((dashboardService.getGaugeChartData(user)));

        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/getStatisticDashboardData")
    public ResponseEntity<DashboardStaticDataDto> getStatisticDashboardData(Authentication authentication){
        try{
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok((dashboardService.getStatisticDashboardData(user)));
        }catch (Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }
}
