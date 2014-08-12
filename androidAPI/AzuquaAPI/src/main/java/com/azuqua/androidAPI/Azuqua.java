package com.azuqua.androidAPI;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Azuqua {
    private static final String TAG = "Azuqua";
    private static final String baseURL = "http://api.azuqua.com";

    private static final Map routes;
    private static final Gson gson = new Gson();


    static {
        Map<String, String> map = new HashMap<String, String>();
        map.put("getFlows",  baseURL+ "/api/account/flos");
        map.put("invokeFlo", baseURL + "/api/flo/:id/invoke");
        routes = Collections.unmodifiableMap(map);
    }

    //private init so no one tries to instantiate
    private Azuqua(){}

    //Initialize Azuqua object with API key
    public static void initialize(Context context){
        MyVolley.init(context);
    }

    //Azuqua Custom Request, because server is not sending back JSON?
    public static void azuquaRequest(String url, JSONObject data, Response.Listener<String> successListener, Response.ErrorListener errorListener){
        Log.i(TAG, "Creating Azuqua Request...");
        Log.i(TAG, "URL: " + url);
        Log.i(TAG, "Data: " + data.toString());

        RequestQueue queue = MyVolley.getRequestQueue();

        AzuquaRequest azuquaRequest = new AzuquaRequest(Method.POST, url, data.toString(), successListener, errorListener);
        queue.add(azuquaRequest);
    }

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

    //Get all flows by user
    public static void getFlosByUser(Response.Listener<String> successListener, Response.ErrorListener errorListener) throws Exception{
        Map params = new HashMap();
        String url = routes.get("getFlows").toString();

        RequestData request = new RequestData(accessKey, accessSecret, params);
        String jsonData = gson.toJson(request);
        JSONObject data = new JSONObject(jsonData);

        azuquaRequest(url, data, successListener, errorListener);
    }

    //Invoke a flow
    public static void invokeFlo(String floId, Map<String, String> params, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws Exception {
        String tmpURL = routes.get("invokeFlo").toString();
        String url = tmpURL.replaceAll(":id", floId);
        RequestData request = new RequestData(accessKey, accessSecret, params);
        String jsonData = gson.toJson(request);
        JSONObject data = new JSONObject(jsonData);
        Log.d(TAG, "URL: " + url);
        Log.d(TAG, "Data: " + data.toString());
        JsonObjectRequest req = new JsonObjectRequest(Method.POST, url, data, successListener, errorListener);

        RequestQueue queue = MyVolley.getRequestQueue();
        queue.add(req);
    }
}
