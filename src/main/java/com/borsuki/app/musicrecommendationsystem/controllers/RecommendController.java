package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    private SpotifyService spotifyService;

    @Autowired
    public RecommendController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
            paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public List<ArtistDto> giveRecommendation(@RequestBody ArtistDto artistDto) throws Exception {
        artistDto = spotifyService.getArtist(artistDto.getName());
        return spotifyService.getRelatedArtists(artistDto.getId());
    }

}
