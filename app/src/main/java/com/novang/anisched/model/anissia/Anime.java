package com.novang.anisched.model.anissia;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 *
 * @author Novang (qkdxorjs1002)
 */
public class Anime {

    @SerializedName("animeNo")
    private int id;

    @SerializedName("status")
    private String status;

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

    @SerializedName("captionCount")
    private int captionCount;

    @SerializedName("captions")
    private List<Caption> captionList;

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
     * 'OFF'일 경우 결방 표시
     *
     * @return boolean
     */
    public String getStatus() {
        return status;
    }

    public boolean isStatus() {
        return status.equals("ON");
    }

    public void setStatus(String status) {
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
        if (time == null || Objects.equals(time, "")) {
            return "N/A";
        }
        return time.replace("-99", "");
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
     * 구분자는 ',' 반환시 '/'로 치환
     *
     * @return (장르1,장르2)
     */
    public String getGenres() {
        return genres.replace(",", "/");
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
        if (startDate == null || Objects.equals(startDate, "")) {
            return "미정";
        }
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
        if (endDate == null || Objects.equals(endDate, "")) {
            return "미정";
        }
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
        if (website == null || Objects.equals(website, "")) {
            return "";
        }
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 자막 수
     *
     * @return (1)
     */
    public int getCaptionCount() {
        return captionCount;
    }

    public void setCaptionCount(int captionCount) {
        this.captionCount = captionCount;
    }

    /**
     * Nullable<br/>
     * 자막 목록
     *
     * @return (List<Caption>)
     */
    public List<Caption> getCaptionList() {
        return captionList;
    }

    public void setCaptionList(List<Caption> captionList) {
        this.captionList = captionList;
    }

    /**
     * 장르 목록 반환<br/>
     * 장르 문자열을 리스트로 반환
     *
     * @return List<String>
     */
    public List<String> getGenreList() {
        StringTokenizer stringTokenizer = new StringTokenizer(genres, ",");
        List<String> genreList = new ArrayList<>();

        while(stringTokenizer.hasMoreElements()) {
            genreList.add((String)stringTokenizer.nextElement());
        }

        return genreList;
    }
}
