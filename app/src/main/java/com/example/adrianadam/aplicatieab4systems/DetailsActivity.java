package com.example.adrianadam.aplicatieab4systems;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.adrianadam.aplicatieab4systems.API.ApiService;
import com.example.adrianadam.aplicatieab4systems.API.ApiUtils;
import com.example.adrianadam.aplicatieab4systems.Response.ResponseDetailsGet;
import com.example.adrianadam.aplicatieab4systems.Response.ResponseFavourites;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private TextView country;
    private TextView latitude;
    private TextView longitude;
    private TextView windProbability;
    private TextView whenToGo;
    private TextView titleDetails;
    private Button favorites;
    private Button back;
    private Button maps;

    private ApiService apiService;

    private boolean isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        country = findViewById(R.id.textCountry);
        latitude = findViewById(R.id.textLatitude);
        longitude = findViewById(R.id.textLongitude);
        windProbability = findViewById(R.id.textWindProbability);
        whenToGo = findViewById(R.id.textWhenToGo);
        titleDetails = findViewById(R.id.titleDetails);
        favorites = findViewById(R.id.btnStar);
        back = findViewById(R.id.btnDetails);
        maps = findViewById(R.id.btnMaps);

        apiService = ApiUtils.getAPIService();

        apiService.getSpotDetails(getIntent().getStringExtra("token"), getIntent().getStringExtra("spotId")).enqueue(new Callback<ResponseDetailsGet>() {
            @Override
            public void onResponse(Call<ResponseDetailsGet> call, Response<ResponseDetailsGet> response) {
                Log.i("Response", "post submitted to API." + response.body()+" " + response.code() + " " + response.message());
                Log.i("Test", response.body().getResult().toString());
                titleDetails.setText(response.body().getResult().getName());
                country.setText(response.body().getResult().getCountry());
                latitude.setText(response.body().getResult().getLatitude() + "");
                longitude.setText(response.body().getResult().getLongitude() + "");
                windProbability.setText(response.body().getResult().getWindProbability() + "");
                whenToGo.setText(response.body().getResult().getWindProbability() + "%");
                titleDetails.setText(response.body().getResult().getName());
                isFavourite = response.body().getResult().isFavorite();

                if(isFavourite) {
                    favorites.setBackgroundResource(R.drawable.star_on);
                } else {
                    favorites.setBackgroundResource(R.drawable.star_off);
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailsGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavourite) {
                    apiService.removeFavourites(getIntent().getStringExtra("token"), getIntent().getStringExtra("spotId")).enqueue(new Callback<ResponseFavourites>() {
                        @Override
                        public void onResponse(Call<ResponseFavourites> call, Response<ResponseFavourites> response) {
                            Log.i("Test", response.body().getResult().toString());
                            Log.i("Response", "post submitted to API." + response.body()+" " + response.code() + " " + response.message());
                            favorites.setBackgroundResource(R.drawable.star_off);
                        }

                        @Override
                        public void onFailure(Call<ResponseFavourites> call, Throwable t) {
                            Log.e("Error", "Unable to submit post to API.");
                        }
                    });
                } else {
                    apiService.addFavourites(getIntent().getStringExtra("token"), getIntent().getStringExtra("spotId")).enqueue(new Callback<ResponseFavourites>() {
                        @Override
                        public void onResponse(Call<ResponseFavourites> call, Response<ResponseFavourites> response) {
                            Log.i("Test", response.body().getResult().toString());
                            Log.i("Response", "post submitted to API." + response.body()+" " + response.code() + " " + response.message());
                            favorites.setBackgroundResource(R.drawable.star_on);
                        }

                        @Override
                        public void onFailure(Call<ResponseFavourites> call, Throwable t) {
                            Log.e("Error", "Unable to submit post to API.");
                        }
                    });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartMain();
            }
        });
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMaps();
            }
        });
    }

    public void restartMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("token", getIntent().getStringExtra("token"));
        finish();
        startActivity(intent);
    }

    public void goToMaps() {
        Uri gmmIntentUri = Uri.parse("geo:" + longitude.getText().toString() + "," + latitude.getText().toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
