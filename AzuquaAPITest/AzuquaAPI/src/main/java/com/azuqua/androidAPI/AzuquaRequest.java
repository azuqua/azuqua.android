package com.azuqua.androidAPI;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by BALASASiDHAR on 27-Apr-15.
 */
public class AzuquaRequest extends AsyncTask<Void, Void, String> {

    public AsyncResponse response;

    private String accessKey;
    private String path;
    private String data;
    private String hash;
    private String timestamp;
    private String verb;

    private URL url;
    private URLConnection connection;
    private DataOutputStream wr = null;

    private static int STATUS_CODE = 0;

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
            url = new URL(Routes.BASE_URL+this.path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            connection = Routes.DEBUG_MODE ? (HttpURLConnection) url.openConnection() : (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Content-Length", ""+ Integer.toString(this.data.getBytes().length));
        connection.setRequestProperty("x-api-timestamp", this.timestamp);
        connection.setRequestProperty("x-api-hash", this.hash);
        connection.setRequestProperty("x-api-accessKey", this.accessKey);
        try {
            ( (HttpURLConnection) connection).setRequestMethod(this.verb);
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
            STATUS_CODE = ((HttpURLConnection) connection).getResponseCode();
            String response = parseStream(connection.getInputStream());
            return response;
        } catch (IOException e) {
            String error = parseStream( ( (HttpURLConnection) connection).getErrorStream() );
            e.printStackTrace();
            return error;
        }finally {
            ( (HttpURLConnection) connection).disconnect();
        }
    }

    @Override
    protected void onPostExecute(String inputStream) {
        super.onPostExecute(inputStream);

        if(STATUS_CODE != 200)
            this.response.onErrorResponse(inputStream);
        else
            this.response.onResponse(inputStream);
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
