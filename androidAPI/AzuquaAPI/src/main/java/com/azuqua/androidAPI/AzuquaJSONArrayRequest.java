package com.azuqua.androidAPI;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.ParseError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyLog;
import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Map;
import java.util.HashMap;

import java.io.UnsupportedEncodingException;

public class AzuquaJSONArrayRequest extends Request<JSONArray> {
    private static final String TAG = "AzuquaRequest";
    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    private final Listener<JSONArray> listener;
    private final String mRequestBody;
    //private final String timestamp;
    //private final String hash;
    //private final String accessKey;

    public AzuquaJSONArrayRequest(int method, String url, String data,  Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        //this.timestamp = timestamp;
        this.listener = listener;
        this.mRequestBody = data;
        //this.hash = hash;
        //this.accessKey = accessKey;
    }

    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        listener.onResponse(response);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map headers = new HashMap<String, String>();

        /*
        headers.put("Content-Length", Integer.toString(mRequestBody.getBytes().length));
        headers.put("Content-Type", "application/json");
        headers.put("x-api-timestamp", this.timestamp);
        headers.put("x-api-hash", this.hash);
        headers.put("x-api-accessKey", this.accessKey);
*/
        return headers;
    }

}
