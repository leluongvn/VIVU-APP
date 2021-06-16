package com.example.vivu.models;

import java.util.List;

public class User {
    private String userName;
    private String emailUser;
    private String numberPhoneUser;
    private String passwordUser;
    private Post postUser;
    private String imageUser;

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public User() {
    }

    public User(String userName, String emailUser, String numberPhoneUser, String passwordUser, String imageUser) {
        this.userName = userName;
        this.emailUser = emailUser;
        this.numberPhoneUser = numberPhoneUser;
        this.passwordUser = passwordUser;
        this.imageUser = imageUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getNumberPhoneUser() {
        return numberPhoneUser;
    }

    public void setNumberPhoneUser(String numberPhoneUser) {
        this.numberPhoneUser = numberPhoneUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Post getPostUser() {
        return postUser;
    }

    public void setPostUser(Post postUser) {
        this.postUser = postUser;
    }
}
