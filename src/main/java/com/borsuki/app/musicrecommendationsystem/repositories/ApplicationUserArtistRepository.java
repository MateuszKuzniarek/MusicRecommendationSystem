package com.borsuki.app.musicrecommendationsystem.repositories;


import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.entities.Likes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationUserArtistRepository extends CrudRepository<Likes, Long> {

    @Query("from Likes where user_id = :user_id")
    List<Likes> findAllByUser_id(@Param("user_id") ApplicationUser user_id);

    @Query("from Likes where user_id = :user_id and artist_id = :artist_id")
    List<Likes> findByArtist_idAndUser_id(@Param("user_id") ApplicationUser user_id, @Param("artist_id") String artist_id);


    @Override
    void delete(Likes likes);
}
