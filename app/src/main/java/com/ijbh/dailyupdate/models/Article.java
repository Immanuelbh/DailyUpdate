package com.ijbh.dailyupdate.models;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName(value="title")
    private String title;

    @SerializedName(value="description")
    private String desc;

    @SerializedName(value = "urlToImage")
    private String imageUrl;

    @SerializedName(value = "author")
    private String author;

    @SerializedName(value = "url")
    private String source;

    @SerializedName(value = "content")
    private String content;

    @SerializedName(value = "publishedAt")
    private String published;




    public Article(String title, String desc, String imageUrl) {
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }

    public String getContent() {
        return content;
    }

    public String getPublished() {
        return published;
    }
}
