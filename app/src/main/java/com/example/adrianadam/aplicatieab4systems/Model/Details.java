package com.example.adrianadam.aplicatieab4systems.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("longitude")
    @Expose
    private float longitude;
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("windProbability")
    @Expose
    private float windProbability;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("whenToGo")
    @Expose
    private String whenToGo;
    @SerializedName("isFavorite")
    @Expose
    private boolean isFavorite;

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

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getWindProbability() {
        return windProbability;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWhenToGo() {
        return whenToGo;
    }

    public void setWhenToGo(String whenToGo) {
        this.whenToGo = whenToGo;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setWindProbability(float windProbability) {
        this.windProbability = windProbability;
    }

    @Override
    public String toString() {
        return "Details{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", windProbability=" + windProbability +
                ", country='" + country + '\'' +
                ", whenToGo='" + whenToGo + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
