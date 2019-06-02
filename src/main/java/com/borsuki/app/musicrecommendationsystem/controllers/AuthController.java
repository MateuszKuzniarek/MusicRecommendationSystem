package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.AuthDto;
import com.borsuki.app.musicrecommendationsystem.dtos.JwtToken;
import com.borsuki.app.musicrecommendationsystem.security.JwtTokenUtil;
import com.borsuki.app.musicrecommendationsystem.services.ApplicationUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final ApplicationUserServiceImpl applicationUserService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            ApplicationUserServiceImpl applicationUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.applicationUserService = applicationUserService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthDto authDto) {
        String username = authDto.getUsername();
        String password = authDto.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = applicationUserService.loadUserByUsername(username);
        String tokenBody = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtToken(tokenBody));
    }
}
