package com.novang.anisched.model.github;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.base.BaseModel;

public class Asset extends BaseModel {

    @SerializedName("browser_download_url")
    private String downloadUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("state")
    private String state;

    public String getDownloadUrl() {
        if (isNullOrEmpty(downloadUrl)) {
            return "";
        }
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getName() {
        if (isNullOrEmpty(downloadUrl)) {
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        if (isNullOrEmpty(state)) {
            return "";
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
