package com.example.adrianadam.aplicatieab4systems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.adrianadam.aplicatieab4systems.API.ApiService;
import com.example.adrianadam.aplicatieab4systems.API.ApiUtils;
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
    private ApiService apiService;
    private HashMap<String, String> responseData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        apiService = ApiUtils.getAPIService();

        apiService.getAllSpots(getIntent().getStringExtra("token")).enqueue(new Callback<ResponseSpotGet>() {
            @Override
            public void onResponse(Call<ResponseSpotGet> call, Response<ResponseSpotGet> response) {
                Log.i("Test", response.body().getResult().get(0).getCountry());
                Log.i("Response", "post submitted to API." + response.body()+" " + response.code() + " " + response.message());
                for(int i = 0; i < response.body().getResult().size(); i++) {
                    responseData.put(response.body().getResult().get(i).getName(), response.body().getResult().get(i).getCountry());
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
}
