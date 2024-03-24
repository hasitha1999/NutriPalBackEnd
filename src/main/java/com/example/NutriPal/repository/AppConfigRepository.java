package com.example.NutriPal.repository;

import java.util.Optional;

import com.example.NutriPal.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {
    Optional<AppConfig> findByProperty(String property);
}
