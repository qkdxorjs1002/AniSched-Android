package com.novang.anisched.model.anissia;

import com.google.gson.annotations.SerializedName;

/**
 * 애니시아 API<br/>
 * https://github.com/anissia-net/document
 *
 * @author Novang (qkdxorjs1002)
 */
public class RecentCaption extends Caption {

    @SerializedName("animeNo")
    private int id;

    @SerializedName("subject")
    private String subject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
