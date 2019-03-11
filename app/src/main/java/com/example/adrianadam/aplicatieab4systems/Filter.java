package com.example.adrianadam.aplicatieab4systems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adrianadam.aplicatieab4systems.API.ApiService;
import com.example.adrianadam.aplicatieab4systems.API.ApiUtils;
import com.example.adrianadam.aplicatieab4systems.Response.ResponseCountryGet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Filter extends AppCompatActivity {

    private EditText filterCountry;
    private EditText filterWindProbability;
    private Button btnApply;
    private Button btnBack;

    private ApiService apiService;

    private List<String> responseData = new ArrayList<>();

    private String filterCountryText = "";
    private String filterWindProbabilityText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterCountry = findViewById(R.id.filterCountry);
        filterWindProbability = findViewById(R.id.filterWindProbability);
        btnApply = findViewById(R.id.btnApply);
        btnBack = findViewById(R.id.btnFilter);

        apiService = ApiUtils.getAPIService();
        apiService.getCountries(getIntent().getStringExtra("token")).enqueue(new Callback<ResponseCountryGet>() {
            @Override
            public void onResponse(Call<ResponseCountryGet> call, Response<ResponseCountryGet> response) {
                for(int i = 0; i < response.body().getResult().size(); i++) {
                    responseData.add(response.body().getResult().get(i));
                }
            }

            @Override
            public void onFailure(Call<ResponseCountryGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkAnswer = true;

                if(filterWindProbability.getText().toString().equals("")) {
                    filterWindProbabilityText = "";
                }

                if(!filterWindProbability.getText().toString().equals("")){
                    try {
                        int num = Integer.parseInt(filterWindProbability.getText().toString());
                        filterWindProbabilityText = filterWindProbability.getText().toString();
                    } catch (NumberFormatException e) {
                        Toast.makeText(Filter.this, "Wind Probability must be a number or empty", Toast.LENGTH_LONG).show();
                        checkAnswer = false;
                    }
                }

                if(filterCountry.getText().toString().equals("")) {
                    filterCountryText = "";
                }

                if(!filterCountry.getText().toString().equals("")) {
                    if(responseData.contains(filterCountry.getText().toString())) {
                        filterCountryText = filterCountry.getText().toString();
                    } else {
                        Toast.makeText(Filter.this, "The selected country doesn't exist in out database", Toast.LENGTH_LONG).show();
                        checkAnswer = false;
                    }
                }

                if(checkAnswer) {
                    Toast.makeText(Filter.this, "Filters applied succesfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartMain(filterCountryText, filterWindProbabilityText);
            }
        });
    }

    public void restartMain(String countryFilter, String windProbabilityFilter) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("token", getIntent().getStringExtra("token"));
        intent.putExtra("countryFilter", countryFilter);
        intent.putExtra("windProbabilityFilter", windProbabilityFilter);
        finish();
        startActivity(intent);
    }
}
