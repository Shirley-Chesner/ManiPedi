package com.example.manipedi.DB;

import java.io.Serializable;

public class NailPolish implements Serializable {
    private Integer id;
    private String brand;
    private String name;
    private String description;
    private String image;
    private String apiUrl;

    public NailPolish(Integer id, String brand, String name, String description, String image, String apiUrl) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.image = image;
        this.apiUrl = apiUrl;
    }

    public String getBrand() {
        return brand.trim();
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name.trim();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return "https://" + image;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String toString() {
        return name.trim() + " - " + brand.trim();
    }
}
