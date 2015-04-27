package com.azuqua.androidAPI.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.azuqua.androidAPI.AsyncResponse;
import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.AzuquaException;
import com.azuqua.androidAPI.log.Logs;
import com.azuqua.androidAPI.model.Flo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collection;

public class TestActivity extends ActionBarActivity {
    private static  final String TAG = "TestActivity";

    private static Gson gson = new Gson();
    //UI reference
    TextView infoTextView;

    private String accessKey = ""; // Account AccessKey
    private String accessSecret = ""; // Account AccessSecretKey

    private String data = ""; // Input data to invoke a flo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        infoTextView = (TextView) findViewById(R.id.info);

        final Azuqua azuqua = new Azuqua(accessKey,accessSecret);

        azuqua.login("username@azuqua.com", "password", new AsyncResponse() {
            @Override
            public void onResponse(String response) {
                infoTextView.setText(response);
            }

            @Override
            public void onErrorResponse(String error) {
                infoTextView.setText(error);
            }
        });

        azuqua.getFlos(new AsyncResponse() {
            @Override
            public void onResponse(String response) {
                infoTextView.setText(response);
                Type collectionType = new TypeToken<Collection<Flo>>(){}.getType();

                Collection<Flo> collection = gson.fromJson(response,collectionType);

                for(Flo flo: collection){
                    flo.setAzuqua(azuqua);
                    try {
                        flo.invoke(data, new AsyncResponse() {
                            @Override
                            public void onResponse(String response) {
                                infoTextView.setText(response);
                            }

                            @Override
                            public void onErrorResponse(String error) {
                                infoTextView.setText(error);
                            }
                        });
                    } catch (AzuquaException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(String error) {
                infoTextView.setText(error);
            }
        });
    }
}
