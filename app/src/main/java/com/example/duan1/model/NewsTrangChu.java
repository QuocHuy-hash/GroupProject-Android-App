package com.example.duan1.model;

import java.util.List;

public class NewsTrangChu {
    private String title,descripsion, fee, time;
    private boolean favorite;
    private List<String> ArrURL;
    public int getsoluonganh(){
        return getArrURL().size();
    }

    public NewsTrangChu() {
    }

    public NewsTrangChu(String title, String descripsion, String fee, String time, boolean favorite, List<String> arrURL) {
        this.title = title;
        this.descripsion = descripsion;
        this.fee = fee;
        this.time = time;
        this.favorite = favorite;
        ArrURL = arrURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripsion() {
        return descripsion;
    }

    public void setDescripsion(String descripsion) {
        this.descripsion = descripsion;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<String> getArrURL() {
        return ArrURL;
    }

    public void setArrURL(List<String> arrURL) {
        ArrURL = arrURL;
    }
}
