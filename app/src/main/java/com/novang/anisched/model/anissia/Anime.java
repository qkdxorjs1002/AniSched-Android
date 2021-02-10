package com.novang.anisched.model.anissia;

import com.google.gson.annotations.SerializedName;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 */
public class Anime {

    @SerializedName("animeNo")
    private int id;

    @SerializedName("status")
    private boolean status;

    @SerializedName("time")
    private String time;

    @SerializedName("subject")
    private String subject;

    @SerializedName("genres")
    private String genres;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("website")
    private String website;

    /**
     * 고유번호
     *
     * @return (1234)
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 상태<br/>
     * '0'일 경우 결방 표시
     *
     * @return (0,1)
     */
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Nullable<br/>
     * 방영 시간/날짜<br/>
     * 날짜일 경우 연도 표시, 공백일 경우 'N/A'
     *
     * @return (00:00/0000-00-00)
     */
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Non-Nullable<br/>
     * 제목
     *
     * @return (타이틀)
     */
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Non-Nullable<br/>
     * 장르<br/>
     * 구분자는 ','
     *
     * @return (장르1,장르2)
     */
    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    /**
     * Nullable<br/>
     * 방영 시작일<br/>
     * 해당 분기 내에 방영 예정의 경우 일자 표시
     *
     * @return (0000-00-00)
     */
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Nullable<br/>
     * 방영 종료일<br/>
     * 해당 분기 내에 방영 종료한 경우 종료 표시
     *
     * @return (0000-00-00)
     */
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Nullable<br/>
     * 공식 사이트 주소
     *
     * @return (https://example.url)
     */
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}