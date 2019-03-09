package com.example.adrianadam.aplicatieab4systems.Response;

import com.example.adrianadam.aplicatieab4systems.Model.Details;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailsGet {
    @SerializedName("result")
    @Expose
    private Details result;

    public Details getResult() {
        return result;
    }

    public void setResult(Details result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseDetailsGet{" +
                "result='" + result + '\'' +
                '}';
    }
}
