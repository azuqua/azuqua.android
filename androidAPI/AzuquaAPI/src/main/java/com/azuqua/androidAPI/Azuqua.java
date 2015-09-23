package com.azuqua.androidAPI;

import android.util.Log;

import com.azuqua.androidAPI.log.Logs;
import com.azuqua.androidAPI.model.Flo;
import com.azuqua.androidAPI.model.LoginInfo;
import com.azuqua.androidAPI.model.Org;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Azuqua {

    private static Gson gson = new Gson();

    // account
    private String accessKey;
    private String accessSecret;

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    // empty constructor
    public Azuqua(){

    }

    public Azuqua(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private String signData(String data, String verb, String path, String timestamp) {

        String method = "signData";

        Mac hmac = null;
        try {
            hmac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKeySpec key = null;
        try {
            key = new SecretKeySpec(this.accessSecret.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            hmac.init(key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        String meta = verb + ":" + path + ":" + timestamp;
        String dataToDigest = meta + data;
        Logs.info(method, "data to digest " + dataToDigest);

        byte[] digest = new byte[0];
        try {
            digest = hmac.doFinal(dataToDigest.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String digestString = bytesToHex(digest).toLowerCase();
        Logs.info(method, "digested string " + digestString);

        return 	digestString;
    }

    private String getISOTime() {

        TimeZone timezone = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(timezone);
        String timestamp = df.format(new Date());

        return timestamp;
    }

    public String makeRequest(String verb, String path, String data, AsyncResponse response){

        String timestamp = getISOTime();

        String signedData = null;
        if (accessKey != null && accessSecret != null) {
            signedData = signData(data, verb.toLowerCase(), path, timestamp);
        }

        Log.i("Signed Data",""+signedData);

        AzuquaRequest azuquaRequest = new AzuquaRequest(this.accessKey, verb, path, data, signedData, timestamp, response);
        azuquaRequest.execute();

        return null;
    }

    public void getFlos(final AzuquaAllFlosRequest request){
        String path = Routes.ALL_FLOS;
        makeRequest("GET", path, "", new AsyncResponse() {
            @Override
            public void onResponse(String response) {
                Log.d("Flos ", response);
                Type ListType = new TypeToken<List<Flo>>() {
                }.getType();
                List<Flo> flos = gson.fromJson(response, ListType);

                // give each Flo a reference to this so it can make a request call
                for (Flo flo : flos) {
                    flo.setAzuqua(Azuqua.this);
                }
                request.onResponse(flos);
            }

            @Override
            public void onErrorResponse(String error) {
                request.onErrorResponse(error);
            }
        });
    }

    public void login(String username,String password, final AzuquaOrgRequest request){
        String path = Routes.LOGIN;
        String loginInfo = gson.toJson(new LoginInfo(username,password));

        makeRequest("POST", path, loginInfo, new AsyncResponse() {
            @Override
            public void onResponse(String response) {
                Type ListType = new TypeToken<List<Org>>(){}.getType();
                List<Org> orgs  = gson.fromJson(response, ListType);
                request.onResponse(orgs);
            }

            @Override
            public void onErrorResponse(String error) {
                request.onErrorResponse(error);
            }
        });
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getAccessSecret() {
        return accessSecret;
    }
}