package org.azuqua.android.api.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import org.azuqua.android.api.Azuqua;
import org.azuqua.android.api.callbacks.AllFlosRequest;
import org.azuqua.android.api.callbacks.AsyncRequest;
import org.azuqua.android.api.callbacks.LoginRequest;
import org.azuqua.android.api.models.AzuquaError;
import org.azuqua.android.api.models.Flo;
import org.azuqua.android.api.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Azuqua azuqua = new Azuqua();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String email = "";
        String password = "";

        azuqua.login(email, password, new LoginRequest() {
            @Override
            public void onResponse(User user) {
                Log.d("Success","User "+user.getFirst_name()+" logged in successfully");
            }

            @Override
            public void onError(AzuquaError error) {
                Log.d("Error", error.getErrorMessage());
            }
        });
    }
}
