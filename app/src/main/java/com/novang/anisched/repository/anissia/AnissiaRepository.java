package com.novang.anisched.repository.anissia;

import androidx.lifecycle.MutableLiveData;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 */
public class AnissiaRepository {

    Retrofit retrofit;
    AnissiaService service;

    public AnissiaRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://anissia.net/api/anime/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(AnissiaService.class);
    }

    /**
     * 애니메이션 목록
     *
     * @param week 0~6: 일~토<br/>7: 기타<br/>8: 신작
     * @return MutableLiveData<List<Anime>>
     */
    public MutableLiveData<List<Anime>> callSchedule(int week) {
        MutableLiveData<List<Anime>> animeList = new MutableLiveData<>();

        Call<List<Anime>> callSchedule = service.callSchedule(week);

        callSchedule.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                animeList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return animeList;
    }
    
    /**
     * 자막 목록
     *
     * @param id 애니메이션 고유번호
     * @return MutableLiveData<List<Caption>>
     */
    public MutableLiveData<List<Caption>> callCaption(int id) {
        MutableLiveData<List<Caption>> captionList = new MutableLiveData<>();

        Call<List<Caption>> callCaption = service.callCaption(id);

        callCaption.enqueue(new Callback<List<Caption>>() {
            @Override
            public void onResponse(Call<List<Caption>> call, Response<List<Caption>> response) {
                captionList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Caption>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return captionList;
    }
}