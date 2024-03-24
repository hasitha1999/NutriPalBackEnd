package com.example.NutriPal.controller;

import com.example.NutriPal.dto.AuthenticationRequest;
import com.example.NutriPal.dto.AuthenticationResponse;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.service.AuthService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

        try{
            authService.register(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("email", "Email exists"));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("userRef", "User ref not exists"));
        }
    }

    @PostMapping("/email-verification/{verificationToken}")
    public ResponseEntity<AuthenticationResponse> emailVerification(@PathVariable String verificationToken) {

        try {
            return ResponseEntity.ok(authService.verifyUserAndCreate(verificationToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

}
