package com.novang.anisched.repository.anissia;

import androidx.lifecycle.MutableLiveData;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.model.anissia.Rank;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 *
 * @author Novang (qkdxorjs1002)
 */
public class AnissiaRepository {

    public static final class FACTOR {
        public static final String DAY = "day";
        public static final String WEEK = "week";
        public static final String MONTH = "month";
    }

    private Retrofit retrofit;
    private AnissiaService service;

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
    public MutableLiveData<List<Anime>> requestSchedule(int week) {
        MutableLiveData<List<Anime>> animeList = new MutableLiveData<>();

        Call<List<Anime>> request = service.requestSchedule(week);

        request.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                animeList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return animeList;
    }

    /**
     * 애니메이션 목록
     *
     * @param page int
     * @return MutableLiveData<List<Anime>>
     */
    public MutableLiveData<List<Anime>> requestAllSchedule(int page) {
        MutableLiveData<List<Anime>> animeList = new MutableLiveData<>();

        Call<List<Anime>> request = service.requestAllSchedule(page);

        request.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                animeList.postValue(response.body());
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
    public MutableLiveData<List<Caption>> requestCaption(int id) {
        MutableLiveData<List<Caption>> captionList = new MutableLiveData<>();

        Call<List<Caption>> request = service.requestCaption(id);

        request.enqueue(new Callback<List<Caption>>() {
            @Override
            public void onResponse(Call<List<Caption>> call, Response<List<Caption>> response) {
                captionList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Caption>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return captionList;
    }

    /**
     * 애니메이션 정보
     *
     * @param id int
     * @return MutableLiveData<Anime>
     */
    public MutableLiveData<Anime> requestAnime(int id) {
        MutableLiveData<Anime> animeInfo = new MutableLiveData<>();

        Call<Anime> request = service.requestAnime(id);

        request.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                animeInfo.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return animeInfo;
    }

    /**
     * 순위 정보
     *
     * @param factor (day/week/month)
     * @return MutableLiveData<List<Rank>>
     */
    public MutableLiveData<List<Rank>> requestRanking(String factor) {
        MutableLiveData<List<Rank>> rankList = new MutableLiveData<>();

        Call<List<Rank>> request = service.requestRanking(factor);

        request.enqueue(new Callback<List<Rank>>() {
            @Override
            public void onResponse(Call<List<Rank>> call, Response<List<Rank>> response) {
                rankList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Rank>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return rankList;
    }

}
