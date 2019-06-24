package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.externalAPI.SpotifyAPI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

public class SpotifyServiceImplTest {

    @InjectMocks
    private SpotifyServiceImpl spotifyService;
    @Mock
    private SpotifyAPI spotifyAPI;
    @Mock
    private ModelMapper modelMapper;

    private String token = "token", artistId = "1", artistName = "Adele";

    @Before
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);

        doReturn(token).when(spotifyAPI).getAccessToken();
        doReturn(artistId).when(spotifyAPI).searchItem(artistName, token);
        doReturn(artistName).when(spotifyAPI).getArtist(artistId, token);

    }

    @Test
    public void getArtist() throws Exception {
        //when
        ArtistDto artistDto = spotifyService.getArtist(artistName);

        //then
        assertThat(artistDto.getId(), is(artistId));
    }
}
