package com.azuqua.androidAPI;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by BALASASiDHAR on 27-Apr-15.
 */
public class AzuquaRequest extends AsyncTask<Void, Void, InputStream> {

    public AsyncResponse response;

    private String BASE_URL = "https://api.azuqua.com";
    private String accessKey;
    private String path;
    private String data;
    private String hash;
    private String timestamp;
    private String verb;

    private URL url;
    private HttpsURLConnection connection;
    private DataOutputStream wr = null;

    public AzuquaRequest(String accessKey,String verb,String path,String data, String hash, String timestamp, AsyncResponse response){
        this.response = response;
        this.accessKey = accessKey;
        this.verb = verb;
        this.path = path;
        this.data = data;
        this.hash = hash;
        this.timestamp = timestamp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            url = new URL(BASE_URL+this.path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected InputStream doInBackground(Void... params) {
        try {
            connection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Content-Length", ""+ Integer.toString(this.data.getBytes().length));
        connection.setRequestProperty("x-api-timestamp", this.timestamp);
        connection.setRequestProperty("x-api-hash", this.hash);
        connection.setRequestProperty("x-api-accessKey", this.accessKey);
        try {
            connection.setRequestMethod(this.verb);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        if(verb.equals("POST")){
            try {
                wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return connection.getInputStream();
        } catch (IOException e) {
            return connection.getErrorStream();
        }
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
        try {
            if(connection.getResponseCode() != 200){
                String error = parseStream(inputStream);
                this.response.onErrorResponse(error);
            }else {
                String response = parseStream(inputStream);
                this.response.onResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
    }

    private String parseStream(InputStream inputStream) {
        StringBuffer response = new StringBuffer();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp = response.toString();
        return temp;
    }
}
