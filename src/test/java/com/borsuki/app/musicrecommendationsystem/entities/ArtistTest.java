package com.borsuki.app.musicrecommendationsystem.entities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArtistTest {

    private Artist artistA;
    private Artist artistB;
    private Artist artistC;

    @Before
    public void init() {

        artistA = new Artist();
        artistA.setId("1");
        artistA.setName("Adele");

        artistB = new Artist();
        artistB.setId("2");
        artistB.setName("Dolly Parton");

        artistC = new Artist();
        artistC.setId("3");
        artistC.setName("Adele");
    }

    @Test
    public void equals_True() {
        //when
        boolean result = artistA.equals(artistC);

        //then
        assertThat(result, is(true));
    }

    @Test
    public void equals_False() {
        //when
        boolean result = artistA.equals(artistB);

        //then
        assertThat(result, is(not(true)));
    }

    @Test
    public void equals_Itself() {
        //when
        boolean result = artistA.equals(artistA);

        //then
        assertThat(result, is(true));
    }

    @Test
    public void equals_WrongType() {
        //when
        boolean result = artistA.equals(new Object());

        //then
        assertThat(result, is(not(true)));
    }
}
