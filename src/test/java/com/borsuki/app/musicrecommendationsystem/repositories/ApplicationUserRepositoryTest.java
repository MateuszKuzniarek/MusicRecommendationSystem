package com.borsuki.app.musicrecommendationsystem.repositories;

import com.borsuki.app.musicrecommendationsystem.entities.ApplicationUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private ApplicationUser applicationUser;

    @Before
    public void init() {

        applicationUser = new ApplicationUser();
        applicationUser.setUsername("user");
        applicationUser.setPassword("password");
        applicationUser.setEmail("example@gmail.com");
    }

    @Test
    public void findByUsername_Found() {
        //mock
        entityManager.merge(applicationUser);
        entityManager.flush();

        //when
        ApplicationUser found = applicationUserRepository.findByUsername(applicationUser.getUsername());

        //then
        assertThat(found.getUsername(), is(applicationUser.getUsername()));
    }

    @Test
    public void findByUsername_NotFound() {
        //when
        ApplicationUser found = applicationUserRepository.findByUsername(applicationUser.getUsername());

        //then
        assertThat(found, is(nullValue()));
    }

    @Test
    public void existsByUsername_True() {
        //mock
        entityManager.merge(applicationUser);
        entityManager.flush();

        //when
        boolean result = applicationUserRepository.existsByUsername(applicationUser.getUsername());

        //then
        assertThat(result, is(true));
    }

    @Test
    public void existsByUsername_False() {
        //when
        boolean result = applicationUserRepository.existsByUsername(applicationUser.getUsername());

        //then
        assertThat(result, is(not(true)));
    }

    @Test
    public void existsByEmail_True() {
        //mock
        entityManager.merge(applicationUser);
        entityManager.flush();

        //when
        boolean result = applicationUserRepository.existsByEmail(applicationUser.getEmail());

        //then
        assertThat(result, is(true));
    }

    @Test
    public void existsByEmail_False() {
        //when
        boolean result = applicationUserRepository.existsByEmail(applicationUser.getEmail());

        //then
        assertThat(result, is(not(true)));
    }
}