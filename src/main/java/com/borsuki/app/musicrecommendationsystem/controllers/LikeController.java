package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.dtos.LikesDto;
import com.borsuki.app.musicrecommendationsystem.entities.Likes;
import com.borsuki.app.musicrecommendationsystem.services.ApplicationUserServiceImpl;
import com.borsuki.app.musicrecommendationsystem.services.LikeService;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;
    private final ApplicationUserServiceImpl applicationUserService;
    private final ModelMapper modelMapper;
    private final SpotifyService spotifyService;


    @Autowired
    public LikeController(LikeService likeService, ApplicationUserServiceImpl applicationUserService, ModelMapper modelMapper, SpotifyService spotifyAPI) {
        this.likeService = likeService;
        this.applicationUserService = applicationUserService;
        this.modelMapper = modelMapper;
        this.spotifyService = spotifyAPI;
    }

    @GetMapping
    public ResponseEntity<?> getAllLikes(Principal principal) throws IOException {
        List<LikesDto> likeDtos = likeService.getAllLikes(applicationUserService.loadUserByUsername(principal.getName())).stream()
                .map(likes -> modelMapper.map(likes, LikesDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(likeDtos);
    }

    @PostMapping
    public ResponseEntity giveLike(@RequestBody ArtistDto artistDto, Principal principal) throws Exception {
        Likes result = new Likes();
        result.setUser_id(applicationUserService.loadUserByUsername(principal.getName()));
        result.setArtist_id(spotifyService.getArtist(artistDto.getName()).getId());
        likeService.giveLike(result);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
