package com.borsuki.app.musicrecommendationsystem.dtos;

import lombok.Getter;

@Getter
public class JwtToken {

    public String token;

    public JwtToken(String tokenBody) {
        this.token = tokenBody;
    }
}
