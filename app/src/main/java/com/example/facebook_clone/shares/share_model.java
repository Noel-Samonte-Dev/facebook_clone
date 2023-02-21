package com.example.facebook_clone.shares;

public class share_model {
    private String name;

    public String getName() {
        return name;
    }

    public int getDp() {
        return dp;
    }

    private int dp;

    public share_model(String name, int dp) {
        this.name = name;
        this.dp = dp;
    }
}
