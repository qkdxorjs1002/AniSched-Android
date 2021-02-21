package com.novang.anisched.model.anissia;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.base.BaseModel;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 *
 * @author Novang (qkdxorjs1002)
 */
public class Rank extends BaseModel {

    @SerializedName("animeNo")
    private int id;

    @SerializedName("subject")
    private String subject;

    @SerializedName("diff")
    private int diff;

    @SerializedName("hit")
    private int hit;

    @SerializedName("rank")
    private int rank;

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
     * 순위 변동
     *
     * @return (-1)
     */
    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    /**
     * 조회수
     *
     * @return (1000)
     */
    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    /**
     * 순위
     *
     * @return (10)
     */
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
