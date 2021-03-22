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
    public void subjectTest() {
        Anime anime = new Anime();
        anime.setStatus("ON");
        anime.setSubject("제목");
        anime.setStartDate("2021-03-25");
        anime.setTime("00:00");
        assertEquals("[03-25] 제목", anime.getSubjectString());
    }
}