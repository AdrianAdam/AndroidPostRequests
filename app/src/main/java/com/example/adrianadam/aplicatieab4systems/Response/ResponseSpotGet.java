package com.example.adrianadam.aplicatieab4systems.Response;

import com.example.adrianadam.aplicatieab4systems.Model.Spot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSpotGet {
    @SerializedName("result")
    @Expose
    private List<Spot> result;

    public List<Spot> getResult() {
        return result;
    }

    public void setResult(List<Spot> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "result=" + result +
                '}';
    }
}
