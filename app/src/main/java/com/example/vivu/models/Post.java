package com.example.vivu.models;

import java.util.List;

public class Post {

    String detailPost;
    String Images;
    List<Comment> commentList;

    public Post(String detailPost, String images) {

        this.detailPost = detailPost;
        Images = images;

    }


    public String getDetailPost() {
        return detailPost;
    }

    public void setDetailPost(String detailPost) {
        this.detailPost = detailPost;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
