package com.borsuki.app.musicrecommendationsystem.dtos;

import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesDto {

    private Long id;

    private ApplicationUser user_id;

    private String artist_id;
}
