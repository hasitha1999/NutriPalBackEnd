package com.example.NutriPal.repository;

import com.example.NutriPal.dto.RecipeWishListDTO;
import com.example.NutriPal.entity.RecipeSaved;
import com.example.NutriPal.entity.Role;
import com.example.NutriPal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface RecipeSavedRepository extends JpaRepository<RecipeSaved, Integer> {

    Optional<ArrayList<RecipeSaved>> findAllByUser(User user);
}
