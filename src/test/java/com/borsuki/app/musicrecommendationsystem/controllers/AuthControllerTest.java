package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.AuthDto;
import com.borsuki.app.musicrecommendationsystem.dtos.JwtToken;
import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.security.JwtTokenUtil;
import com.borsuki.app.musicrecommendationsystem.services.ApplicationUserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private ApplicationUserServiceImpl applicationUserService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private AuthenticationManager authenticationManager;

    private AuthDto authDto = new AuthDto();
    private ApplicationUser applicationUser = new ApplicationUser();
    private String token = "Token";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        //an authDto set up
        authDto.setUsername("user");
        authDto.setPassword("userPassword");

        //an applicationUser set up
        applicationUser.setUsername(authDto.getUsername());
        applicationUser.setPassword(authDto.getPassword());

        //the methods mock
        doReturn(applicationUser).when(applicationUserService).loadUserByUsername(authDto.getUsername());
        doReturn(token).when(jwtTokenUtil).generateToken(applicationUser);
    }

    @Test
    public void login() {
        //when
        ResponseEntity<?> response = authController.login(authDto);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(((JwtToken) response.getBody()).getToken(), is(token));
    }
}
