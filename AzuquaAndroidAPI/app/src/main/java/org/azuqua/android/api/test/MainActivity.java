package org.azuqua.android.api.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

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

    Azuqua azuqua = new Azuqua("https", "alphaapi.azuqua.com", 443);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        azuqua.login("sasidhar@azuqua.com", "S@sidhar678", new LoginRequest() {
            @Override
            public void onResponse(User user) {
                getFLOS(user);
            }

            @Override
            public void onError(AzuquaError error) {
                Log.d("Error", error.getErrorMessage());
            }
        });
    }

    private void getFLOS(final User user) {
        azuqua.getFlos(44, user.getAccess_key(), user.getAccess_secret(), new AllFlosRequest() {
            @Override
            public void onResponse(ArrayList<Flo> floList) {
                if (floList.isEmpty())
                    Log.d("FLO", "No FLOs found...");
                else {
                    Flo flo = floList.get(0);

                    Map<String, Map> params = new HashMap<String, Map>();
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("Phone Number", "9959582678");
                    data.put("Message", "Hello World !");
                    params.put("QR Code", data);

                    azuqua.invokeFlo(flo.getAlias(), new Gson().toJson(params),
                            user.getAccess_key(), user.getAccess_secret(), new AsyncRequest() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Response", response);
                                }

                                @Override
                                public void onError(AzuquaError error) {
                                    Log.d("Response", error.getErrorMessage());
                                }
                            });
                }

            }

            @Override
            public void onError(AzuquaError error) {
                Log.d("Error", error.getErrorMessage());
            }
        });
    }
}
