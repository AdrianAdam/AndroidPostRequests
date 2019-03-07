package com.example.adrianadam.aplicatieab4systems.Response;

import com.example.adrianadam.aplicatieab4systems.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserGet {

    @SerializedName("result")
    @Expose
    private User result;

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Example{" +
                "result=" + result +
                '}';
    }
}
