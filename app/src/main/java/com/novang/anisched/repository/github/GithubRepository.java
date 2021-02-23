package com.novang.anisched.repository.github;

import androidx.lifecycle.MutableLiveData;

import com.novang.anisched.model.github.Release;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Github API<br/>
 * https://docs.github.com
 *
 * @author Novang (qkdxorjs1002)
 */
public class GithubRepository {

    private Retrofit retrofit;
    private GithubService service;

    public String username, repo;

    public GithubRepository(String username, String repo) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubService.class);

        this.username = username;
        this.repo = repo;
    }

    /**
     * Release 목록
     *
     * @return Call<List < Release>>
     */
    public MutableLiveData<List<Release>> requestRelease() {
        MutableLiveData<List<Release>> releaseList = new MutableLiveData<>();

        Call<List<Release>> request = service.requestRelease(username, repo);

        request.enqueue(new Callback<List<Release>>() {
            @Override
            public void onResponse(Call<List<Release>> call, Response<List<Release>> response) {
                releaseList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Release>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return releaseList;
    }

}
