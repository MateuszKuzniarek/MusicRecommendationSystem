package com.borsuki.app.musicrecommendationsystem.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
