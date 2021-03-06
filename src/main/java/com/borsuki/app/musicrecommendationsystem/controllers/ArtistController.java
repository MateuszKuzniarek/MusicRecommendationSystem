package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ArtistController extends BaseController {

    private final SpotifyService spotifyService;

    @Autowired
    public ArtistController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
            paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public ResponseEntity<?> getArtist(@RequestBody ArtistDto artistDto) {
        ArtistDto result = spotifyService.getArtistById(artistDto.getId());
        result.setName(result.getName().replace("\"", ""));
        if (result.getName().equals("")) { return ResponseEntity.status(HttpStatus.OK).body("Artist not found"); }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
