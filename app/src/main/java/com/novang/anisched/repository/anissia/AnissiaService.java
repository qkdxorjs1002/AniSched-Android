package com.novang.anisched.repository.anissia;


import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.model.anissia.Rank;
import com.novang.anisched.model.anissia.RecentCaption;
import com.novang.anisched.model.anissia.AnissiaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 *
 * @author Novang (qkdxorjs1002)
 */
public interface AnissiaService {

    @GET("autocorrect?q=")
    Call<AnissiaResponse<String[]>> ping();

    /**
     * 스케줄 목록
     *
     * @param week 0~6: 일~토<br/>7: 기타<br/>8: 신작
     * @return Call<List<Anime>>
     */
    @GET("schedule/{week}")
    Call<AnissiaResponse<List<Anime>>> requestSchedule(@Path("week") int week);

    /**
     * 전체 목록
     *
     * @param page int
     * @return Call<List<Anime>>
     */
    @GET("list/{page}")
    Call<AnissiaResponse<List<Anime>>> requestAllSchedule(@Path("page") int page);

    /**
     * 자막 목록
     *
     * @param id 애니메이션 고유번호
     * @return Call<List<Caption>>
     */
    @GET("caption/animeNo/{id}")
    Call<AnissiaResponse<List<Caption>>> requestCaption(@Path("id") int id);

    /**
     * 최근 업로드된 자막 목록
     *
     * @return Call<List<RecentCaption>>
     */
    @GET("caption/recent")
    Call<AnissiaResponse<List<RecentCaption>>> requestRecentCaption();

    /**
     * 애니메이션 정보
     *
     * @param id 애니메이션 고유번호
     * @return Call<Anime>
     */
    @GET("animeNo/{id}")
    Call<AnissiaResponse<Anime>> requestAnime(@Path("id") int id);

    /**
     * 순위 정보
     *
     * @param factor 순위 단위 (day/week/month)
     * @return Call<List<Rank>>
     */
    @GET("rank/{factor}")
    Call<AnissiaResponse<List<Rank>>> requestRanking(@Path("factor") String factor);

    /**
     * 검색 Auto correct
     *
     * @param query 쿼리할 단어
     * @return Call<List<String>>
     */
    @GET("autocorrect")
    Call<AnissiaResponse<List<String>>> requestAutoCorrect(@Query("q") String query);

}
