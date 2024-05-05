package com.example.NutriPal.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.NutriPal.config.JwtService;
import com.example.NutriPal.dto.AuthenticationRequest;
import com.example.NutriPal.dto.AuthenticationResponse;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.RoleRepository;
import com.example.NutriPal.repository.UserRepository;
import com.example.NutriPal.utils.StringHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    public User addUser(User user){
        user.setIsActive(true);
        user.setIsDeleted(false);
       User savedUser =  userRepository.saveAndFlush(user);

       return savedUser;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(User user){
        return userRepository.findById(user.getUserId()).get();
    }

}
