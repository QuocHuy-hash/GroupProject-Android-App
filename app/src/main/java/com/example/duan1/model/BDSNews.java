package com.example.duan1.model;

public class BDSNews {
    private int id;
    private String title;
    private String description;
    private double price;
    private String dienTich ;
    private String adress;
    private String tenPhanKhu;
    private String loaiDat;
    private String direction;
    private String soPhongNgu;
    private String soPhongWc;

    public BDSNews() {
    }

    public BDSNews(int id, String title, String description, double price, String dienTich, String adress,
                   String tenPhanKhu, String loaiDat, String soPhongNgu, String soPhongWc) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.dienTich = dienTich;
        this.adress = adress;
        this.tenPhanKhu = tenPhanKhu;
        this.loaiDat = loaiDat;
        this.soPhongNgu = soPhongNgu;
        this.soPhongWc = soPhongWc;
    }

    public BDSNews(int id, String title, String description, double price, String dienTich, String adress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.dienTich = dienTich;
        this.adress = adress;
    }

    public BDSNews(int id, String title, String description, double price, String dienTich, String adress, String tenPhanKhu, String loaiDat, String direction) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.dienTich = dienTich;
        this.adress = adress;
        this.tenPhanKhu = tenPhanKhu;
        this.loaiDat = loaiDat;
        this.direction = direction;
    }

    public String getSoPhongNgu() {
        return soPhongNgu;
    }

    public void setSoPhongNgu(String soPhongNgu) {
        this.soPhongNgu = soPhongNgu;
    }

    public String getSoPhongWc() {
        return soPhongWc;
    }

    public void setSoPhongWc(String soPhongWc) {
        this.soPhongWc = soPhongWc;
    }

    public String getTenPhanKhu() {
        return tenPhanKhu;
    }

    public void setTenPhanKhu(String tenPhanKhu) {
        this.tenPhanKhu = tenPhanKhu;
    }

    public String getLoaiDat() {
        return loaiDat;
    }

    public void setLoaiDat(String loaiDat) {
        this.loaiDat = loaiDat;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDienTich() {
        return dienTich;
    }

    public void setDienTich(String dienTich) {
        this.dienTich = dienTich;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
