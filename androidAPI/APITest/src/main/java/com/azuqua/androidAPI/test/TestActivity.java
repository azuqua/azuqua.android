package com.azuqua.androidAPI.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.AzuquaAllFlosRequest;
import com.azuqua.androidAPI.AzuquaFloRequest;
import com.azuqua.androidAPI.model.Flo;
import com.google.gson.Gson;

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

//        azuqua.login(Config.EMAIL, Config.PASSWORD, new AzuquaOrgRequest() {
//            @Override
//            public void onResponse(Collection<Org> orgCollection) {
//                for (Org org:orgCollection)
//                    Toast.makeText(getApplicationContext(), "Org Name : " +org.getName(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onErrorResponse(String error) {
//                Toast.makeText(getApplicationContext(), "Error "+error, Toast.LENGTH_LONG).show();
//            }
//        });

        azuqua.getFlos(new AzuquaAllFlosRequest() {
            @Override
            public void onResponse(Collection<Flo> flosCollection) {
                for(Flo flo: flosCollection)
                    flo.read(new AzuquaFloRequest() {
                        @Override
                        public void onResponse(Flo floData) {
                            Toast.makeText(getApplicationContext(), "Flo Name : "+floData.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onErrorResponse(String error) {
                            Toast.makeText(getApplicationContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                        }
                    });
            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(getApplicationContext(), "Error "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
