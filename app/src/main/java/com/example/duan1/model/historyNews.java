package com.example.duan1.model;

public class historyNews {
    private int id;
    private String title_historyNews;
    private String desc_historyNews;
    private String time_historyNews;

    public historyNews(int id, String title_historyNews, String desc_historyNews ,String time_historyNews) {
        this.id = id;
        this.title_historyNews = title_historyNews;
        this.desc_historyNews = desc_historyNews;
        this.time_historyNews = time_historyNews;
    }

    public historyNews() {
    }

    public String getTime_historyNews() {
        return time_historyNews;
    }

    public void setTime_historyNews(String time_historyNews) {
        this.time_historyNews = time_historyNews;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_historyNews() {
        return title_historyNews;
    }

    public void setTitle_historyNews(String title_historyNews) {
        this.title_historyNews = title_historyNews;
    }

    public String getDesc_historyNews() {
        return desc_historyNews;
    }

    public void setDesc_historyNews(String desc_historyNews) {
        this.desc_historyNews = desc_historyNews;
    }
}
