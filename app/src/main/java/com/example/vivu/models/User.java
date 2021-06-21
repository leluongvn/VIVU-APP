package com.example.vivu.models;

import java.util.List;

public class User {
    private String id;
    private String username;
    private String status;
    private String phone;
    private String password;
    private String email;
    private String image;

    public User() {
    }

    public User(String id, String username, String status, String phone, String password, String email, String image) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
