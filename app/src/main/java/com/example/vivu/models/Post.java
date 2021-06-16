package com.example.vivu.models;

import java.util.List;

public class Post {

    String detailPost;
    String ownerPost;
    String emailOwnerPost;

    String imagePost;
    String imageUserPost;

    public Post() {
    }


    public Post(String detailPost, String ownerPost, String emailOwnerPost, String imagePost, String imageUserPost) {
        this.detailPost = detailPost;
        this.ownerPost = ownerPost;
        this.emailOwnerPost = emailOwnerPost;
        this.imagePost = imagePost;
        this.imageUserPost = imageUserPost;
    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(String imagePost) {
        this.imagePost = imagePost;
    }

    public String getImageUserPost() {
        return imageUserPost;
    }

    public void setImageUserPost(String imageUserPost) {
        this.imageUserPost = imageUserPost;
    }

    public String getDetailPost() {
        return detailPost;
    }

    public void setDetailPost(String detailPost) {
        this.detailPost = detailPost;
    }

    public String getOwnerPost() {
        return ownerPost;
    }

    public void setOwnerPost(String ownerPost) {
        this.ownerPost = ownerPost;
    }

    public String getEmailOwnerPost() {
        return emailOwnerPost;
    }

    public void setEmailOwnerPost(String emailOwnerPost) {
        this.emailOwnerPost = emailOwnerPost;
    }

}
