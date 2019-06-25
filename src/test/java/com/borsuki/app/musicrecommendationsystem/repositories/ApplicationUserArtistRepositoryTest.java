package com.borsuki.app.musicrecommendationsystem.repositories;

import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import com.borsuki.app.musicrecommendationsystem.entities.Likes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationUserArtistRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserArtistRepository applicationUserArtistRepository;

    private Likes likesA, likesB;
    private ApplicationUser applicationUser;

    @Before
    public void init() {
        likesA = new Likes();
        likesB = new Likes();
        applicationUser = new ApplicationUser();

        applicationUser.setUsername("user");
        applicationUser.setPassword("password");
        applicationUser.setEmail("example@gmail.com");
        applicationUser.setId((long) 1);

        likesA.setArtist_id("1");
        likesA.setUser_id(applicationUser);
        likesA.setId((long) 1);

        likesB.setArtist_id("2");
        likesB.setUser_id(applicationUser);
        likesB.setId((long) 2);
    }

    @Test
    public void findAllByUser_Id_Found() {
        //mock
        entityManager.merge(applicationUser);
        entityManager.merge(likesA);
        entityManager.merge(likesB);
        entityManager.flush();

        //when
        List<Likes> found = applicationUserArtistRepository.findAllByUser_id(applicationUser);

        //then
        assertThat(found.size(), is(2));
    }

    @Test
    public void findByArtist_IdAndUser_Id_Found() {
        //mock
        entityManager.merge(applicationUser);
        entityManager.merge(likesA);
        entityManager.merge(likesB);
        entityManager.flush();

        //when
        List<Likes> found = applicationUserArtistRepository.findByArtist_idAndUser_id(applicationUser, likesA.getArtist_id());

        //then
        assertThat(found.size(), is(1));
    }

    @Test
    public void findByArtist_IdAndUser_Id_NotFound() {
        //when
        List<Likes> found = applicationUserArtistRepository.findByArtist_idAndUser_id(applicationUser, likesA.getArtist_id());

        //then
        assertThat(found.isEmpty(), is(true));
    }

    @Test
    public void delete() {
        //mock
        entityManager.merge(likesA);
        entityManager.flush();

        //when
        applicationUserArtistRepository.delete(likesA);
        Likes found = entityManager.find(Likes.class, (long) 1);

        //then
        assertThat(found, is(nullValue()));
    }
}
