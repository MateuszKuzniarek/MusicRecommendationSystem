package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.services.RecommendationService;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RecommendationController {

    private SpotifyService spotifyService;
    private RecommendationService recommendationService;

    @Autowired
    public RecommendationController(SpotifyService spotifyService, RecommendationService recommendationService) {
        this.spotifyService = spotifyService;
        this.recommendationService = recommendationService;
    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
            paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public List<ArtistDto> giveRecommendation(@RequestBody ArtistDto artistDto) throws Exception {
        artistDto = spotifyService.getArtist(artistDto.getName());
        return spotifyService.getRelatedArtists(artistDto.getId());
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
            paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public ResponseEntity<List<ArtistDto>> getRecommendations(Principal principal) throws IOException {
        List<ArtistDto> artistDtos = recommendationService.getRecommendations(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(artistDtos);
    }

}
