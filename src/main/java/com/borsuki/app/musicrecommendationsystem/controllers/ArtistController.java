package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.services.ArtistService;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/artists")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ArtistController {

    private final ArtistService artistService;

    private final ModelMapper modelMapper;

    private final SpotifyService spotifyService;

    @Autowired
    public ArtistController(ArtistService artistService, ModelMapper modelMapper, SpotifyService spotifyService) {
        this.artistService = artistService;
        this.modelMapper = modelMapper;
        this.spotifyService = spotifyService;
    }

    @PostMapping
    public ResponseEntity<?> getArtist(@RequestBody ArtistDto artistDto) throws IOException {
        ArtistDto result = spotifyService.getArtistById(artistDto.getId());
        result.setName(result.getName().replace("\"",""));
        if(result.getName().equals(""))
            return ResponseEntity.status(HttpStatus.OK).body("Artist not found");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
