package com.borsuki.app.musicrecommendationsystem.controllers;


import com.borsuki.app.musicrecommendationsystem.dtos.NewUserDto;
import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.exceptions.ApplicationException;
import com.borsuki.app.musicrecommendationsystem.services.ApplicationUserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ApplicationUserControllerTest {

    @InjectMocks
    private ApplicationUserController applicationUserController;
    @Mock
    private ApplicationUserServiceImpl applicationUserService;
    @Spy
    private ModelMapper modelMapper;

    private NewUserDto newUserDto;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        newUserDto = new NewUserDto();
        newUserDto.setUsername("User");
        newUserDto.setPassword("Password");
        newUserDto.setEmail("example@gmail.com");
    }

    @Test
    public void register() throws ApplicationException {
        //mock
        doCallRealMethod().when(modelMapper).map(newUserDto, ApplicationUser.class);
        doThrow(ApplicationException.class).when(applicationUserService).addUser(any(ApplicationUser.class));

        //when-then
        assertThrows(ApplicationException.class, () -> applicationUserController.register(newUserDto));
    }
}
