package com.example.adrianadam.aplicatieab4systems.Model;

public class MainPageSpot {

    private String name;
    private String country;
    private String id;

    public MainPageSpot(String name, String country, String id) {
        this.name = name;
        this.country = country;
        this.id = id;
    }

    public MainPageSpot(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "MainPageSpot{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
