package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.entities.Artist;
import com.borsuki.app.musicrecommendationsystem.externalAPI.SpotifyAPI;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class SpotifyServiceImpl implements SpotifyService {


    private SpotifyAPI spotifyAPI;
    private ModelMapper modelMapper;

    private String accessToken;

    @Autowired
    public SpotifyServiceImpl(SpotifyAPI spotifyAPI, ModelMapper modelMapper) {
        this.spotifyAPI = spotifyAPI;
        this.modelMapper = modelMapper;

    }

    @Override
    public ArtistDto getArtist(String artistName) throws Exception {
        accessToken = spotifyAPI.getAccessToken();
        String artistId = spotifyAPI.searchItem(artistName, accessToken);
        if (artistId.equals(""))
            throw new Exception();
        artistName = spotifyAPI.getArtist(artistId, accessToken);
        ArtistDto artist = new ArtistDto(artistId, artistName);
        return artist;
    }
    @Override
    public ArtistDto getArtistById(String artistId) throws IOException {
        accessToken = spotifyAPI.getAccessToken();
        ArtistDto result = new ArtistDto();
        result.setId(artistId);
        result.setName(spotifyAPI.getArtist(artistId,accessToken));
        return result;
    }

    @Override
    public List<ArtistDto> getRelatedArtists(String artistId) throws IOException {
        accessToken = spotifyAPI.getAccessToken();
        Map<String, String> artists = spotifyAPI.getRelated(artistId, accessToken);
        List<ArtistDto> lists = artists.entrySet().stream()
                .map(entry -> new ArtistDto(entry.getKey(), entry.getValue())).collect(Collectors.toList());

        return lists;
    }
}
