package com.azuqua.androidAPI;

import java.util.Objects;

/**
 * Created by BALASASiDHAR on 27-Apr-15.
 */
public interface AsyncResponse {
    void onResponse(String response);
    void onErrorResponse(String error);
}
