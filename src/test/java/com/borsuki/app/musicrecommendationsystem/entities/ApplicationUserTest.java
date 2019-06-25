package com.borsuki.app.musicrecommendationsystem.entities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationUserTest {

    private ApplicationUser applicationUserA;
    private ApplicationUser applicationUserB;
    private ApplicationUser applicationUserC;

    @Before
    public void init() {

        applicationUserA = new ApplicationUser();
        applicationUserA.setId((long) 1);
        applicationUserA.setUsername("robert");
        applicationUserA.setPassword("passwordA");

        applicationUserB = new ApplicationUser();
        applicationUserB.setId((long) 2);
        applicationUserB.setUsername("romek");
        applicationUserB.setPassword("passwordB");

        applicationUserC = new ApplicationUser();
        applicationUserC.setId((long) 3);
        applicationUserC.setUsername("robert");
        applicationUserC.setPassword("passwordC");
    }

    @Test
    public void equals_True() {
        //when
        boolean result = applicationUserA.equals(applicationUserC);

        //then
        assertThat(result, is(true));
    }

    @Test
    public void equals_False() {
        //when
        boolean result = applicationUserA.equals(applicationUserB);

        //then
        assertThat(result, is(not(true)));
    }

    @Test
    public void equals_Itself() {
        //when
        boolean result = applicationUserA.equals(applicationUserA);

        //then
        assertThat(result, is(true));
    }

    @Test
    public void equals_WrongType() {
        //when
        boolean result = applicationUserA.equals(new Object());

        //then
        assertThat(result, is(not(true)));
    }
}
