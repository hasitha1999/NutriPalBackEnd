package com.example.NutriPal.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.NutriPal.config.JwtService;
import com.example.NutriPal.dto.AuthenticationRequest;
import com.example.NutriPal.dto.AuthenticationResponse;
import com.example.NutriPal.entity.Role;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.RoleRepository;
import com.example.NutriPal.repository.UserRepository;
import com.example.NutriPal.utils.StringHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender emailSender;

    @Value("${spring.mail.noreply}")
    private String noReplyEMail;

    @Value("${app.domain}")
    private String appDomain;

    private final static Map<String, User> UNVERIFIED_USERS = new HashMap<>();
    public User addUser(User user){
        Role roleUser = roleRepository.findById(2).get();

        user.setIsActive(true);
        user.setIsDeleted(false);
        if(user.getPassword() == null){
            user.setPassword(passwordEncoder.encode("1234"));
        }
        user.setRole(roleUser);
       User savedUser =  userRepository.saveAndFlush(user);

       return savedUser;
    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getGymID(),
                        authenticationRequest.getPassword()
                )
        );

        // generate token
        User user = userRepository.findByGymID(authenticationRequest.getGymID()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).role(user.getRole().getName()).build();
    }

    public Map<String, Object> getAllUsers(int pageNumber, int pageSize, String globalFilter) {
        Page<User> userPage = userRepository
                .findAllUsers
                        (PageRequest.of(pageNumber, pageSize), globalFilter, globalFilter, globalFilter);
        return Map.of("data", userPage.stream().toList(), "count", userPage.getTotalElements());
    }

    public User getUserById(User user){
        return userRepository.findById(user.getUserId()).get();
    }

    public void passwordResetSendMail(AuthenticationRequest authenticationRequest){
        User user = userRepository.findByGymID(authenticationRequest.getGymID()).orElseThrow();
        Thread emailThread = new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(noReplyEMail);
            message.setTo(user.getEmail());
            message.setSubject("Nutripal | Password Reset");
            message.setText(
                    "Use the following link to reset your password. \n\n" +
                            appDomain + "/resetPassword?n=" + user.getGymID()

            );
            emailSender.send(message);
        });
            emailThread.start();
    }
    public void passwordReset(AuthenticationRequest authenticationRequest){
        User user = userRepository.findByGymID(authenticationRequest.getGymID()).orElseThrow();
        user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));

        userRepository.saveAndFlush(user);

    }


}
