package com.example.facebook_clone.post_model;

public class post_model {
    private String name, date, description;
    private int dp, post;

    public String getName() {
        return name;
    }

    public int getDp() {
        return dp;
    }

    public int getPost() {
        return post;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public post_model(String name, String date, String description, int dp, int post){
        this.name = name;
        this.date = date;
        this.description = description;
        this.dp = dp;
        this.post = post;
    }
}
