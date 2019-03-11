package com.example.adrianadam.aplicatieab4systems;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.emailText);
        signIn = findViewById(R.id.buttonSignIn);
        createAccount = findViewById(R.id.buttonCreateAccount);

        email.setHint("test@yahoo.com");

        apiService = ApiUtils.getAPIService();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {
            runCodeOnline();
        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(Register.this, "No connection detected. Will try to use the data saved on cache", Toast.LENGTH_LONG).show();
            createAccount.setVisibility(View.INVISIBLE);
            checkSharedPreferences();
        }
    }

    private void checkSharedPreferences() {
        final String emailCached = mPreferences.getString(getString(R.string.email), "");
        final String tokenCached = mPreferences.getString(getString(R.string.token), "");

        if(emailCached.equals("") && tokenCached.equals("")) {
            Toast.makeText(Register.this, "No data stored on cache. Unable to continue", Toast.LENGTH_LONG).show();
        } else {
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailEditText = email.getText().toString();
                    if(emailEditText.equals(emailCached)) {
                        startMainActivityOffline(emailCached, tokenCached);
                    } else {
                        Toast.makeText(Register.this, "Wrong email adress", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void runCodeOnline() {
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

    public void createAccount(final String email) {
        apiService.createUser(email).enqueue(new Callback<ResponseUserGet>() {
            @Override
            public void onResponse(Call<ResponseUserGet> call, Response<ResponseUserGet> response) {
                Log.i("Response", "post submitted to API." + response.body()+"");
                ResponseUserGet responseUserGet = response.body();
                mEditor.putString(getString(R.string.email), response.body().getResult().getEmail());
                mEditor.commit();
                mEditor.putString(getString(R.string.token), response.body().getResult().getToken());
                mEditor.commit();
                startMainActivityOnline(responseUserGet);
            }

            @Override
            public void onFailure(Call<ResponseUserGet> call, Throwable t) {
                Log.e("Error", "Unable to submit post to API.");
            }
        });
    }

    public void startMainActivityOnline(ResponseUserGet responseUserGet) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", responseUserGet.getResult().getEmail());
        intent.putExtra("token", responseUserGet.getResult().getToken());
        finish();
        startActivity(intent);
    }

    public void startMainActivityOffline(String email, String token) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        finish();
        startActivity(intent);
    }
}
