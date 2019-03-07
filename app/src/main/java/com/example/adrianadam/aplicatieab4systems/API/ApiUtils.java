package com.example.adrianadam.aplicatieab4systems.API;

import com.example.adrianadam.aplicatieab4systems.Retrofit.RetrofitClient;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://internship-2019.herokuapp.com/";

    public static ApiService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
