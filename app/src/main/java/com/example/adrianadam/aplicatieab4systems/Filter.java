package com.example.adrianadam.aplicatieab4systems;

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

    private ApiService apiService;

    private List<String> responseData = new ArrayList<>();

    public String filterCountryText;
    public String filterWindProbabilityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterCountry = findViewById(R.id.filterCountry);
        filterWindProbability = findViewById(R.id.filterWindProbability);
        btnApply = findViewById(R.id.btnApply);

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
                if(filterWindProbability.getText().toString().equals("")) {
                    filterWindProbabilityText = "";
                }

                if(!filterWindProbability.getText().toString().equals("")){
                    try {
                        int num = Integer.parseInt(filterWindProbability.getText().toString());
                        filterWindProbabilityText = filterWindProbability.getText().toString();
                    } catch (NumberFormatException e) {
                        Log.i("Error", filterWindProbability.getText().toString() + " is not a number");
                        Toast.makeText(Filter.this, "Wind Probability must be a number or empty", Toast.LENGTH_LONG).show();
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
                    }
                }
            }
        });
    }
}
