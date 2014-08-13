package com.azuqua.androidAPI;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Azuqua {
    private static final String TAG = "Azuqua";
    private static final String baseURL = "http://api.azuqua.com";
    private static Gson gson = new Gson();
    protected final static char[] hexArray = "0123456789abcdef".toCharArray(); //Faster hex convert?

    //private initializer
    private Azuqua(){}

    //Init Azuqua with Context
    public static void initialize(Context context){
        MyVolley.init(context);
    }

    // *** Requests ***
    //Azuqua Custom Request, because server is not sending back JSON?
    public static void azuquaRequest(String url, JSONObject data, Response.Listener<String> successListener, Response.ErrorListener errorListener){
        Log.i(TAG, "Creating Azuqua Request...");
        Log.i(TAG, "URL: " + url);
        Log.i(TAG, "Data: " + data.toString());

        RequestQueue queue = MyVolley.getRequestQueue();

        AzuquaRequest azuquaRequest = new AzuquaRequest(Method.POST, url, data.toString(), successListener, errorListener);
        queue.add(azuquaRequest);
    }

    // *** Object Parsing ***
    //Parse Flos
    public static ArrayList<Flo> parseFlos(String floString) throws JSONException {
        ArrayList<Flo> flos = new ArrayList<Flo>();
        String rootFloString = "{flos: " + floString + "}";
        JSONObject rootJson = new JSONObject(rootFloString);
        JSONArray jsonMainArr = rootJson.getJSONArray("flos");

        for (int i=0;i<jsonMainArr.length();i++){
            JSONObject jsonFlo = jsonMainArr.getJSONObject(i);
            Flo flo = gson.fromJson(jsonFlo.toString(), Flo.class);
            flos.add(flo);
        }
        return flos;
    }


    //*** Azuqua API Calls ***

    //Sign in
    public static void signIn(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String url = baseURL + "/signIn";
        Log.i(TAG, "Creating String Request...");
        Log.i(TAG, "URL: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueue queue = MyVolley.getRequestQueue();
        queue.add(stringRequest);
    }

    //Get Orgs
    public static void getOrgs(String token, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) throws JSONException {
        String url = baseURL + "/orgs";
        Log.i(TAG, "Creating AzuquaJSONArrayRequest...");
        Log.i(TAG, "URL: " + url);

        //No Params
        Map params = new HashMap();
        RequestData requestData = new RequestData("", "", params, "POST", "/orgs");
        String jsonData = gson.toJson(requestData);

        JSONObject data = new JSONObject(jsonData);
        //AzuquaJSONArrayRequest request = new AzuquaJSONArrayRequest(Request.Method.POST, url, data.toString(), listener, errorListener);
        //RequestQueue queue = MyVolley.getRequestQueue();
        //queue.add(request);
    }

    //Get Flos
    public static void getFlos(String accessKey, String accessSecret, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) throws JSONException, NoSuchAlgorithmException{
        String url = baseURL + "/account/flos";
        Log.i(TAG, "Creating AzuquaJSONArrayRequest...");
        Log.i(TAG, "URL: " + url);

        //No Params
        Map params = new HashMap();

        //Time stamp request
        String timestamp = generateTimeStamp();

        String hash = signData(accessSecret, params, "POST", timestamp, "/account/flos");

        AzuquaJSONArrayRequest request = new AzuquaJSONArrayRequest(Request.Method.POST, url, "", hash, timestamp, accessKey, listener, errorListener);
        logRequest(request);
        RequestQueue queue = MyVolley.getRequestQueue();
        queue.add(request);
    }

    private static void logRequest(Request request){
        Log.i(TAG, "Request Log\n");
        try {
            Log.i(TAG, "Request Body:");
            Log.i(TAG, request.getBody().toString());

            Log.i(TAG, "Request Headers:");
            Log.i(TAG, request.getHeaders().toString());

        } catch (AuthFailureError e){
            Log.i(TAG, "AuthFailureError" + e.getMessage());
        }
        Log.i(TAG, "Request Conten-Type");
        Log.i(TAG, request.getBodyContentType());
    }

    //Generate TimeStamp
    private static String generateTimeStamp(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;
    }

    //SignData
    private static String signData(String accessSecret, Map data, String method, String timestamp, String path) throws NoSuchAlgorithmException{
        final Charset charSet = Charset.forName("US-ASCII");
        final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        final SecretKeySpec secret_key = new SecretKeySpec(charSet.encode(accessSecret).array(), "HmacSHA256");
        try {
            sha256_HMAC.init(secret_key);
        } catch (InvalidKeyException e) {
            Log.i(TAG, "Invalid Key Exception:");
            Log.i(TAG, e.getMessage());
        }

        String meta = method.toLowerCase() + ":" + path + ":" + timestamp;
        String toBeEncrypted = meta + data.toString();

        return bytesToHex(sha256_HMAC.doFinal(toBeEncrypted.getBytes()));
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
