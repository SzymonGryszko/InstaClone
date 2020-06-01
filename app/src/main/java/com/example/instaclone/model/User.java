package com.example.instaclone.model;

public class User {

    private String user_id;
    private String email;
    private long phone_number;
    private String username;

    public User(String user_id, String email, long phone_number, String username) {
        this.user_id = user_id;
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
    }

    public User(){

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
