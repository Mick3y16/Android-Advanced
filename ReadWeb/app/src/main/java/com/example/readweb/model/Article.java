package com.example.readweb.model;

/**
 * Created by pedro on 30/08/2016.
 */
public class Article {

    private long id;

    private int state;

    private String title;

    private String url;

    private String creationDate;

    public Article() { }

    public Article(String title, String url, int state) {
        this.title = title;
        this.url = url;
        this.state = state;
    }

    public long getId() {
        return this.id;
    }

    public int getState() {
        return this.state;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

}
