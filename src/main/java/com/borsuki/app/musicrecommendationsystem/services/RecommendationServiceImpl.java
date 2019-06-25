package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.entities.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.MANDATORY)
public class RecommendationServiceImpl implements RecommendationService {

    private final LikeService likeService;
    private final SpotifyService spotifyService;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public RecommendationServiceImpl(LikeService likeService, SpotifyService spotifyService, ApplicationUserService applicationUserService) {
        this.likeService = likeService;
        this.spotifyService = spotifyService;
        this.applicationUserService = applicationUserService;
    }

    @Override
    public List<ArtistDto> getRecommendations(String username) throws IOException {
        ApplicationUser user = (ApplicationUser) applicationUserService.loadUserByUsername(username);
        List<Likes> likes = likeService.getAllLikes(user);
        List<ArtistDto> recommendedArtists = new ArrayList<>();
        for(Likes like : likes) {
            recommendedArtists.addAll(spotifyService.getRelatedArtists(like.getArtist_id()));
        }
        return recommendedArtists;
    }
}
