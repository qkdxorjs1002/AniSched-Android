package com.novang.anisched.repository.tmdb;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.novang.anisched.model.tmdb.Movie;
import com.novang.anisched.model.tmdb.Search;
import com.novang.anisched.model.tmdb.TV;
import com.novang.anisched.model.tmdb.Videos;

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

    private final TMDBService service;

    public TMDBRepository() {
        service = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TMDBService.class);
    }

    /**
     * 작품 검색
     *
     * @param apiKey API Key
     * @param lang language code
     * @param keyword keyword
     * @return Call<List<Anime>>
     */
    public LiveData<Search> search(String apiKey, String lang, String keyword) {
        final MutableLiveData<Search> resultList = new MutableLiveData<>();

        final Call<Search> request = service.search(apiKey, lang, keyword);

        request.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                resultList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Search> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return resultList;
    }

    /**
     * 영화 상세 정보
     *
     * @param apiKey API Key
     * @param lang language code
     * @param id id
     * @return Call<Movie>
     */
    public LiveData<Movie> requestMovie(String apiKey, String lang, int id) {
        final MutableLiveData<Movie> movieDetail = new MutableLiveData<>();

        final Call<Movie> request = service.requestMovie(id, apiKey, lang);

        request.enqueue(new Callback<Movie>() {
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
    public LiveData<TV> requestTv(String apiKey, String lang, int id) {
        final MutableLiveData<TV> tvDetail = new MutableLiveData<>();

        final Call<TV> request = service.requestTv(id, apiKey, lang);

        request.enqueue(new Callback<TV>() {
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

    /**
     * 비디오 정보
     *
     * @param type media type
     * @param id id
     * @param apiKey API Key
     * @param lang language code
     * @return Call<Videos>
     */
    public LiveData<Videos> requestVideos(String apiKey, String lang, String type, int id) {
        final MutableLiveData<Videos> videoList = new MutableLiveData<>();

        final Call<Videos> request = service.requestVideos(type, id, apiKey, lang);

        request.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                videoList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return videoList;
    }

}
