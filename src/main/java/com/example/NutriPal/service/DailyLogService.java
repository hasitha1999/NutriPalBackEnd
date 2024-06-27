package com.example.NutriPal.service;

import com.example.NutriPal.dto.DailyLogChartDto;
import com.example.NutriPal.dto.DailyLogDto;
import com.example.NutriPal.dto.DailyLogEatDto;
import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.DailyRepository;
import com.example.NutriPal.repository.LogTypeRepository;
import com.example.NutriPal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyLogService {

    private final DailyRepository dailyRepository;
    private final LogTypeRepository logTypeRepository;
    private final UserRepository userRepository;
    public DailyLog createDailyLog(User user, DailyLogDto dailyLogDto){
        LogType logType = logTypeRepository.findByType(dailyLogDto.getLogType()).get();
        DailyLog dailyLog;
        if(dailyLogDto.getLogId() == null){
             dailyLog = DailyLog.builder().user(user).logType(logType).inputData(dailyLogDto.getUserInput()).build();
        }else{
            dailyLog = dailyRepository.findById(dailyLogDto.getLogId()).get();
            dailyLog.setInputData(dailyLogDto.getUserInput());
        }
        if (Objects.equals(logType.getType(), "Weight")){
            user.setWeight(dailyLogDto.getUserInput());
            userRepository.saveAndFlush(user);
        }


        return dailyRepository.saveAndFlush(dailyLog);
    }
    public List<DailyLog> createDailyEatLog(User user, DailyLogEatDto dailyLogEatDto){
        LogType fat = logTypeRepository.findByType("Fat").get();
        LogType carb = logTypeRepository.findByType("Carbohydrate").get();
        LogType protein = logTypeRepository.findByType("Protein").get();
        LogType calorie = logTypeRepository.findByType("Calorie").get();

        LocalDate date = LocalDate.now();
        DailyLog dailyLogFat;
        DailyLog dailyLogCarb;
        DailyLog dailyLogProtein;
        DailyLog dailyLogCalorie;

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
        if(dailyRepository.findByCreatedAtAndLogType(date,calorie).isPresent()){
            dailyLogCalorie = dailyRepository.findByCreatedAtAndLogType(date,calorie).get();
            dailyLogCalorie.setInputData(dailyLogProtein.getInputData() + dailyLogEatDto.getCalorie());
        }else{
            dailyLogCalorie = DailyLog.builder().user(user).logType(calorie).inputData(dailyLogEatDto.getCalorie()).build();
        }

        ArrayList<DailyLog> dailyLogList = new ArrayList<>();
        dailyLogList.add(dailyLogCarb);
        dailyLogList.add(dailyLogFat);
        dailyLogList.add(dailyLogProtein);
        dailyLogList.add(dailyLogCalorie);

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
    public DailyLogChartDto getDailyLogDataList(User user, String logCategory, LocalDate dateTime){
        LogType logType = logTypeRepository.findByType(logCategory).get();
        Optional<ArrayList<DailyLog>> dailyLogOptional = dailyRepository.findByCreatedAtAfterAndUserAndLogType(dateTime,user,logType);
        ArrayList<LocalDate> dateList = new ArrayList<>();
        ArrayList<Double> userInputValueList = new ArrayList<>();
        DailyLogChartDto dailyLogChartDto;
        if(dailyLogOptional.isPresent()){
            for (DailyLog logElement: dailyLogOptional.get()) {
                dateList.add(logElement.getCreatedAt());
                userInputValueList.add(logElement.getInputData());
            }
            dailyLogChartDto = DailyLogChartDto.builder().x(dateList).y(userInputValueList).build();

        }else{
            dailyLogChartDto = null;
        }
        return  dailyLogChartDto;

    }


}
