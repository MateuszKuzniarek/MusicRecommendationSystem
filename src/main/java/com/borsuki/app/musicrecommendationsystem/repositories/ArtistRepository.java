package com.borsuki.app.musicrecommendationsystem.repositories;

import com.borsuki.app.musicrecommendationsystem.entities.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
}
