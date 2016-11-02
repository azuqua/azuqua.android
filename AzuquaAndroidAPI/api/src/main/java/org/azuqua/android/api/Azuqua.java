package org.azuqua.android.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.azuqua.android.api.callbacks.AllFlosRequest;
import org.azuqua.android.api.callbacks.AsyncRequest;
import org.azuqua.android.api.callbacks.LoginRequest;
import org.azuqua.android.api.models.AzuquaError;
import org.azuqua.android.api.models.Flo;
import org.azuqua.android.api.models.LoginInfo;
import org.azuqua.android.api.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static android.R.attr.data;

/**
 * Created by sasidhar on 07-Oct-15.
 */

public class Azuqua {


    public Azuqua() {
    }

    public Azuqua(String protocol, String host, int port) {
        Routes.PROTOCOL = protocol;
        Routes.HOST = host;
        Routes.PORT = port;
    }

    private Gson gson = new Gson();

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public void login(String email, String password, final LoginRequest orgListRequest) {
        String loginInfo = gson.toJson(new LoginInfo(email, password));
        RequestHandler requestHandler = new RequestHandler("POST", Routes.ORG_LOGIN, loginInfo, getISOTime(), new AsyncRequest() {
            @Override
            public void onResponse(String response) {
                try {
                    Type collectionType = new TypeToken<User>() {
                    }.getType();
                    User user = gson.fromJson(response, collectionType);
                    orgListRequest.onResponse(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(AzuquaError error) {
                orgListRequest.onError(error);
            }
        });
        requestHandler.execute();
    }

    public void getFlos(long org_id, String accessKey, String accessSecret, final AllFlosRequest allFlosRequest) {

        String timestamp = getISOTime();

        Routes.ORG_FLOS = Routes.ORG_FLOS.replace(":org_id", "" + org_id);

        String data = "{\"org_id\":\"" + org_id + "\",\"channel_key\":\"azuquamobile\"}";

        String signedData = signData(data, "get", Routes.ORG_FLOS, accessSecret, timestamp);

        RequestHandler requestHandler = new RequestHandler("GET", Routes.ORG_FLOS, "",
                signedData, accessKey, timestamp, new AsyncRequest() {
            @Override
            public void onResponse(String response) {
                ArrayList<Flo> activeFlos = new ArrayList<>();
                activeFlos.clear();
                try {
                    Type collectionType = new TypeToken<List<Flo>>() {
                    }.getType();

                    List<Flo> floList = gson.fromJson(response, collectionType);

                    if (floList != null && !floList.isEmpty()) {
                        for (Flo flo : floList) {
                            if (flo.isActive()) {
                                activeFlos.add(flo);
                            }
                        }
                    }

                    allFlosRequest.onResponse(activeFlos);
                } catch (IndexOutOfBoundsException ee) {
                    allFlosRequest.onResponse(activeFlos);
                } catch (Exception e) {
                    AzuquaError error = new AzuquaError();
                    error.setStatusCode(200);
                    error.setErrorMessage(e.toString());
                    allFlosRequest.onError(error);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(AzuquaError error) {
                allFlosRequest.onError(error);
            }
        });

        requestHandler.execute();
    }

    public void getFloInputs(String alias, String accessKey, String accessSecret, final AsyncRequest asyncRequest) {
        String timestamp = getISOTime();
        String route = Routes.FLO_INPUTS.replace(":alias", alias);
        String signedData = signData("", "get", route, accessSecret, timestamp);
        RequestHandler requestHandler = new RequestHandler("GET", route, "", signedData, accessKey, timestamp, new AsyncRequest() {
            @Override
            public void onResponse(String response) {
                asyncRequest.onResponse(response);
            }

            @Override
            public void onError(AzuquaError error) {
                asyncRequest.onError(error);
            }
        });

        requestHandler.execute();
    }

    public void getFloOutputs(String alias, String accessKey, String accessSecret, final AsyncRequest asyncRequest) {
        String timestamp = getISOTime();
        String route = Routes.FLO_OUTPUTS.replace(":alias", alias);
        String signedData = signData("", "get", route, accessSecret, timestamp);
        RequestHandler requestHandler = new RequestHandler("GET", route, "", signedData, accessKey, timestamp, new AsyncRequest() {
            @Override
            public void onResponse(String response) {
                asyncRequest.onResponse(response);
            }

            @Override
            public void onError(AzuquaError error) {
                asyncRequest.onError(error);
            }
        });

        requestHandler.execute();
    }

    public void invokeFlo(String alias, String data, String accessKey, String accessSecret, final AsyncRequest asyncRequest) {

        String timestamp = getISOTime();
        String route = Routes.FLO_INVOKE.replace(":alias", alias);
        String signedData = signData(data, "post", route, accessSecret, timestamp);

        final AzuquaError error = new AzuquaError();

        RequestHandler requestHandler = new RequestHandler("POST", route, data, signedData, accessKey, timestamp, new AsyncRequest() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("error")) {
                        error.setStatusCode(200);
                        error.setErrorMessage(response);
                        asyncRequest.onError(error);
                    } else if (jsonObject.has("data")) {
                        String dataString = jsonObject.getString("data");
                        JSONObject dataObj = new JSONObject(dataString);

                        if (dataObj.has("error")) {
                            error.setStatusCode(200);
                            error.setErrorMessage(dataString);
                            asyncRequest.onError(error);
                        } else {
                            asyncRequest.onResponse(response);
                        }
                    } else {
                        asyncRequest.onResponse(response);
                    }
                } catch (JSONException e) {
                    error.setStatusCode(200);
                    error.setErrorMessage(e.toString());
                    asyncRequest.onError(error);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(AzuquaError error) {
                asyncRequest.onError(error);
            }
        });
        requestHandler.execute();
    }

    private String getISOTime() {
        TimeZone timezone = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        df.setTimeZone(timezone);
        return df.format(new Date());
    }

    private String signData(String data, String verb, String path, String accessSecret, String timestamp) {

        Mac hmac = null;
        try {
            hmac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKeySpec key = null;
        try {
            key = new SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            assert hmac != null;
            hmac.init(key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        String meta = verb + ":" + path + ":" + timestamp;

//        if (path.equalsIgnoreCase(Routes.ORG_FLOS)) {
//            meta += "{" + data + "}";
//        }

        String dataToDigest = meta + data;

        byte[] digest = new byte[0];
        try {
            digest = hmac.doFinal(dataToDigest.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bytesToHex(digest).toLowerCase();
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
