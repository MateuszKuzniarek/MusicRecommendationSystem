package com.borsuki.app.musicrecommendationsystem.services;


import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.entities.Likes;
import com.borsuki.app.musicrecommendationsystem.repositories.ApplicationUserArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final ApplicationUserArtistRepository applicationUserArtistRepository;

    @Autowired
    public LikeServiceImpl(ApplicationUserArtistRepository applicationUserArtistRepository) {
        this.applicationUserArtistRepository = applicationUserArtistRepository;
    }

    @Override
    public void giveLike(Likes like) {
        if (!applicationUserArtistRepository.findByArtist_idAndUser_id(like.getUser_id(), like.getArtist_id()).isEmpty()) {
            applicationUserArtistRepository.delete(applicationUserArtistRepository.findByArtist_idAndUser_id(like.getUser_id(), like.getArtist_id()).get(0));
        } else {
            applicationUserArtistRepository.save(like);
        }

    }

    @Override
    public List<Likes> getAllLikes(ApplicationUser user_id) {
        List<Likes> result = new ArrayList();
        applicationUserArtistRepository.findAllByUser_id(user_id).forEach(result::add);
        return result;
    }

    @Override
    public Optional<Likes> getLike(Long like_id) {
        return applicationUserArtistRepository.findById(like_id);
    }
}
