package com.example.NutriPal.utils;

import com.example.NutriPal.dto.ChartDataDto;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.DailyRepository;
import com.example.NutriPal.repository.LogTypeRepository;
import com.example.NutriPal.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Component
public class DashboardHelpers {

    private final DailyRepository dailyRepository;
    private final LogTypeRepository logTypeRepository;
    private final UserRepository userRepository;

    public DashboardHelpers(DailyRepository dailyRepository, LogTypeRepository logTypeRepository, UserRepository userRepository) {
        this.dailyRepository = dailyRepository;
        this.logTypeRepository = logTypeRepository;
        this.userRepository = userRepository;
    }

    public ChartDataDto getTypeWiseChatData(LocalDate startDate, LocalDate endDate, Long logType, Long userId){
        ChartDataDto selectedChartData;
        LogType selectedLogType = logTypeRepository.findById(logType).get();
        selectedChartData = dailyRepository.getChartData(startDate, endDate, logType, userId);

        if (selectedChartData == null) {
            selectedChartData = ChartDataDto.builder().value(0L).label(selectedLogType.getType()).build();
        }
        return selectedChartData;

    }

    public ArrayList<ChartDataDto> getTypeWiseChatDataList(LocalDate startDate, LocalDate endDate, Long userId){
        ArrayList<ChartDataDto> chartDataList = new ArrayList<>();

        ChartDataDto fatMonthlyVolume = getTypeWiseChatData(startDate, endDate, 3L, userId);
        ChartDataDto proteinMonthlyVolume = getTypeWiseChatData(startDate, endDate, 4L, userId);
        ChartDataDto carbMonthlyVolume = getTypeWiseChatData(startDate, endDate, 5L, userId);
        Collections.addAll(chartDataList, fatMonthlyVolume, carbMonthlyVolume, proteinMonthlyVolume);

        return chartDataList;

    }

    public double basalMetabolicRateCalculator(Long userId){
        double bmr;
        User user = userRepository.findById(userId).get();
        Period age = Period.between(LocalDate.parse(user.getDob()), LocalDate.now());
        double ageInYears = age.getYears();


        if (Objects.equals(user.getGender(), "Male")){
            bmr = 88.362 + (13.397 * user.getWeight()) + (4.799* user.getHeight()) - (5.677* ageInYears);

        } else if (Objects.equals(user.getGender(), "Female")) {
            bmr = 447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * ageInYears);

        }else {
            bmr = 0;
        }
        return bmr;

    }

    public double totalDailyEnergyExpenditureCalculator(Long userId){
        double tdee;
        User user = userRepository.findById(userId).get();
        tdee = basalMetabolicRateCalculator(userId) * user.getActiveLevel();
        return tdee;

    }

    public double getTotalCalorieIntake(Long userId){
        ArrayList<ChartDataDto> chartDataList = getTypeWiseChatDataList(LocalDate.now(), LocalDate.now(), userId);

        double fatValue = chartDataList.stream().filter(chartData -> Objects.equals(chartData.getLabel(), "Fat")).findFirst().orElse(new ChartDataDto()).getValue();
        double carbohydrateValue = chartDataList.stream().filter(chartData -> Objects.equals(chartData.getLabel(), "Carbohydrate")).findFirst().orElse(new ChartDataDto()).getValue();
        double proteinValue = chartDataList.stream().filter(chartData -> Objects.equals(chartData.getLabel(), "Protein")).findFirst().orElse(new ChartDataDto()).getValue();

        return (fatValue * 9) + (carbohydrateValue * 4) + (proteinValue * 4);

    }





}
