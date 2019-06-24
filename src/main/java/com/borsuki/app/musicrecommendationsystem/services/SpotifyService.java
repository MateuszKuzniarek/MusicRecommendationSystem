package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;

import java.io.IOException;
import java.util.List;

public interface SpotifyService {

    ArtistDto getArtist(String artistName) throws Exception;

    List<ArtistDto> getRelatedArtists(String artistId) throws IOException;

    ArtistDto getArtistById(String artistId) throws IOException;

}
