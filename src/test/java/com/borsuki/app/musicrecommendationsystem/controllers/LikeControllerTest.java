package com.borsuki.app.musicrecommendationsystem.controllers;

import com.borsuki.app.musicrecommendationsystem.dtos.ArtistDto;
import com.borsuki.app.musicrecommendationsystem.dtos.LikesDto;
import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.entities.Likes;
import com.borsuki.app.musicrecommendationsystem.services.ApplicationUserServiceImpl;
import com.borsuki.app.musicrecommendationsystem.services.LikeService;
import com.borsuki.app.musicrecommendationsystem.services.SpotifyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LikeControllerTest {

    @InjectMocks
    private LikeController likeController;
    @Mock
    private LikeService likeService;
    @Spy
    private ModelMapper modelMapper;
    @Mock
    private SpotifyService spotifyService;
    @Mock
    private ApplicationUserServiceImpl applicationUserService;
    @Mock
    private Principal principal;

    private ApplicationUser applicationUser = new ApplicationUser();
    private Likes likes = new Likes();
    private List<Likes> allLikes = Arrays.asList(likes);
    private ArtistDto artistDto = new ArtistDto();

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

        applicationUser.setId((long) 1);
        applicationUser.setEmail("example@gmail.com");
        applicationUser.setPassword("password");
        applicationUser.setUsername("username");

        likes.setId((long) 1);
        likes.setArtist_id("1");
        likes.setUser_id(applicationUser);

        artistDto.setId("1");
        artistDto.setName("Adele");

        doReturn(applicationUser.getUsername()).when(principal).getName();
        doReturn(applicationUser).when(applicationUserService).loadUserByUsername(applicationUser.getUsername());
        doReturn(artistDto).when(spotifyService).getArtist(artistDto.getName());
        doReturn(allLikes).when(likeService).getAllLikes(any(ApplicationUser.class));
        doCallRealMethod().when(modelMapper).map(likes, LikesDto.class);
    }

    @Test
    public void getAllLikes_StatusCheck() throws IOException {
        //when
        ResponseEntity<List<LikesDto>> response = (ResponseEntity<List<LikesDto>>) likeController.getAllLikes(principal);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void giveLike() throws Exception {
        //mock
        ArgumentCaptor<Likes> argument = ArgumentCaptor.forClass(Likes.class);
        doNothing().when(likeService).giveLike(argument.capture());

        //when
        likeController.giveLike(artistDto, principal);

        //then
        assertThat(argument.getValue().getUser_id(), is(applicationUser));
        assertThat(argument.getValue().getArtist_id(), is(artistDto.getId()));
    }
}
