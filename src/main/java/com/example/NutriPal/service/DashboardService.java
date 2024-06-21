package com.example.NutriPal.service;

import com.example.NutriPal.dto.ChartDataDto;
import com.example.NutriPal.repository.DailyRepository;
import com.example.NutriPal.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.NutriPal.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class DashboardService {

    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;

    public DashboardService(DailyRepository dailyRepository, UserRepository userRepository) {
        this.dailyRepository = dailyRepository;
        this.userRepository = userRepository;
    }

    public ArrayList<ChartDataDto> getDashboardChartData(User user){

        ArrayList<ChartDataDto> chartDataList = new ArrayList<>();
        Long userId = user.getUserId();
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        ChartDataDto fatMonthlyVolume = dailyRepository.getChartData(startDate, endDate, 3L, userId);
        ChartDataDto proteinMonthlyVolume = dailyRepository.getChartData(startDate, endDate, 4L, userId);
        ChartDataDto carbMonthlyVolume = dailyRepository.getChartData(startDate, endDate, 5L, userId);
        Collections.addAll(chartDataList, fatMonthlyVolume, carbMonthlyVolume, proteinMonthlyVolume);

        return chartDataList;

    }

}
