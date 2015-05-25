package com.azuqua.androidAPI;

import com.azuqua.androidAPI.model.Flo;

/**
 * Created by BALASASiDHAR on 25-May-15.
 */
public interface AzuquaFloRequest {
    void onResponse(Flo floData);
    void onErrorResponse(String error);
}
