package com.example.duan1.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class thoiTrangNews {
    private int id;
    private String titlePost;
    private String descriptionPost;
    private String typeproduct;

    private double price;
    private String address;

    public thoiTrangNews() {
    }

    public thoiTrangNews(int id, String titlePost, String descriptionPost, String typeproduct, double price, String address) {
        this.id = id;
        this.titlePost = titlePost;
        this.descriptionPost = descriptionPost;
        this.typeproduct = typeproduct;
        this.price = price;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public void setTitlePost(String titlePost) {
        this.titlePost = titlePost;
    }

    public String getDescriptionPost() {
        return descriptionPost;
    }

    public void setDescriptionPost(String descriptionPost) {
        this.descriptionPost = descriptionPost;
    }

    public String getTypeproduct() {
        return typeproduct;
    }

    public void setTypeproduct(String typeproduct) {
        this.typeproduct = typeproduct;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
