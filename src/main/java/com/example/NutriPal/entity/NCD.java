package com.example.NutriPal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NCD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ncdId;

    private String ncdName;

//    @ManyToMany
//    private Set<User> user;

}
