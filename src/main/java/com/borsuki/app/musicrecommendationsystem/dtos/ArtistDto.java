package com.borsuki.app.musicrecommendationsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

    @Null
    private String id;

    @NotEmpty
    private String name;

}
