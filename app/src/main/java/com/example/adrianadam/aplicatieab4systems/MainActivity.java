package com.example.adrianadam.aplicatieab4systems;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.adrianadam.aplicatieab4systems.API.ApiService;
import com.example.adrianadam.aplicatieab4systems.API.ApiUtils;
import com.example.adrianadam.aplicatieab4systems.Adapter.ListAdapter;
import com.example.adrianadam.aplicatieab4systems.Model.Details;
import com.example.adrianadam.aplicatieab4systems.Model.MainPageSpot;
import com.example.adrianadam.aplicatieab4systems.Model.Spot;
import com.example.adrianadam.aplicatieab4systems.Response.ResponseDetailsGet;
import com.example.adrianadam.aplicatieab4systems.Response.ResponseSpotGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnFilter;
    private Button btnFavorite;
    private ListAdapter listAdapter;
    private ArrayList<MainPageSpot> spotModels = new ArrayList<>();
    private ApiService apiService;
    private HashMap<String, String> responseData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        btnFavorite = findViewById(R.id.btnMainFavorite);
        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterActivity();
            }
        });

        apiService = ApiUtils.getAPIService();

        if(getIntent().hasExtra("countryFilter") && getIntent().hasExtra("windProbabilityFilter")) {
            if(getIntent().getStringExtra("countryFilter").equals("") && getIntent().getStringExtra("windProbabilityFilter").equals("")) {
                getSpotsNotFiltered();
            } else {
                if(!getIntent().getStringExtra("countryFilter").equals("") && getIntent().getStringExtra("windProbabilityFilter").equals("")) {
                    getSpotsFilteredByCountry();
                }
                else if(getIntent().getStringExtra("countryFilter").equals("") && !getIntent().getStringExtra("windProbabilityFilter").equals("")) {
                    getSpotsFilteredByWind();
                }
                else {
                    getSpotsFilteredByAll();
                }
            }
        } else {
            getSpotsNotFiltered();
        }
    }

    public void openFilterActivity() {
        Intent intent = new Intent(this, Filter.class);
        intent.putExtra("token", getIntent().getStringExtra("token"));
        finish();
        startActivity(intent);
    }

    public void openDetailsActivity(String spotId) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("token", getIntent().getStringExtra("token"));
        intent.putExtra("spotId", spotId);
        finish();
        startActivity(intent);
    }

    public void getSpotsFilteredByAll() {
        apiService.getAllSpots(getIntent().getStringExtra("token")).enqueue(new Callback<ResponseSpotGet>() {
            @Override
            public void onResponse(Call<ResponseSpotGet> call, final Response<ResponseSpotGet> response) {
                for(int i = 0; i < response.body().getResult().size(); i++) {
                    final int I = i;
                    if(response.body().getResult().get(i).getCountry().equals(getIntent().getStringExtra("countryFilter"))) {
                        apiService.getSpotDetails(getIntent().getStringExtra("token"), response.body().getResult().get(i).getId()).enqueue(new Callback<ResponseDetailsGet>() {
                            @Override
                            public void onResponse(Call<ResponseDetailsGet> call, Response<ResponseDetailsGet> response1) {
                                Log.i("Error", response1.message() + " " + response1.code() + " " + response.body().getResult().get(I).getId());
                                if(response1.body().getResult().getWindProbability() == Integer.parseInt(getIntent().getStringExtra("windProbabilityFilter"))) {
                                    responseData.put(response.body().getResult().get(I).getName(), response.body().getResult().get(I).getCountry());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDetailsGet> call, Throwable t) {
                                Log.e("Error", "Unable to submit post to API.");
                            }
                        });
                    }
                }

                List<HashMap<String, String>> listItems = new ArrayList<>();
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listItems, R.layout.list_item,
                        new String[]{"First line", "Second line"},
                        new int[]{R.id.text_city, R.id.text_country});

                Iterator it = responseData.entrySet().iterator();
                while(it.hasNext()) {
                    HashMap<String, String> resultMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry) it.next();
                    resultMap.put("First line", pair.getKey().toString());
                    resultMap.put("Second line", pair.getValue().toString());
                    listItems.add(resultMap);
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseSpotGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });
    }

    public void getSpotsFilteredByWind() {
        apiService.getAllSpots(getIntent().getStringExtra("token")).enqueue(new Callback<ResponseSpotGet>() {
            @Override
            public void onResponse(Call<ResponseSpotGet> call, final Response<ResponseSpotGet> response) {
                for(int i = 0; i < response.body().getResult().size(); i++) {
                    final int I = i;
                    apiService.getSpotDetails(getIntent().getStringExtra("token"), response.body().getResult().get(i).getId()).enqueue(new Callback<ResponseDetailsGet>() {
                        @Override
                        public void onResponse(Call<ResponseDetailsGet> call, Response<ResponseDetailsGet> response1) {
                            Log.i("Error", response1.message() + " " + response1.code() + " " + response.body().getResult().get(I).getId());
                            if(response1.body().getResult().getWindProbability() == Integer.parseInt(getIntent().getStringExtra("windProbabilityFilter"))) {
                                responseData.put(response.body().getResult().get(I).getName(), response.body().getResult().get(I).getCountry());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDetailsGet> call, Throwable t) {
                            Log.e("Error", "Unable to submit post to API.");
                        }
                    });
                }

                List<HashMap<String, String>> listItems = new ArrayList<>();
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listItems, R.layout.list_item,
                        new String[]{"First line", "Second line"},
                        new int[]{R.id.text_city, R.id.text_country});

                Iterator it = responseData.entrySet().iterator();
                while(it.hasNext()) {
                    HashMap<String, String> resultMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry) it.next();
                    resultMap.put("First line", pair.getKey().toString());
                    resultMap.put("Second line", pair.getValue().toString());
                    listItems.add(resultMap);
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseSpotGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });
    }

    public void getSpotsFilteredByCountry() {
        apiService.getAllSpots(getIntent().getStringExtra("token")).enqueue(new Callback<ResponseSpotGet>() {
            @Override
            public void onResponse(Call<ResponseSpotGet> call, Response<ResponseSpotGet> response) {
                for(int i = 0; i < response.body().getResult().size(); i++) {
                    if(response.body().getResult().get(i).getCountry().equals(getIntent().getStringExtra("countryFilter"))) {
                        spotModels.add(new MainPageSpot(response.body().getResult().get(i).getName(), response.body().getResult().get(i).getCountry(), response.body().getResult().get(i).getId()));
                    }
                }

                listAdapter = new ListAdapter(spotModels, getApplicationContext());

                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainPageSpot pageSpot = spotModels.get(position);
                        openDetailsActivity(pageSpot.getId());
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseSpotGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });
    }

    public void getSpotsNotFiltered() {
        apiService.getAllSpots(getIntent().getStringExtra("token")).enqueue(new Callback<ResponseSpotGet>() {
            @Override
            public void onResponse(Call<ResponseSpotGet> call, Response<ResponseSpotGet> response) {
                for(int i = 0; i < response.body().getResult().size(); i++) {
                    spotModels.add(new MainPageSpot(response.body().getResult().get(i).getName(), response.body().getResult().get(i).getCountry(), response.body().getResult().get(i).getId()));
                }

                listAdapter = new ListAdapter(spotModels, getApplicationContext());

                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainPageSpot pageSpot = spotModels.get(position);
                        openDetailsActivity(pageSpot.getId());
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseSpotGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });
    }
}
