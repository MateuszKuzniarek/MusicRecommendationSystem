package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
<<<<<<< HEAD
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;

=======
import com.borsuki.app.musicrecommendationsystem.services.ArtistService;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyServiceImpl;
import org.modelmapper.ModelMapper;
>>>>>>> a311548... ArtistController is able to get artist by id
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD

import java.io.IOException;

=======

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> a311548... ArtistController is able to get artist by id

@RestController
@RequestMapping("/artists")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ArtistController {

    private final SpotifyService spotifyService;

    @Autowired
    public ArtistController(SpotifyService spotifyService) {
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
