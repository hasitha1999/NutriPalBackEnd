package com.example.NutriPal.controller;

import com.example.NutriPal.dto.AuthenticationRequest;
import com.example.NutriPal.dto.AuthenticationResponse;
import com.example.NutriPal.entity.Allergy;
import com.example.NutriPal.entity.NCD;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.service.AuthService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/createOrUpdateUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
                try {
                    return ResponseEntity.ok(authService.addUser(user));
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
    }
    @GetMapping("/getAllUsers")
    public Map<String, Object> users(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String globalFilter) {
        return authService.getAllUsers(pageNumber, pageSize, globalFilter.toLowerCase());
    }

    @GetMapping("/getUserById")
    public ResponseEntity<User> getUserById(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(authService.getUserById(user));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllNCD")
    public ResponseEntity<List<NCD>> getAllNCD() {
        try {
            return ResponseEntity.ok(authService.loadALLNCD());
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllAllergies")
    public ResponseEntity<List<Allergy>> getAllAllergies() {
        try {
            return ResponseEntity.ok(authService.loadALLAllergy());
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<AuthenticationResponse> forgotPassword(@RequestBody AuthenticationRequest authenticationRequest) {
        authService.passwordResetSendMail(authenticationRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<String> forgotPasswordReset(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.passwordReset(authenticationRequest));
    }

}
