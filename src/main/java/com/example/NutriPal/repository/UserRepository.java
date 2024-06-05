package com.example.NutriPal.repository;


import com.example.NutriPal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByGymID(String gymID);

    @Query("SELECT user FROM User user WHERE user.isDeleted=false AND user.role.roleName = 'USER' AND " +
            "(LOWER(user.firstName) LIKE CONCAT('%', :firstName, '%') OR LOWER(user.lastName) LIKE CONCAT('%', :lastName, '%')" +
            " OR LOWER(user.gymID) LIKE CONCAT('%', :gymID, '%'))")
    Page<User>
    findAllUsers
            (Pageable pageable, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("gymID") String gymID);


}
