package com.borsuki.app.musicrecommendationsystem.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewUserDto {

    @NotEmpty(message = "Username must be specified.")
    @Size(max = 32, message = "Login can not be longer than 32 characters.")
    private String username;

    @NotEmpty(message = "Email must be specified.")
    @Email(message = "Email address must be valid.")
    @Size(max = 64, message = "Email address can not be longer than 64 characters.")
    private String email;

    @NotEmpty(message = "Password must be specified.")
    @Size(min = 8, max = 64, message = "Password must be at 8 characters length up to 64 characters.")
    @Pattern.List({
            @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter."),
            @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one capital letter."),
            @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit."),
            @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character."),
            @Pattern(regexp = "\\S+", message = "Password must not contain whitespaces.")
    })
    private String password;
}
