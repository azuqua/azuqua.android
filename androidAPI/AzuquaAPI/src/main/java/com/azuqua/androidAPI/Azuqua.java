package com.azuqua.androidAPI;

import com.azuqua.androidAPI.log.Logs;
import com.azuqua.androidAPI.model.Flo;
import com.azuqua.androidAPI.model.LoginInfo;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Azuqua {

    private boolean DEBUG = true;
    private static Gson gson = new Gson();
    private Vector<Flo> floCache = new Vector<Flo>();

    // routes
    public final static String invokeRoute = "/flo/:id/invoke";
    public final static String listRoute = "/account/flos";
    public final static String accountsInfoRoute = "/account/data";

    // account
    private String accessKey;
    private String accessSecret;

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

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

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String timestamp = df.format(new Date());
        Logs.info("Time Stamp", timestamp);

        return timestamp;
    }

    public String makeRequest(String verb, String path, String data, AsyncResponse response){

        String timestamp = getISOTime();
        String signedData = signData(data, verb.toLowerCase(), path, timestamp);

        AzuquaRequest azuquaRequest = new AzuquaRequest(this.accessKey, verb, path, data, signedData, timestamp, response);
        azuquaRequest.execute();

        return null;
    }

    public void getFlos(AsyncResponse response){
        String path = listRoute;
        makeRequest("GET", path, "", response);
    }

    public void login(String username,String password,AsyncResponse response){
        String path = accountsInfoRoute;
        String loginInfo = gson.toJson(new LoginInfo(username,password));
        makeRequest("POST",path,loginInfo,response);
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