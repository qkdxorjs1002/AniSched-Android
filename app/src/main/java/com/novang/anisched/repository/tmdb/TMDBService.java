package com.novang.anisched.repository.tmdb;

import com.novang.anisched.model.tmdb.Movie;
import com.novang.anisched.model.tmdb.Search;
import com.novang.anisched.model.tmdb.TV;
import com.novang.anisched.model.tmdb.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
                        @Query("query") String keyword
    );

    /**
     * 영화 상세 정보
     *
     * @param id id
     * @param apiKey API Key
     * @param lang language code
     * @return Call<Movie>
     */
    @GET("movie/{id}")
    Call<Movie> movieDetail(@Path("id") int id,
                            @Query("api_key") String apiKey,
                            @Query("language") String lang
    );

    /**
     * TV 프로그램 상세 정보
     *
     * @param id id
     * @param apiKey API Key
     * @param lang language code
     * @return Call<TV>
     */
    @GET("tv/{id}")
    Call<TV> tvDetail(@Path("id") int id,
                      @Query("api_key") String apiKey,
                      @Query("language") String lang
    );

    /**
     * 비디오 정보
     *
     * @param type media type
     * @param id id
     * @param apiKey API Key
     * @param lang language code
     * @return Call<Videos>
     */
    @GET("{type}/{id}/videos")
    Call<Videos> videos(@Path("type") String type,
                        @Path("id") int id,
                        @Query("api_key") String apiKey,
                        @Query("language") String lang
    );

}
