package com.example.NutriPal.service;

import com.example.NutriPal.dto.*;
import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.DailyRepository;
import com.example.NutriPal.repository.LogTypeRepository;
import com.example.NutriPal.repository.UserRepository;
import com.example.NutriPal.utils.DashboardHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DailyLogService {

    private final DailyRepository dailyRepository;
    private final LogTypeRepository logTypeRepository;
    private final UserRepository userRepository;
    private final DashboardHelpers dashboardHelpers;
    public DailyLog createDailyLog(User user, DailyLogDto dailyLogDto){
        LogType logType = logTypeRepository.findByType(dailyLogDto.getLogType()).get();
        DailyLog dailyLog;
        if(dailyLogDto.getLogId() == null){
             dailyLog = DailyLog.builder().user(user).logType(logType).inputData(dailyLogDto.getUserInput()).build();
        }else{
            dailyLog = dailyRepository.findById(dailyLogDto.getLogId()).get();
            dailyLog.setInputData(dailyLogDto.getUserInput());
        }
        if (logType.getType() == "Weight"){
            user.setWeight(dailyLogDto.getUserInput());
            userRepository.saveAndFlush(user);
        }


        return dailyRepository.saveAndFlush(dailyLog);
    }
    public List<DailyLog> createDailyEatLog(User user, DailyLogEatDto dailyLogEatDto){
        LogType fat = logTypeRepository.findByType("Fat").get();
        LogType carb = logTypeRepository.findByType("Carbohydrate").get();
        LogType protein = logTypeRepository.findByType("Protein").get();

        LocalDate date = LocalDate.now();
        DailyLog dailyLogFat;
        DailyLog dailyLogCarb;
        DailyLog dailyLogProtein;

        if(dailyRepository.findByCreatedAtAndLogType(date,fat).isPresent()){
            dailyLogFat = dailyRepository.findByCreatedAtAndLogType(date,fat).get();
            dailyLogFat.setInputData(dailyLogFat.getInputData() + dailyLogEatDto.getFat());
        }else{
            dailyLogFat = DailyLog.builder().user(user).logType(fat).inputData(dailyLogEatDto.getFat()).build();
        }
        if(dailyRepository.findByCreatedAtAndLogType(date,fat).isPresent()){
            dailyLogCarb = dailyRepository.findByCreatedAtAndLogType(date,carb).get();
            dailyLogCarb.setInputData(dailyLogCarb.getInputData() + dailyLogEatDto.getCarbs());
        }else{
            dailyLogCarb = DailyLog.builder().user(user).logType(carb).inputData(dailyLogEatDto.getCarbs()).build();
        }
        if(dailyRepository.findByCreatedAtAndLogType(date,fat).isPresent()){
            dailyLogProtein = dailyRepository.findByCreatedAtAndLogType(date,protein).get();
            dailyLogProtein.setInputData(dailyLogProtein.getInputData() + dailyLogEatDto.getProtein());
        }else{
            dailyLogProtein = DailyLog.builder().user(user).logType(protein).inputData(dailyLogEatDto.getProtein()).build();
        }

        ArrayList<DailyLog> dailyLogList = new ArrayList<>();
        dailyLogList.add(dailyLogCarb);
        dailyLogList.add(dailyLogFat);
        dailyLogList.add(dailyLogProtein);

        return dailyRepository.saveAllAndFlush(dailyLogList);
    }

    public DailyLogDto getDailyLogDetailsByCurrentDate(User user, String logCategory, LocalDate dateTime){
        LogType logType = logTypeRepository.findByType(logCategory).get();
        Optional<DailyLog> dailyLogOptional = dailyRepository.findByCreatedAtAndUserAndLogType(dateTime,user,logType);
        DailyLogDto dailyLogDto = null;
        if(dailyLogOptional.isPresent()){
            DailyLog dailyLog = dailyLogOptional.get();
             dailyLogDto = DailyLogDto.builder().logId(dailyLog.getLogID()).userInput(dailyLog.getInputData()).logType(dailyLog.getLogType().getType()).weight(user.getWeight()).gender(user.getGender()).dob(user.getDob()).build();
        }
        else {
            dailyLogDto = DailyLogDto.builder().weight(user.getWeight()).build();
        }

         return dailyLogDto;
    }
    public ArrayList<DailyLogChartDto> getDailyLogDataList(User user, String logCategory, LocalDate dateTime){
        LogType logType = logTypeRepository.findByType(logCategory).get();
        Optional<ArrayList<DailyLog>> dailyLogOptional = dailyRepository.findByCreatedAtAfterAndUserAndLogType(dateTime,user,logType);
        ArrayList<DailyLogChartDto> dailyLogList = new ArrayList<>();

        if(dailyLogOptional.isPresent()){
            for (DailyLog logElement: dailyLogOptional.get()) {
                DailyLogChartDto dailyLogChartDto = DailyLogChartDto.builder().date(logElement.getCreatedAt()).userInputValue(logElement.getInputData()).build();
                dailyLogList.add(dailyLogChartDto);

            }

        }else{
            dailyLogList = null;
        }
        return  dailyLogList;

    }

    public AverageWaterValueDto getAverageWaterIntake(String timeGap, User user){
        ChartDataDto waterIntakeData = null;
        double averageValue = 0;
        AverageWaterValueDto averageWaterValueDto = null;
        if (Objects.equals(timeGap, "Month")){
            waterIntakeData = dailyRepository.getChartData(LocalDate.now().minusMonths(1), LocalDate.now(), 1L, user.getUserId());
            averageValue = waterIntakeData.getValue()/30;


        } else if (Objects.equals(timeGap, "Week")) {
            waterIntakeData = dailyRepository.getChartData(LocalDate.now().minusDays(7), LocalDate.now(), 1L, user.getUserId());
            averageValue = waterIntakeData.getValue()/7;
        }
        averageWaterValueDto = AverageWaterValueDto.builder().averagewaterIntake(averageValue).build();

        return averageWaterValueDto;


    }
    public ArrayList<DailyWaterLogDto> getWeekWaterIntakeData(User user){
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        double dailyWaterGoal = (((user.getWeight() * 2.2)/2)*29.574*100)/100;
        double currentDateWaterIntake = 0;
        boolean isGoalArchived = false;
       ArrayList<DailyWaterLogDto> weekWaterLogList = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            currentDateWaterIntake = dashboardHelpers.getTypeWiseChatData(date, date, 1L, user.getUserId()).getValue();
            isGoalArchived = currentDateWaterIntake >= dailyWaterGoal;
            DailyWaterLogDto dailyWaterLogDto = DailyWaterLogDto.builder().waterAmount(currentDateWaterIntake).dayOfWeek(dayOfWeek).isArchived(isGoalArchived).build();
            weekWaterLogList.add(dailyWaterLogDto);

        }
        return weekWaterLogList;
    }



}
