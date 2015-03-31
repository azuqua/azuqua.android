package com.azuqua.androidAPI;


import android.util.Log;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class RequestData{
    private static final String TAG = "RequestData";
    private final String accessKey;
    private final Map<String, String> params;
    private String method;
    private String path;
    private String hash;
    protected final static char[] hexArray = "0123456789abcdef".toCharArray(); //Faster hex convert?

    public RequestData(String accessKey, String accessSecret, Map params, String httpMethod, String path){
        this.accessKey = accessKey;
        this.params = params;
        this.method = httpMethod.toLowerCase();
        this.path = path;

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        String data = method + ":" + path + nowAsISO;
        try {
            this.hash = encode(accessSecret, data);
        } catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
    };

    //Encode Data
    public static String encode(String key, String data) throws Exception{
        final Charset charSet = Charset.forName("US-ASCII");
        final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        final SecretKeySpec secret_key = new SecretKeySpec(charSet.encode(key).array(), "HmacSHA256");
        try {
            sha256_HMAC.init(secret_key);
        } catch (InvalidKeyException e) {
            Log.i(TAG, "Invalid Key Exception:");
            Log.i(TAG, e.getMessage());
        }

        return bytesToHex(sha256_HMAC.doFinal(data.getBytes()));
    }

    //Quicker bytes to Hex?
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
