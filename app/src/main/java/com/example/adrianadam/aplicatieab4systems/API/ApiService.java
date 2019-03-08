package com.example.adrianadam.aplicatieab4systems.API;

import com.example.adrianadam.aplicatieab4systems.Response.ResponseSpotGet;
import com.example.adrianadam.aplicatieab4systems.Response.ResponseUserGet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("/api-user-get")
    Call<ResponseUserGet> createUser(@Field("email") String email);

    @Headers("Content-Type: application/json")
    @POST("/api-spot-get-all")
    Call<ResponseSpotGet> getAllSpots(@Header("token") String token);

    @Headers("Content-Type: application/json")
    @POST("/api-spot-get-countries")
    Call<ResponseSpotGet> getCountries(@Header("token") String userToken);
}
