package com.example.adrianadam.aplicatieab4systems.API;

import com.example.adrianadam.aplicatieab4systems.Response.ResponseUserGet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("/api-user-get")
    Call<ResponseUserGet> createUser(@Field("email") String email);
}
