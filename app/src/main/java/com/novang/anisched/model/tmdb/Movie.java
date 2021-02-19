package com.novang.anisched.model.tmdb;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.tmdb.common.Genre;
import com.novang.anisched.model.tmdb.common.Production;

import java.util.List;
import java.util.Objects;

/**
 * TMDB API<br/>
 * https://developers.themoviedb.org/3
 *
 * @author Novang (qkdxorjs1002)
 */
public class Movie {

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("budget")
    private int budget;

    @SerializedName("genres")
    private List<Genre> genreList;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<Production> productionCompany;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("revenue")
    private String revenue;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagLine;

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

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        if (originalTitle == null || Objects.equals(originalTitle, "")) {
            return "";
        }
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        if (overview == null || Objects.equals(overview, "")) {
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

    public List<Production> getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(List<Production> productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
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
        if (backdropPath == null || Objects.equals(backdropPath, "")) {
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
        if (posterPath == null) {
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
}
