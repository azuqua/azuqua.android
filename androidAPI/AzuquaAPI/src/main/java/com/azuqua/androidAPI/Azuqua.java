package com.azuqua.androidAPI;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Azuqua {
    private static final String TAG = "AzuquaAPI";
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
    public static void signIn(final String email, final String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String url = Constants.baseURL + "/account/data";
        Log.i(TAG, "Creating String Request...");
        Log.i(TAG, "URL: " + url);


        StringRequest stringRequest = new StringRequest(Method.POST, url, listener, errorListener){

            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("password",password);

                return params;
            };
        };
        RequestQueue queue = MyVolley.getRequestQueue();
        queue.add(stringRequest);
    }

    //Get Orgs
    public static void getOrgs(String password, String email, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) throws NoSuchAlgorithmException {
        String url = Constants.baseURL + "/account/data";
        Log.i(TAG, "Creating AzuquaJSONArrayRequest...");
        Log.i(TAG, "URL: " + url);

        //No Params
        Map params = new HashMap();
        params.put("email", email);
        params.put("password", password);

        //Time stamp request
        //String timestamp = generateTimeStamp();

        //Sign Data
        //String hash = signData(accessSecret, params, "POST", timestamp, "/account/flos");
        JSONObject json = new JSONObject(params);

        //Create and Submit Request
        JsonObjectRequest request = new JsonObjectRequest(url, json, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(8000, 0, 3));
        RequestQueue queue = MyVolley.getRequestQueue();
        queue.add(request);
    }

    //Get Flos
    public static void getFlos(String accessKey, String accessSecret, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) throws NoSuchAlgorithmException{
        String url = Constants.baseURL + "/account/flos";
        Log.i(TAG, "Creating AzuquaJSONArrayRequest...");
        Log.i(TAG, "URL: " + url);

        //No Params
        Map params = new HashMap();

        //Time stamp request
        String timestamp = generateTimeStamp();

        //Sign Data
        String hash = signData(accessSecret, params, "POST", timestamp, "/account/flos");

        //Create and Submit request
        //AzuquaJSONArrayRequest request = new AzuquaJSONArrayRequest(Request.Method.POST, url, "", hash, timestamp, accessKey, listener, errorListener);
        //logRequest(request);
        //RequestQueue queue = MyVolley.getRequestQueue();
        //queue.add(request);
    }

    //Invoke Flo
    public static void invokeFlo(String accessKey, String accessSecret, String id, HashMap data, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) throws NoSuchAlgorithmException{
        String url = Constants.baseURL + "/flo/" + id + "/invoke";
        Log.i(TAG, "Creating AzuquaJSONArrayRequest...");
        Log.i(TAG, "URL: " + url);

        //Time stamp request
        String timestamp = generateTimeStamp();

        //Sign Data
        String hash = signData(accessSecret, data, "POST", "/flo/" + id + "/invoke", timestamp);

        //Create and Submit request
        AzuquaJSONObjectRequest request = new AzuquaJSONObjectRequest(Request.Method.POST, url, data, hash, timestamp, accessKey, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(8000, 0, 3));
        RequestQueue queue = MyVolley.getRequestQueue();
        queue.add(request);
    }

    private static void logRequest(Request request){
        Log.i(TAG, "Request Log\n\n");
        try {
            Log.i(TAG, "Request Body:");
            Log.i(TAG, request.getBody().toString());

            Log.i(TAG, "Request Headers:");
            JSONObject headers = new JSONObject(request.getHeaders());
            Log.i(TAG, headers.toString());


        } catch (AuthFailureError e){
            Log.i(TAG, "AuthFailureError" + e.getMessage());
        }

        Log.i(TAG, "Request Content-Type");
        Log.i(TAG, request.getBodyContentType());

        Log.i(TAG, "Request.toString()");
        Log.i(TAG, request.toString());
    }

    //Generate TimeStamp
    private static String generateTimeStamp(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;
    }

    //SignData
    private static String signData(String accessSecret, Map data, String method, String path, String timestamp) throws NoSuchAlgorithmException{
        final Charset charSet = Charset.forName("US-ASCII");
        final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        final SecretKeySpec secret_key = new SecretKeySpec(charSet.encode(accessSecret).array(), "HmacSHA256");
        try {
            sha256_HMAC.init(secret_key);
        } catch (InvalidKeyException e) {
            Log.i(TAG, "Invalid Key Exception:");
            Log.i(TAG, e.getMessage());
        }

        String stringData;
        if(data.isEmpty()){
            stringData = "";
        } else {
            JSONObject jsonData = new JSONObject(data);
            stringData = jsonData.toString();
        }

        String meta = method.toLowerCase() + ":" + path + ":" + timestamp;
        String toBeEncrypted = meta + stringData;

        String hash =  bytesToHex(sha256_HMAC.doFinal(toBeEncrypted.getBytes()));
        Log.i(TAG, "hash returned: " + hash);

        return hash;
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
