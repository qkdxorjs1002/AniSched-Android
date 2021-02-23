package com.novang.anisched.model.github;

import com.google.gson.annotations.SerializedName;
import com.novang.anisched.model.base.BaseModel;

import java.util.List;

public class Release extends BaseModel {

    @SerializedName("assets")
    private List<Asset> assetList;

    @SerializedName("body")
    private String body;

    @SerializedName("prerelease")
    private boolean pre;

    @SerializedName("tag_name")
    private String tagName;

    public List<Asset> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
    }

    public String getBody() {
        if (isNullOrEmpty(body)) {
            return "";
        }
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPre() {
        return pre;
    }

    public void setPre(boolean pre) {
        this.pre = pre;
    }

    public String getTagName() {
        if (isNullOrEmpty(tagName)) {
            return "";
        }
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
