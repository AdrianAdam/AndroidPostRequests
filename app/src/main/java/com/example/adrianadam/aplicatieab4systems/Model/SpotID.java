package com.example.adrianadam.aplicatieab4systems.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotID {
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SpotID{" +
                "id='" + id + '\'' +
                '}';
    }
}
