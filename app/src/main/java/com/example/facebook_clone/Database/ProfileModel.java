package com.example.facebook_clone.Database;

public class ProfileModel {
    int user_id;
    String username;
    String password;

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }

    public String getPost() {
        return post;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getShared_post() {
        return shared_post;
    }

    String firstname;
    String lastname;
    String email;
    String mobile;
    String gender;
    String post;
    String profile_image;
    String shared_post;
}