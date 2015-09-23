package com.azuqua.androidAPI;

import com.azuqua.androidAPI.model.Flo;

import java.util.List;

/**
 * Created by BALASASiDHAR on 25-May-15.
 */
public interface AzuquaAllFlosRequest {
    void onResponse(List<Flo> flosCollection);
    void onErrorResponse(String error);
}
