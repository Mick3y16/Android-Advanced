package com.example.volley;

/**
 * Created by pedro on 02/09/2016.
 */
public class Person {

    private String name;

    private String imageURL;

    public Person(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getName() {
        return this.name;
    }

    public String getImageURL() {
        return this.imageURL;
    }
}
