package com.example.readweb.model;

/**
 * Created by pedro on 30/08/2016.
 */
public class Tag {

    private long id;

    private String name;

    public Tag() { }

    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
