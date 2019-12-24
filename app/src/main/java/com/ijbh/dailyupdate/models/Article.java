package com.ijbh.dailyupdate.models;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName(value="title")
    private String title;

    @SerializedName(value="description")
    private String desc;

    public Article(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
