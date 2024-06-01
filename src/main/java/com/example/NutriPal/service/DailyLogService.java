package com.example.NutriPal.service;

import com.example.NutriPal.dto.DailyLogChartDto;
import com.example.NutriPal.dto.DailyLogDto;
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
        if (logType.getType() == "Weight"){
            user.setWeight(dailyLogDto.getUserInput());
            userRepository.saveAndFlush(user);
        }


        return dailyRepository.saveAndFlush(dailyLog);
    }

    public DailyLogDto getDailyLogDetailsByCurrentDate(User user, String logCategory, LocalDate dateTime){
        double userWeight  = user.getWeight();
        LogType logType = logTypeRepository.findByType(logCategory).get();
        Optional<DailyLog> dailyLogOptional = dailyRepository.findByCreatedAtAndUserAndLogType(dateTime,user,logType);
        DailyLogDto dailyLogDto = null;
        if(dailyLogOptional.isPresent()){
            DailyLog dailyLog = dailyLogOptional.get();
             dailyLogDto = DailyLogDto.builder().logId(dailyLog.getLogID()).userInput(dailyLog.getInputData()).logType(dailyLog.getLogType().getType()).weight(userWeight).build();
        }
        else {
            dailyLogDto = DailyLogDto.builder().weight(userWeight).build();
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

}
