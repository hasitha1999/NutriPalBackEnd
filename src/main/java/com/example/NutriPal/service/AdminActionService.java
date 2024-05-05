package com.example.NutriPal.service;

import com.example.NutriPal.entity.AppConfig;
import com.example.NutriPal.repository.AppConfigRepository;
import com.example.NutriPal.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminActionService {
    private final UserRepository userRepository;
    private final AppConfigRepository appConfigRepository;
    public AppConfig getAppConfig(String property) throws Exception {

        Optional<AppConfig> appConfigOptional = appConfigRepository.findByProperty(property);
        return appConfigOptional.orElseThrow(() -> new Exception("App config not found"));
    }



    public void saveAppConfig(AppConfig appConfig) {
        appConfigRepository.save(appConfig);
    }

}
