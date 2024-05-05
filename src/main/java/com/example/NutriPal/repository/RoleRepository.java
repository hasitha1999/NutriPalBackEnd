package com.example.NutriPal.repository;

import java.util.Optional;

import com.example.NutriPal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
