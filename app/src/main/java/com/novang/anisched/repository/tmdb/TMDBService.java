package com.novang.anisched.repository.tmdb;

import com.novang.anisched.model.tmdb.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * TMDB API<br/>
 * https://developers.themoviedb.org/3
 *
 * @author Novang (qkdxorjs1002)
 */
public interface TMDBService {

    /**
     * 작품 검색
     *
     * @param apiKey API Key
     * @param lang language code
     * @param keyword keyword
     * @return Call<List<Anime>>
     */
    @GET("search/multi")
    Call<Search> search(@Query("api_key") String apiKey,
                                @Query("language") String lang,
                                @Query("query") String keyword);

}
