package com.borsuki.app.musicrecommendationsystem.repositories;

import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository <ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
