package com.novang.anisched.repository.anissia;


import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.model.anissia.Rank;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 *
 * @author Novang (qkdxorjs1002)
 */
public interface AnissiaService {

    /**
     * 스케줄 목록
     *
     * @param week 0~6: 일~토<br/>7: 기타<br/>8: 신작
     * @return Call<List<Anime>>
     */
    @GET("schedule/{week}")
    Call<List<Anime>> callSchedule(@Path("week") int week);

    /**
     * 전체 목록
     *
     * @param page int
     * @return Call<List<Anime>>
     */
    @GET("list/{page}")
    Call<List<Anime>> callAllList(@Path("page") int page);

    /**
     * 자막 목록
     *
     * @param id 애니메이션 고유번호
     * @return Call<List<Caption>>
     */
    @GET("caption/animeNo/{id}")
    Call<List<Caption>> callCaption(@Path("id") int id);

    /**
     * 애니메이션 정보
     *
     * @param id 애니메이션 고유번호
     * @return Call<Anime>
     */
    @GET("animeNo/{id}")
    Call<Anime> callAnimeInfo(@Path("id") int id);

    /**
     * 순위 정보
     *
     * @param factor 순위 단위 (day/week/month)
     * @return Call<List<Rank></Rank>>
     */
    @GET("rank/{factor}")
    Call<List<Rank>> callRankInfo(@Path("factor") String factor);
    
}
