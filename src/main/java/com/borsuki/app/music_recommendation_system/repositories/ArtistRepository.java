package com.borsuki.app.music_recommendation_system.repositories;

import com.borsuki.app.music_recommendation_system.entities.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
}
