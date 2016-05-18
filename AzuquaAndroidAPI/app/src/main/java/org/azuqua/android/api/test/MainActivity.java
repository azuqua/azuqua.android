package org.azuqua.android.api.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.azuqua.android.api.Azuqua;
import org.azuqua.android.api.callbacks.AllFlosRequest;
import org.azuqua.android.api.callbacks.LoginRequest;
import org.azuqua.android.api.models.Flo;
import org.azuqua.android.api.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Azuqua azuqua = new Azuqua();
        azuqua.login("sasidhar@azuqua.com", "S@sidhar678", new LoginRequest() {
            @Override
            public void onResponse(User user) {
                azuqua.getFlos(user.getAccess_key(), user.getAccess_secret(), new AllFlosRequest() {
                    @Override
                    public void onResponse(ArrayList<Flo> floList) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
