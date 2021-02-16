package com.novang.anisched.model.tmdb.tv;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * TMDB API<br/>
 * https://developers.themoviedb.org/3
 *
 * @author Novang (qkdxorjs1002)
 */
public class Network {

    @SerializedName("name")
    private String firstAirDate;

    @SerializedName("id")
    private int id;

    @SerializedName("logo_path")
    private String logoPath;

    @SerializedName("origin_country")
    private String originCountry;

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    /**
     * 이미지 URL
     *
     * @param width w200~500 / original
     * @return String URL
     */
    public String getLogoURL(String width) {
        if (logoPath == null || Objects.equals(logoPath, "")) {
            return "";
        }
        return "https://image.tmdb.org/t/p/".concat(width).concat(logoPath);
    }

}
