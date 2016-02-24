package org.azuqua.android.api;

import android.os.AsyncTask;
import android.util.Log;

import org.azuqua.android.api.callbacks.AsyncRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sasidhar on 07-Oct-15.
 */

public class RequestHandler extends AsyncTask<String, Void, String> {

    private String requestMethod = null;
    private String requestPath = null;
    private String payLoadData = null;
    private String time_stamp = null;
    private String signedData = null;
    private String accessKey = null;

    private AsyncRequest asyncResponse = null;
    private int statusCode;

    private URL url;
    private DataOutputStream dataOutputStream;
    private URLConnection connection;

    public RequestHandler() {
        // empty constructor
    }

    public RequestHandler(String requestMethod, String requestPath, String payLoadData, String time_stamp, AsyncRequest asyncResponse) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.payLoadData = payLoadData;
        this.time_stamp = time_stamp;
        this.asyncResponse = asyncResponse;
    }

    public RequestHandler(String requestMethod, String requestPath, String payLoadData, String signedData, String accessKey, String time_stamp, AsyncRequest asyncResponse) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.payLoadData = payLoadData;
        this.signedData = signedData;
        this.accessKey = accessKey;
        this.time_stamp = time_stamp;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            url = new URL(Routes.getProtocol(), Routes.getHost(), Routes.getPort(), this.requestPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        IOException ioException;
        try {

            connection = Routes.getProtocol().equals("https") ? (HttpsURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection();

            connection.setUseCaches(false);
            connection.setDoInput(true);
            if (this.requestMethod.equalsIgnoreCase("post"))
                connection.setDoOutput(true);
            else
                connection.setDoOutput(false);

            ((HttpURLConnection) connection).setRequestMethod(this.requestMethod.toUpperCase());
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(this.payLoadData.getBytes().length));
            connection.setRequestProperty("host", Routes.getHost());
            connection.setRequestProperty("x-api-timestamp", this.time_stamp);

            if (signedData != null) {
                connection.setRequestProperty("x-api-hash", this.signedData);
                connection.setRequestProperty("x-api-accessKey", this.accessKey);
            }

            connection.connect();

            if (this.requestMethod.equalsIgnoreCase("post")) {
                dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(this.payLoadData);
//                Log.d("Request Handler", this.payLoadData);
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            statusCode = ((HttpURLConnection) connection).getResponseCode();
            String response = "";
            if (statusCode == HttpURLConnection.HTTP_OK)
                response = parseResponse(connection.getInputStream());
            else
                response = parseResponse(((HttpURLConnection) connection).getErrorStream());

            for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
//                Log.d("logConnection", header.getKey() + "=" + header.getValue());
            }

            return response;

        } catch (IOException e) {
            ioException = e;
            e.printStackTrace();
        } finally {
            ((HttpURLConnection) connection).disconnect();
        }
        return ioException.toString();
    }

    @Override
    protected void onPostExecute(String responseString) {
        super.onPostExecute(responseString);
        if (statusCode == HttpURLConnection.HTTP_OK) {
            this.asyncResponse.onResponse(responseString);
        } else {
            this.asyncResponse.onError(responseString);
        }
    }

    private String parseResponse(InputStream inputStream) {
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
        return response.toString();
    }

}
