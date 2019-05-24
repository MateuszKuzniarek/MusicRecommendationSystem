package com.borsuki.app.music_recommendation_system.controllers;

import com.borsuki.app.music_recommendation_system.dtos.ArtistDto;
import com.borsuki.app.music_recommendation_system.services.ArtistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/artists")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ArtistController {

    private final ArtistService artistService;

    private final ModelMapper modelMapper;

    @Autowired
    public ArtistController(ArtistService artistService, ModelMapper modelMapper) {
        this.artistService = artistService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDto>> getAllArtists() {
        List<ArtistDto> artistDtos = artistService.getAllArtists().stream()
                .map(artist -> modelMapper.map(artist, ArtistDto.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(artistDtos);
    }
}
