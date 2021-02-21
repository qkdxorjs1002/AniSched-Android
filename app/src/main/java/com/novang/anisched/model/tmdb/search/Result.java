package com.novang.anisched.model.tmdb.search;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.base.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TMDB API<br/>
 * https://developers.themoviedb.org/3
 *
 * @author Novang (qkdxorjs1002)
 */
public class Result extends BaseModel {

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genre_ids")
    private List<Integer> genreIdList;

    @SerializedName("id")
    private int id;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("name")
    private String name;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<Integer> getGenreIdList() {
        if (genreIdList == null) {
            return new ArrayList<>();
        }
        return genreIdList;
    }

    public void setGenreIdList(List<Integer> genreIdList) {
        this.genreIdList = genreIdList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOriginalTitle() {
        if (isNullOrEmpty(originalTitle)) {
            return "";
        }
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        if (isNullOrEmpty(overview)) {
            return "줄거리 내용 없음";
        }
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
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
     * 미디어 제목 반환<br/>
     * 타입 상관 없이 반환
     *
     * @return String
     */
    public String getFlexibleName() {
        if (mediaType.equals("tv")) {
            return name;
        } else if (mediaType.equals("movie")) {
            return title;
        }
        return "";
    }

    /**
     * 미디어 제목 반환<br/>
     * 타입 상관 없이 반환
     *
     * @return String
     */
    public String getFlexibleOriginalName() {
        if (mediaType.equals("tv")) {
            return originalName;
        } else if (mediaType.equals("movie")) {
            return originalTitle;
        }
        return "";
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
}
