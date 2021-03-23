package com.novang.anisched;

import com.novang.anisched.model.anissia.Anime;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void extraTest() {
        Anime anime = new Anime();
        anime.setStatus("ON");
        anime.setStartDate("2019-03-25");
        anime.setEndDate("2020-03-25");
        anime.setTime("00:00");
        assertEquals("종영", anime.getExtraInfo());
        anime.setStartDate("2089-03-25");
        anime.setEndDate("2099-03-25");
        assertEquals("03-25", anime.getExtraInfo());
    }
}