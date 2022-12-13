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
    private int idUser;
    private String nameUser;
    private String tenDanhMuc;
    private String date;
    private String image;

    public thoiTrangNews() {
    }

    public thoiTrangNews(int id, String titlePost, String descriptionPost, String typeproduct,
                         double price, String address, int idUser, String nameUser,
                         String tenDanhMuc, String date, String image) {
        this.id = id;
        this.titlePost = titlePost;
        this.descriptionPost = descriptionPost;
        this.typeproduct = typeproduct;
        this.price = price;
        this.address = address;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.tenDanhMuc = tenDanhMuc;
        this.date = date;
        this.image = image;
    }

    public thoiTrangNews(int id, String titlePost, String descriptionPost, String typeproduct
            , double price, String address, int idUser, String nameUser, String tenDanhMuc , String date) {
        this.id = id;
        this.titlePost = titlePost;
        this.descriptionPost = descriptionPost;
        this.typeproduct = typeproduct;
        this.price = price;
        this.address = address;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.tenDanhMuc = tenDanhMuc;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
