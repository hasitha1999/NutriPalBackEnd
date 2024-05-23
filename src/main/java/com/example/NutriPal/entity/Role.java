package com.example.NutriPal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Column(nullable = false, length = 100, name = "role_name")
    @NotBlank(message = "Role name must be required.")
    private String roleName;

    public String getName() {
        return roleName;
    }
}
