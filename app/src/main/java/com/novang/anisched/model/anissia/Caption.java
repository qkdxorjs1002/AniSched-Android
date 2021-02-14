package com.novang.anisched.model.anissia;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 *
 * @author Novang (qkdxorjs1002)
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
        if(episode.equals("0")) {
            return "단편";
        }
        return episode.concat("화");
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

    public String getTimeElapsed() {
        try {
            long up = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA).parse(uploadDate).getTime();
            long now = Calendar.getInstance(Locale.KOREA).getTime().getTime();
            long diff = (now - up) / 60000;

            int elapsedYear = (int) (diff / 525600);
            if(elapsedYear > 0) {
                return String.valueOf(elapsedYear).concat("년 전");
            }

            int elapsedMonth = (int) ((diff % 525600) / 43800);
            if(elapsedMonth > 0) {
                return String.valueOf(elapsedMonth).concat("개월 전");
            }

            int elapsedDay = (int) (((diff % 525600) % 43800) / 1440);
            if(elapsedDay > 0) {
                return String.valueOf(elapsedDay).concat("일 전");
            }

            int elapsedHour = (int) ((((diff % 525600) % 43800) % 1440) / 60);
            if(elapsedHour > 0) {
                return String.valueOf(elapsedHour).concat("시간 전");
            }

            int elapsedMinute = (int) ((((diff % 525600) % 43800) % 1440) % 60);
            if(elapsedMinute > 0) {
                return String.valueOf(elapsedMinute).concat("분 전");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "방금 전";
    }

    /**
     * Nullable<br/>
     * 링크 주소<br/>
     * 값이 없을 경우 자막 준비중 표시
     *
     * @return (https://example.url)
     */
    public String getWebsite() {
        if(website == null) {
            return "";
        }
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
