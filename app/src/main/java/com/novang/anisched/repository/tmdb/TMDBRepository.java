package com.novang.anisched.repository.tmdb;

import androidx.lifecycle.MutableLiveData;

import com.novang.anisched.model.tmdb.Movie;
import com.novang.anisched.model.tmdb.Search;
import com.novang.anisched.model.tmdb.TV;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TMDB API<br/>
 * https://developers.themoviedb.org/3
 *
 * @author Novang (qkdxorjs1002)
 */
public class TMDBRepository {

    Retrofit retrofit;
    TMDBService service;

    public TMDBRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TMDBService.class);
    }

    /**
     * 작품 검색
     *
     * @param apiKey API Key
     * @param lang language code
     * @param keyword keyword
     * @return Call<List<Anime>>
     */
    public MutableLiveData<Search> search(String apiKey, String lang, String keyword) {
        MutableLiveData<Search> searchList = new MutableLiveData<>();

        Call<Search> callSchedule = service.search(apiKey, lang, keyword);

        callSchedule.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                searchList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Search> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return searchList;
    }

    /**
     * 영화 상세 정보
     *
     * @param apiKey API Key
     * @param lang language code
     * @param id id
     * @return Call<Movie>
     */
    public MutableLiveData<Movie> requestMovieDetail(String apiKey, String lang, int id) {
        MutableLiveData<Movie> movieDetail = new MutableLiveData<>();

        Call<Movie> callMovieDetail = service.movieDetail(id, apiKey, lang);

        callMovieDetail.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieDetail.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return movieDetail;
    }

    /**
     * TV 상세 정보
     *
     * @param apiKey API Key
     * @param lang language code
     * @param id id
     * @return Call<TV>
     */
    public MutableLiveData<TV> requestTVDetail(String apiKey, String lang, int id) {
        MutableLiveData<TV> tvDetail = new MutableLiveData<>();

        Call<TV> callMovieDetail = service.tvDetail(id, apiKey, lang);

        callMovieDetail.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                tvDetail.postValue(response.body());
            }

            @Override
            public void onFailure(Call<TV> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return tvDetail;
    }

}
