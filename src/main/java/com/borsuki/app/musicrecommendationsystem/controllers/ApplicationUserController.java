package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.NewUserDto;
import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.exceptions.ApplicationException;
import com.borsuki.app.musicrecommendationsystem.services.ApplicationUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ApplicationUserController extends BaseController{

    private final ApplicationUserService applicationUserService;

    private final ModelMapper modelMapper;

    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService, ModelMapper modelMapper) {
        this.applicationUserService = applicationUserService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity register(@RequestBody @Valid NewUserDto newUserDto) throws ApplicationException {
        ApplicationUser user = modelMapper.map(newUserDto, ApplicationUser.class);
        applicationUserService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
