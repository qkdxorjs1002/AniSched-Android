package com.novang.anisched.repository.anissia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.AnissiaResponse;
import com.novang.anisched.model.anissia.AutoCorrect;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.model.anissia.Rank;
import com.novang.anisched.model.anissia.RecentCaption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

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

    public static HashMap<Integer, String> DAY_OF_WEEK = new HashMap<Integer, String>() {{
        put(0, "일요일");
        put(1, "월요일");
        put(2, "화요일");
        put(3, "수요일");
        put(4, "목요일");
        put(5, "금요일");
        put(6, "토요일");
        put(7, "외전");
        put(8, "신작");
    }};

    private final AnissiaService service;

    public AnissiaRepository() {
        service = new Retrofit.Builder()
                .baseUrl("https://api.anissia.net/anime/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AnissiaService.class);
    }

    public LiveData<Boolean> ping() {
        final MutableLiveData<Boolean> available = new MutableLiveData<>();

        final Call<AnissiaResponse<String[]>> request = service.ping();

        request.enqueue(new Callback<AnissiaResponse<String[]>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<String[]>> call, Response<AnissiaResponse<String[]>> response) {
                available.postValue(response.code() == 200 || response.code() == 204);
            }

            @Override
            public void onFailure(Call<AnissiaResponse<String[]>> call, Throwable throwable) {
                available.postValue(false);
                throwable.printStackTrace();
            }
        });

        return available;
    }

    /**
     * 애니메이션 목록
     *
     * @param week 0~6: 일~토<br/>7: 기타<br/>8: 신작
     * @return MutableLiveData<List<Anime>>
     */
    public LiveData<List<Anime>> requestSchedule(int week) {
        final MutableLiveData<List<Anime>> animeList = new MutableLiveData<>();

        final Call<AnissiaResponse<List<Anime>>> request = service.requestSchedule(week);

        request.enqueue(new Callback<AnissiaResponse<List<Anime>>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<List<Anime>>> call, Response<AnissiaResponse<List<Anime>>> response) {
                animeList.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<AnissiaResponse<List<Anime>>> call, Throwable throwable) {
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
    public LiveData<List<Anime>> requestAllSchedule(int page) {
        final MutableLiveData<List<Anime>> animeList = new MutableLiveData<>();

        final Call<AnissiaResponse<List<Anime>>> request = service.requestAllSchedule(page);

        request.enqueue(new Callback<AnissiaResponse<List<Anime>>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<List<Anime>>> call, Response<AnissiaResponse<List<Anime>>> response) {
                animeList.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<AnissiaResponse<List<Anime>>> call, Throwable throwable) {
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
    public LiveData<List<Caption>> requestCaption(int id) {
        final MutableLiveData<List<Caption>> captionList = new MutableLiveData<>();

        final Call<AnissiaResponse<List<Caption>>> request = service.requestCaption(id);

        request.enqueue(new Callback<AnissiaResponse<List<Caption>>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<List<Caption>>> call, Response<AnissiaResponse<List<Caption>>> response) {
                captionList.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<AnissiaResponse<List<Caption>>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return captionList;
    }

    /**
     * 최근 업로드된 자막 목록
     *
     * @return MutableLiveData<List<RecentCaption>>
     */
    public LiveData<List<RecentCaption>> requestRecentCaption() {
        final MutableLiveData<List<RecentCaption>> captionList = new MutableLiveData<>();

        final Call<AnissiaResponse<List<RecentCaption>>> request = service.requestRecentCaption();

        request.enqueue(new Callback<AnissiaResponse<List<RecentCaption>>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<List<RecentCaption>>> call, Response<AnissiaResponse<List<RecentCaption>>> response) {
                captionList.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<AnissiaResponse<List<RecentCaption>>> call, Throwable throwable) {
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
    public LiveData<Anime> requestAnime(int id) {
        final MutableLiveData<Anime> animeInfo = new MutableLiveData<>();

        final Call<AnissiaResponse<Anime>> request = service.requestAnime(id);

        request.enqueue(new Callback<AnissiaResponse<Anime>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<Anime>> call, Response<AnissiaResponse<Anime>> response) {
                animeInfo.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<AnissiaResponse<Anime>> call, Throwable throwable) {
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
    public LiveData<List<Rank>> requestRanking(String factor) {
        final MutableLiveData<List<Rank>> rankList = new MutableLiveData<>();

        final Call<AnissiaResponse<List<Rank>>> request = service.requestRanking(factor);

        request.enqueue(new Callback<AnissiaResponse<List<Rank>>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<List<Rank>>> call, Response<AnissiaResponse<List<Rank>>> response) {
                rankList.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<AnissiaResponse<List<Rank>>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return rankList;
    }

    /**
     * 검색 Auto correct
     *
     * @param query 쿼리할 단어
     * @return Call<List<String>>
     */
    public LiveData<List<AutoCorrect>> requestAutoCorrect(String query) {
        final MutableLiveData<List<AutoCorrect>> autoList = new MutableLiveData<>();

        final Call<AnissiaResponse<List<String>>> request = service.requestAutoCorrect(query);

        request.enqueue(new Callback<AnissiaResponse<List<String>>>() {
            @Override
            public void onResponse(Call<AnissiaResponse<List<String>>> call, Response<AnissiaResponse<List<String>>> response) {
                if (response.body() == null) {
                    return;
                }
                final ListIterator<String> listIterator = response.body().getData().listIterator();
                final List<AutoCorrect> autoCorrectList = new ArrayList<>();


                while (listIterator.hasNext()) {
                    autoCorrectList.add(new AutoCorrect(listIterator.next()));
                }

                autoList.postValue(autoCorrectList);
            }

            @Override
            public void onFailure(Call<AnissiaResponse<List<String>>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return autoList;
    }

}
