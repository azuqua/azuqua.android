package org.azuqua.android.api.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.azuqua.android.api.Azuqua;
import org.azuqua.android.api.callbacks.AllFlosRequest;
import org.azuqua.android.api.callbacks.AsyncRequest;
import org.azuqua.android.api.callbacks.LoginRequest;
import org.azuqua.android.api.models.AzuquaError;
import org.azuqua.android.api.models.Flo;
import org.azuqua.android.api.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Azuqua azuqua = new Azuqua();

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

    private void getFLOS(User user) {
        azuqua.getFlos(user.getAccess_key(), user.getAccess_secret(), new AllFlosRequest() {
            @Override
            public void onResponse(ArrayList<Flo> floList) {
                if(floList.isEmpty())
                    Log.d("FLO", "No FLOs found...");
            }

            @Override
            public void onError(AzuquaError error) {
                Log.d("Error", error.getErrorMessage());
            }
        });
    }
}
