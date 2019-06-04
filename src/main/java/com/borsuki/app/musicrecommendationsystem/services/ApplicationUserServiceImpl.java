package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.exceptions.ApplicationException;
import com.borsuki.app.musicrecommendationsystem.exceptions.NotUniqueValueException;
import com.borsuki.app.musicrecommendationsystem.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository,
            PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApplicationUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserRepository.findByUsername(username);
    }


    @Override
    public void addUser(ApplicationUser user) throws ApplicationException {
        if(applicationUserRepository.existsByUsername(user.getUsername())) {
            throw new NotUniqueValueException("username not unique");
        }
        if(applicationUserRepository.existsByEmail(user.getEmail())) {
            throw new NotUniqueValueException("email not unique");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }
}
