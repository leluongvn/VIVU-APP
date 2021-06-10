package com.example.vivu.models;

import java.util.List;

public class User {
    private String userName;
    private String emailUser;
    private String numberPhoneUser;
    private String passwordUser;
    private Post postUser;

    public User() {
    }

    public User(String userName, String emailUser, String numberPhoneUser, String passwordUser) {
        this.userName = userName;
        this.emailUser = emailUser;
        this.numberPhoneUser = numberPhoneUser;
        this.passwordUser = passwordUser;
    }

    public User(String userName, String emailUser, String numberPhoneUser, String passwordUser, Post postUser) {
        this.userName = userName;
        this.emailUser = emailUser;
        this.numberPhoneUser = numberPhoneUser;
        this.passwordUser = passwordUser;
        this.postUser = postUser;
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
