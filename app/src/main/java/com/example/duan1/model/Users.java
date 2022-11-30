package com.example.duan1.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {

    private int id;
    private String name;
    private String email;
    private String sdt;
    private String image;
    private String password;

    public Users() {
    }

    public Users(int id, String name, String email, String sdt, String image, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sdt = sdt;
        this.image = image;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("email", email);
        result.put("sdt", sdt);
        result.put("image", image);
        result.put("password", password);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapImage() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("email", email);
        result.put("sdt", sdt);
        result.put("image", image);
        result.put("password", password);

        return result;
    }
}
