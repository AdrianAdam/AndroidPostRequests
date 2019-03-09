package com.example.adrianadam.aplicatieab4systems.Response;

import com.example.adrianadam.aplicatieab4systems.Model.SpotID;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFavourites {
    @SerializedName("result")
    @Expose
    private SpotID result;

    public SpotID getResult() {
        return result;
    }

    public void setResult(SpotID result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseFavourites{" +
                "result=" + result +
                '}';
    }
}
