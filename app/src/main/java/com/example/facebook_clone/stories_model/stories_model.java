package com.example.facebook_clone.stories_model;

public class stories_model {
    private String name;
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

    public stories_model(String name, int dp, int post){
        this.name = name;
        this.dp = dp;
        this.post = post;
    }
}
