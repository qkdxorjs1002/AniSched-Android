package com.novang.anisched.model.anissia;

import com.google.gson.annotations.SerializedName;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 */
public class Caption {

    @SerializedName("episode")
    private String episode;

    @SerializedName("updDt")
    private String uploadDate;

    @SerializedName("website")
    private String website;

    @SerializedName("name")
    private String author;

    /**
     * Non-Nullable<br/>
     * 회차<br/>
     * 값이 0일 경우 단편 표시
     *
     * @return (0/12/12.3)
     */
    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    /**
     * Non-Nullable<br/>
     * 등록 시간
     *
     * @return (0000-00-00 00:00:00)
     */
    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * Nullable<br/>
     * 링크 주소<br/>
     * 값이 없을 경우 자막 준비중 표시
     *
     * @return (https://example.url)
     */
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Non-Nullable<br/>
     * 제작자 이름
     *
     * @return (이름)
     */
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
