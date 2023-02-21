package com.example.facebook_clone.comments;

public class comments_model {
    private String name, date, desc;
    private int likes, comments, shares, dp;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public int getShares() {
        return shares;
    }

    public int getDp() {
        return dp;
    }

    public comments_model(String name, String date, String desc, int likes, int comments, int shares, int dp) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.dp = dp;
    }
}
