package com.borsuki.app.musicrecommendationsystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ArtistDto {

    private String id;

    private String name;

    public ArtistDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArtistDto() {

    }
}
