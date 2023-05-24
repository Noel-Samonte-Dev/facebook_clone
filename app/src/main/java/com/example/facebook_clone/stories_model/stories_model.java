package com.example.facebook_clone.stories_model;

public class stories_model {
    private String name, user_id;
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

    public String getUser_id() {
        return user_id;
    }

    public stories_model(String name, int dp, int post, String user_id){
        this.name = name;
        this.dp = dp;
        this.post = post;
        this.user_id = user_id;
    }
}
