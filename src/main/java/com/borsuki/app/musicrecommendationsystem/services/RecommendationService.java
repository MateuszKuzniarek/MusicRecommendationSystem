package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;

import java.io.IOException;
import java.util.List;

public interface RecommendationService {
    List<ArtistDto> getRecommendations(String username) throws IOException;
}
