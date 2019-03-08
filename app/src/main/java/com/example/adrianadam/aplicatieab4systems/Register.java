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
import com.example.adrianadam.aplicatieab4systems.Response.ResponseUserGet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText email;
    private Button signIn;
    private Button createAccount;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.emailText);
        signIn = findViewById(R.id.buttonSignIn);
        createAccount = findViewById(R.id.buttonCreateAccount);

        email.setHint("test@yahoo.com");

        apiService = ApiUtils.getAPIService();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "You need to enter an email adress", Toast.LENGTH_LONG).show();
                } else {
                    createAccount(email.getText().toString());
                }
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "You need to enter an email adress", Toast.LENGTH_LONG).show();
                } else {
                    createAccount(email.getText().toString());
                }
            }
        });
    }

    public void createAccount(String email) {
        apiService.createUser(email).enqueue(new Callback<ResponseUserGet>() {
            @Override
            public void onResponse(Call<ResponseUserGet> call, Response<ResponseUserGet> response) {
                Log.i("Response", "post submitted to API." + response.body()+"");
                ResponseUserGet responseUserGet = response.body();
                startMainActivity(responseUserGet);
            }

            @Override
            public void onFailure(Call<ResponseUserGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });
    }

    public void startMainActivity(ResponseUserGet responseUserGet) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", responseUserGet.getResult().getEmail());
        intent.putExtra("token", responseUserGet.getResult().getToken());
        startActivity(intent);
    }
}
