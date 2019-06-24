package com.borsuki.app.musicrecommendationsystem.services;

import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.entities.Likes;

import java.util.List;
import java.util.Optional;

public interface LikeService {
    void giveLike(Likes like) throws Exception;

    List<Likes> getAllLikes(ApplicationUser user_id);

    Optional<Likes> getLike(Long like_id);
}
