package com.example.adrianadam.aplicatieab4systems.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCountryGet {
    @SerializedName("result")
    @Expose
    private List<String> result;

    public List<String> getResult() {
        return this.result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseCountryGet{" +
                "result=" + result +
                '}';
    }
}
