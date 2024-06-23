package com.example.NutriPal.service;

import com.example.NutriPal.dto.ChartDataDto;
import com.example.NutriPal.dto.DashboardStaticDataDto;
import com.example.NutriPal.repository.UserRepository;
import com.example.NutriPal.utils.DashboardHelpers;
import org.springframework.stereotype.Service;
import com.example.NutriPal.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DashboardService {

    private final DashboardHelpers dashboardHelpers;
    private final UserRepository userRepository;

    public DashboardService(DashboardHelpers dashboardHelpers, UserRepository userRepository) {
        this.dashboardHelpers = dashboardHelpers;
        this.userRepository = userRepository;
    }

    public ArrayList<ChartDataDto> getDashboardChartData(User user){
        ArrayList<ChartDataDto> chartDataList = new ArrayList<>();
        Long userId = user.getUserId();
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        chartDataList = dashboardHelpers.getTypeWiseChatDataList(startDate, endDate, userId);
        return chartDataList;
    }

//    public ArrayList<ChartDataDto> getDailyGaugeChartData(User user){
//        ArrayList<ChartDataDto> chartDataList = new ArrayList<>();
//        Long userId = user.getUserId();
//        LocalDate startDate = LocalDate.now().minusMonths(1);
//        LocalDate endDate = LocalDate.now();
//        chartDataList = dashboardHelpers.getTypeWiseChatDataList(startDate, endDate, userId);
//
//    }

    public List<Map<String, Object>> getGaugeChartData(User user){

        double totalCalorie = 0;
        String fatLevel = "";
        String carbLevel = "";
        String proteinLevel = "";

        double fatValue = dashboardHelpers.getTypeWiseChatData(LocalDate.now(), LocalDate.now(), 3L, user.getUserId()).getValue();
        double carbohydrateValue = dashboardHelpers.getTypeWiseChatData(LocalDate.now(), LocalDate.now(), 4L, user.getUserId()).getValue();
        double proteinValue = dashboardHelpers.getTypeWiseChatData(LocalDate.now(), LocalDate.now(), 5L, user.getUserId()).getValue();

        totalCalorie = dashboardHelpers.getTotalCalorieIntake(user.getUserId());

        double fatPercentage = fatValue == 0 ? 0 : ((fatValue*9)/totalCalorie)*100;
        double carbohydratePercentage = carbohydrateValue == 0 ? 0 : ((carbohydrateValue*4)/totalCalorie)*100;
        double proteinPercentage = proteinValue == 0 ? 0 : ((proteinValue*4)/totalCalorie)*100;

        List<Map<String, Object>> guageChartDataList = new ArrayList<>();

        String dietType = user.getDietType();
        if (Objects.equals(dietType, "Low Carb Diet")){
            fatLevel = fatPercentage <= 39 ? "inProgress" : fatPercentage > 39 && fatPercentage <= 45 ? "archived" : fatPercentage > 45 && fatPercentage <= 48 ? "limitExceed" : "overLimit";
            proteinLevel = proteinValue <= 39 ? "inProgress" : proteinValue > 39 && proteinValue <= 45 ? "archived" : proteinValue > 45 && proteinValue <= 48 ? "limitExceed" : "overLimit";
            carbLevel = carbohydratePercentage <= 19 ? "inProgress" : (carbohydratePercentage > 19 && carbohydratePercentage <= 25.0) ? "archived" : (carbohydratePercentage > 25 && carbohydratePercentage <= 28) ? "limitExceed" : "overLimit";

        } else if (Objects.equals(dietType, "Balanced Diet")) {
            fatLevel = fatPercentage <= 29 ? "inProgress" : fatPercentage > 29 && fatPercentage <= 35 ? "archived" : fatPercentage > 35 && fatPercentage <= 38 ? "limitExceed" : "overLimit";
            proteinLevel = proteinValue <= 29 ? "inProgress" : proteinValue > 29 && proteinValue <= 35 ? "archived" : proteinValue > 35 && proteinValue <= 38 ? "limitExceed" : "overLimit";
            carbLevel = carbohydratePercentage <= 39 ? "inProgress" : carbohydratePercentage > 39 && carbohydratePercentage <= 45 ? "archived" : carbohydratePercentage > 45 && carbohydratePercentage <= 48 ? "limitExceed" : "overLimit";

        }
        else if (Objects.equals(dietType, "High Protein Diet")) {
            fatLevel = fatPercentage <= 24 ? "inProgress" : fatPercentage > 24 && fatPercentage <= 30 ? "archived" : fatPercentage > 30 && fatPercentage <= 33 ? "limitExceed" : "overLimit";
            proteinLevel = proteinValue <= 34 ? "inProgress" : proteinValue > 34 && proteinValue <= 40 ? "archived" : proteinValue > 40 && proteinValue <= 43 ? "limitExceed" : "overLimit";
            carbLevel = carbohydratePercentage <= 39 ? "inProgress" : carbohydratePercentage > 39 && carbohydratePercentage <= 45 ? "archived" : carbohydratePercentage > 45 && carbohydratePercentage <= 48 ? "limitExceed" : "overLimit";

        }

        guageChartDataList.add(Map.of("type", "Fat", "percentage", fatPercentage, "level", fatLevel));
        guageChartDataList.add(Map.of("type", "Carbohydrate", "percentage", carbohydratePercentage, "level", carbLevel));
        guageChartDataList.add(Map.of("type", "Protein", "percentage", proteinPercentage, "level", proteinLevel));

        return guageChartDataList;
    }

    public DashboardStaticDataDto getStatisticDashboardData(User user){

        DashboardStaticDataDto dashboardStaticDataDto = null;
        double tdee = dashboardHelpers.totalDailyEnergyExpenditureCalculator(user.getUserId());
        double bmr = dashboardHelpers.basalMetabolicRateCalculator(user.getUserId());
        double waterIntake = dashboardHelpers.getTypeWiseChatData(LocalDate.now(), LocalDate.now(), 2L, user.getUserId()).getValue();
        double totalCalorie = dashboardHelpers.getTotalCalorieIntake(user.getUserId());
        double bmi = user.getWeight() / user.getHeight();
        dashboardStaticDataDto = DashboardStaticDataDto.builder().tdee(tdee).bmr(bmr).waterIntake(waterIntake).totalCalorie(totalCalorie).bmi(bmi).build();
        return dashboardStaticDataDto;
    }



}
