package com.azuqua.androidAPI.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

    private String accessKey = Config.AccessKey;
    private String accessSecret = Config.AccessSecret;

    private String data = "{\"key\":\"value\"}"; // Input data to invoke a flo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        infoTextView = (TextView) findViewById(R.id.info);

        final Azuqua azuqua = new Azuqua(accessKey,accessSecret);

//        azuqua.login(Config.EMAIL, Config.PASSWORD, new AsyncResponse() {
//            @Override
//            public void onResponse(String response) {
//                infoTextView.setText(response);
//            }
//
//            @Override
//            public void onErrorResponse(String error) {
//                infoTextView.setText(error);
//            }
//        });

        azuqua.getFlos(new AsyncResponse() {
            @Override
            public void onResponse(String response) {
                infoTextView.setText(response);
                Type collectionType = new TypeToken<Collection<Flo>>(){}.getType();

                Collection<Flo> collection = gson.fromJson(response,collectionType);

                for(Flo flo: collection){
                    flo.setAzuqua(azuqua);
//                    try {
//                        flo.invoke(data, new AsyncResponse() {
//                            @Override
//                            public void onResponse(String response) {
//                                infoTextView.setText(response);
//                            }
//
//                            @Override
//                            public void onErrorResponse(String error) {
//                                infoTextView.setText(error);
//                            }
//                        });
//                    } catch (AzuquaException e) {
//                        e.printStackTrace();
//                    }

                    try {
                        flo.disable(new AsyncResponse() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),"Response "+response, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onErrorResponse(String error) {
                                Toast.makeText(getApplicationContext(),"Error "+error, Toast.LENGTH_LONG).show();
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(String error) {
                infoTextView.setText(error);
            }
        });

//        azuqua.getFlos(new AsyncResponse() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Response "+response,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onErrorResponse(String error) {
//                Toast.makeText(getApplicationContext(), "Error Response "+error,Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
