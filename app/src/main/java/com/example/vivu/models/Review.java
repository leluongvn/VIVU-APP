package com.example.vivu.models;

public class Review {
    String titleReview ;
    String detailReview;
    String imageReview;

    public String getTitleReview() {
        return titleReview;
    }

    public Review(String titleReview, String detailReview, String imageReview) {
        this.titleReview = titleReview;
        this.detailReview = detailReview;
        this.imageReview = imageReview;
    }

    public void setTitleReview(String titleReview) {
        this.titleReview = titleReview;
    }

    public String getDetailReview() {
        return detailReview;
    }

    public void setDetailReview(String detailReview) {
        this.detailReview = detailReview;
    }

    public String getImageReview() {
        return imageReview;
    }

    public void setImageReview(String imageReview) {
        this.imageReview = imageReview;
    }
}
