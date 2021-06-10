package com.example.vivu.models;

public class Comment {
    String detailComment;
    String imageComment;


    public Comment(String detailComment, String imageComment) {
        this.detailComment = detailComment;
        this.imageComment = imageComment;
    }

    public String getDetailComment() {
        return detailComment;
    }

    public void setDetailComment(String detailComment) {
        this.detailComment = detailComment;
    }

    public String getImageComment() {
        return imageComment;
    }

    public void setImageComment(String imageComment) {
        this.imageComment = imageComment;
    }
}
