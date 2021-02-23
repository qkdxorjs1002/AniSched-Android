package com.novang.anisched.repository.github;

import com.novang.anisched.model.github.Release;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Github API<br/>
 * https://docs.github.com
 *
 * @author Novang (qkdxorjs1002)
 */
public interface GithubService {

    /**
     * Release 목록
     *
     * @return Call<List<Release>>
     */
    @GET("repos/{username}/{repo}/releases")
    Call<List<Release>> requestRelease(@Path("username") String username,
                                       @Path("repo") String repo);
    
}
