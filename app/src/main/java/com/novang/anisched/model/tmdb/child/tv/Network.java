package com.novang.anisched.model.tmdb.child.tv;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.base.BaseModel;

/**
 * TMDB API<br/>
 * https://developers.themoviedb.org/3
 *
 * @author Novang (qkdxorjs1002)
 */
public class Network extends BaseModel {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("logo_path")
    private String logoPath;

    @SerializedName("origin_country")
    private String originCountry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (isNullOrEmpty(logoPath)) {
            return "";
        }
        return "https://image.tmdb.org/t/p/".concat(width).concat(logoPath);
    }

}
