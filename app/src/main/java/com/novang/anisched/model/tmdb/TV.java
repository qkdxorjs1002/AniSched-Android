package com.novang.anisched.model.tmdb;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.base.BaseModel;
import com.novang.anisched.model.tmdb.common.Genre;
import com.novang.anisched.model.tmdb.tv.Episode;
import com.novang.anisched.model.tmdb.tv.Network;
import com.novang.anisched.model.tmdb.common.Production;
import com.novang.anisched.model.tmdb.tv.Season;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * TMDB API<br/>
 * https://developers.themoviedb.org/3
 *
 * @author Novang (qkdxorjs1002)
 */
public class TV extends BaseModel {

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRuntime;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genres")
    private List<Genre> genreList;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("in_production")
    private boolean inProduction;

    @SerializedName("languages")
    private List<String> languageList;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("last_episode_to_air")
    private Episode lastEpisodeToAir;

    @SerializedName("name")
    private String name;

    @SerializedName("networks")
    private List<Network> networkList;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<Production> productionCompany;

    @SerializedName("seasons")
    private List<Season> seasonList;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagLine;

    @SerializedName("type")
    private String type;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<Integer> getEpisodeRuntime() {
        return episodeRuntime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRuntime = episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public List<String> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<String> languageList) {
        this.languageList = languageList;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public Episode getLastEpisodeToAir() {
        return lastEpisodeToAir;
    }

    public void setLastEpisodeToAir(Episode lastEpisodeToAir) {
        this.lastEpisodeToAir = lastEpisodeToAir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(List<Network> networkList) {
        this.networkList = networkList;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        if (isNullOrEmpty(originalName)) {
            return "";
        }
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        if (isNullOrEmpty(overview)) {
            return "줄거리 내용 없음";
        }
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Production> getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(List<Production> productionCompany) {
        this.productionCompany = productionCompany;
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * 배너 이미지 URL<br/>
     * 배너 이미지 없으면 포스터 이미지 반환
     *
     * @param width w200~500 / original
     * @return String URL
     */
    public String getBackdropURL(String width) {
        if (isNullOrEmpty(backdropPath)) {
            return getPosterURL(width);
        }
        return "https://image.tmdb.org/t/p/".concat(width).concat(backdropPath);
    }

    /**
     * 이미지 URL
     *
     * @param width w200~500 / original
     * @return String URL
     */
    public String getPosterURL(String width) {
        if (isNullOrEmpty(posterPath)) {
            return "";
        }
        return "https://image.tmdb.org/t/p/".concat(width).concat(posterPath);
    }

    /**
     * 평가 점수 반환<br/>
     * 100점 만점
     *
     * @return int
     */
    public int getVoteDecimal() {
        return (int)(voteAverage * 10);
    }

    public String getStringNetworkList() {
        if (networkList != null) {
            Iterator<Network> iterator = networkList.iterator();
            StringBuilder string = new StringBuilder();

            while (iterator.hasNext()) {
                string.append(iterator.next().getName());

                if (iterator.hasNext()) {
                    string.append(" • ");
                }
            }

            return string.toString();
        }
        return "";
    }

    public String getStringProductionList() {
        if (productionCompany != null) {
            Iterator<Production> iterator = productionCompany.iterator();
            StringBuilder string = new StringBuilder();

            while (iterator.hasNext()) {
                string.append(iterator.next().getName());

                if (iterator.hasNext()) {
                    string.append(" • ");
                }
            }

            return string.toString();
        }
        return "";
    }

    public int getRuntime() {
        if (episodeRuntime != null) {
            if (episodeRuntime.size() != 0) {
                return episodeRuntime.get(0);
            }
        }
        return 0;
    }
}
