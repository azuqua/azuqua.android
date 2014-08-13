package com.azuqua.androidAPI.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.VolleyLog;
import com.azuqua.androidAPI.Azuqua;

import org.json.JSONArray;
import org.json.JSONException;

public class TestActivity extends Activity {
    private static  final String TAG = "TestActivity";
    private static  final String AccessKey = "cf589168e17832fa0d49c30051baa50dfeb0230f";
    private static  final String AccessSecret = "445c124fe15dcbd5e8a5dfa7fe108db859b7d1788c61e7525d4f01f0bbbc708e";

    //UI reference
    TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        infoTextView = (TextView) findViewById(R.id.info);
        infoTextView.setText("Starting Tests \n");

        Azuqua.initialize(this);

        //Begin Tests
        //signInTest();
        //getOrgsTest();
        getFlosTest();
    }

    // ***API Tests***

    //signInTest
    public void signInTest(){
        infoTextView.setText(infoTextView.getText() + "\nSignIn Test");
        Azuqua.signIn("dylan@azuqua.com", "ddc888", createSignInListener(), createSignInErrorListener());
    }

    //getOrgsTest
    public void getOrgsTest(){
        infoTextView.setText(infoTextView.getText() + "\nGetOrgs Test");
        try {
            Azuqua.getOrgs("Token", createGetOrgsListener(), createGetOrgsErrorListener());
        } catch (JSONException e){
            Log.i(TAG, "JSON Exception, I'm DEAD");
            finish();
        }
    }

    //getFlosTest
    public void getFlosTest(){
        infoTextView.setText(infoTextView.getText() + "\nGetFlos Test");
        try {
            Azuqua.getFlos(AccessKey, AccessSecret, createGetFlosListener(), createGetFlosErrorListener());
        } catch (Exception e){
            Log.i(TAG, "Exception, I'm DEAD: \n" + e.getMessage());
            finish();
        }
    }

    //Listeners For Tests
    //SignIn listeners
    public Response.Listener<String> createSignInListener(){
        Response.Listener listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                infoTextView.setText(infoTextView.getText() + "\nSignIn Response: " + response);
                Log.i(TAG, "signIn Response:" + response);
            }
        };
        return listener;
    }

    public Response.ErrorListener createSignInErrorListener(){
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                infoTextView.setText(infoTextView.getText() + "\nSignIn Response: Error");
                Log.i(TAG, "signIn Response: Error");
            }
        };
        return errorListener;
    }


    //GetOrgs listeners
    public Response.Listener<JSONArray> createGetOrgsListener(){
        Response.Listener listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                infoTextView.setText(infoTextView.getText() + "\nGetOrgs Response: " + response.toString());
                Log.i(TAG, "getOrgs Response:" + response.toString());
            }
        };
        return listener;
    }

    public Response.ErrorListener createGetOrgsErrorListener(){
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                infoTextView.setText(infoTextView.getText() + "\nGetOrgs Response: Error");
                Log.i(TAG, "getOrgs Response: Error");
            }
        };
        return errorListener;
    }

    //GetFlos listeners
    public Response.Listener<JSONArray> createGetFlosListener(){
        Response.Listener listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                infoTextView.setText(infoTextView.getText() + "\nGetFlos Response: " + response.toString());
                Log.i(TAG, "getFlos Response:" + response.toString());
            }
        };
        return listener;
    }

    public Response.ErrorListener createGetFlosErrorListener(){
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                infoTextView.setText(infoTextView.getText() + "\nGetFlos Response: Error");
                Log.i(TAG, "getFlos Response: Error");
                Log.i(TAG, error.networkResponse.headers.toString());
                Log.i(TAG, "local message: " + error.getLocalizedMessage());
                Log.i(TAG, "message: " + error.getMessage());
                Log.i(TAG, "data: " + error.networkResponse.data.toString());


            }
        };
        return errorListener;
    }

}
