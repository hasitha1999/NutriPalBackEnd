package com.example.NutriPal.entity;

import com.example.NutriPal.utils.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    private String gender;
    private String dob;
    private String phone;
    private String goal;

    private int activeLevel;
    private double weight;
    private double height;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String password;

    private Boolean isDeleted = false;
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isDeleted && isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive && !isDeleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isDeleted && isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
