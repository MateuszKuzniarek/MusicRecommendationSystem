package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.entities.Artist;
import com.borsuki.app.musicrecommendationsystem.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.MANDATORY)
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {this.artistRepository = artistRepository;}

    @Override
    public List<Artist> getAllArtists() {
        List<Artist> result = new ArrayList<>();
        artistRepository.findAll().forEach(result::add);
        return result;
    }
}
