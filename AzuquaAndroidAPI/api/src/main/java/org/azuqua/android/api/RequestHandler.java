package org.azuqua.android.api;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.azuqua.android.api.callbacks.AsyncRequest;
import org.azuqua.android.api.models.AzuquaError;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sasidhar on 07-Oct-15.
 */

class RequestHandler extends AsyncTask<String, Void, String> {

    private String requestMethod = null;
    private String requestPath = null;
    private String payLoadData = null;
    private String time_stamp = null;
    private String signedData = null;
    private String accessKey = null;

    private AsyncRequest asyncResponse = null;
    private int statusCode;

    private URL url;
    private URLConnection connection;

    public RequestHandler() {
        // empty constructor
    }

    RequestHandler(String requestMethod, String requestPath, String payLoadData, String time_stamp, AsyncRequest asyncResponse) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.payLoadData = payLoadData;
        this.time_stamp = time_stamp;
        this.asyncResponse = asyncResponse;
    }

    RequestHandler(String requestMethod, String requestPath, String payLoadData, String signedData, String accessKey, String time_stamp, AsyncRequest asyncResponse) {
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
            url = new URL(Routes.PROTOCOL, Routes.HOST, Routes.PORT, this.requestPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String error = null;
        AzuquaError azuquaError = new AzuquaError();
        try {
            connection = Routes.PROTOCOL.equals("https") ? (HttpsURLConnection)
                    url.openConnection() : (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoInput(true);

            if (this.requestMethod.equalsIgnoreCase("post"))
                connection.setDoOutput(true);
            else
                connection.setDoOutput(false);

            ((HttpURLConnection) connection).setRequestMethod(this.requestMethod.toUpperCase());
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(this.payLoadData.getBytes().length));
            connection.setRequestProperty("host", Routes.HOST);
            connection.setRequestProperty("x-api-timestamp", this.time_stamp);

            if (signedData != null) {
                connection.setRequestProperty("x-api-hash", this.signedData);
                connection.setRequestProperty("x-api-accessKey", this.accessKey);
            }

            connection.connect();

            if (this.requestMethod.equalsIgnoreCase("post")) {
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(this.payLoadData);
//                Log.d("Request Handler", this.payLoadData);
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            statusCode = ((HttpURLConnection) connection).getResponseCode();
            String response = "";
            if (statusCode == HttpURLConnection.HTTP_OK)
                response = parseResponse(connection.getInputStream());
            else {
                azuquaError.setStatusCode(statusCode);
                String errorResponse = parseResponse(((HttpURLConnection) connection).getErrorStream());
                JsonElement jsonElement = new JsonParser().parse(errorResponse);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonElement str = jsonObject.get("error");
                    azuquaError.setErrorMessage(str.getAsString());
                } else {
                    azuquaError.setErrorMessage(errorResponse);
                }
                response = new Gson().toJson(azuquaError);
            }
//            for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
////                Log.d("logConnection", header.getKey() + "=" + header.getValue());
//            }
            return response;
        } catch (SocketTimeoutException e) {
            azuquaError.setStatusCode(statusCode);
            azuquaError.setErrorMessage("Request timed out error.");
            error = new Gson().toJson(azuquaError);
        } catch (UnknownHostException e) {
            azuquaError.setStatusCode(statusCode);
            azuquaError.setErrorMessage("Invalid host address.");
            error = new Gson().toJson(azuquaError);
        } catch (JsonSyntaxException e) {
            azuquaError.setStatusCode(statusCode);
            azuquaError.setErrorMessage("Invalid path or not a valid request.");
            error = new Gson().toJson(azuquaError);
        } catch (IOException e) {
            azuquaError.setStatusCode(statusCode);
            azuquaError.setErrorMessage(e.toString());
            error = new Gson().toJson(azuquaError);
        } finally {
            ((HttpURLConnection) connection).disconnect();
        }
        return error;
    }

    @Override
    protected void onPostExecute(String responseString) {
        super.onPostExecute(responseString);
        if (statusCode == HttpURLConnection.HTTP_OK) {
            this.asyncResponse.onResponse(responseString);
        } else {
            AzuquaError azuquaError = new Gson().fromJson(responseString, AzuquaError.class);
            this.asyncResponse.onError(azuquaError);
        }
    }

    private String parseResponse(InputStream inputStream) {
        StringBuilder response = new StringBuilder();
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
