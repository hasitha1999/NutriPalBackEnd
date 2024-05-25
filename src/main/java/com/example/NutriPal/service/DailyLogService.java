package com.example.NutriPal.service;

import com.example.NutriPal.dto.DailyLogDto;
import com.example.NutriPal.entity.DailyLog;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.DailyRepository;
import com.example.NutriPal.repository.LogTypeRepository;
import com.example.NutriPal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
             dailyLog = DailyLog.builder().user(user).logType(logType).input_data(dailyLogDto.getUserInput()).build();
        }else{
            dailyLog = dailyRepository.findById(dailyLogDto.getLogId()).get();
            dailyLog.setInput_data(dailyLogDto.getUserInput());
        }
        if (logType.getType() == "Weight"){
            user.setWeight(dailyLogDto.getUserInput());
            userRepository.saveAndFlush(user);
        }


        return dailyRepository.saveAndFlush(dailyLog);
    }

    public Optional<DailyLog> getDailyLogDetailsByCurrentDate(User user, String logCategory){
        LogType logType = logTypeRepository.findByType(logCategory).get();

        return  dailyRepository.findByCreatedAtAndUserAndLogType(LocalDateTime.now(),user,logType);
    }
}
