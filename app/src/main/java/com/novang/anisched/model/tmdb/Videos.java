package com.novang.anisched.model.tmdb;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.base.BaseModel;
import com.novang.anisched.model.tmdb.child.common.Video;

import java.util.List;

public class Videos extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Video> videoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
