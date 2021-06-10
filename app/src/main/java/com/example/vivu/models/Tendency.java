package com.example.vivu.models;

public class Tendency {


    public Tendency() {
    }

    String imagesCity;
    String cityName;

    public Tendency(String imagesCity, String cityName) {
        this.imagesCity = imagesCity;
        this.cityName = cityName;
    }

    public String getImagesCity() {
        return imagesCity;
    }

    public void setImagesCity(String imagesCity) {
        this.imagesCity = imagesCity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
