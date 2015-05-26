package com.azuqua.androidAPI;

import com.azuqua.androidAPI.model.Flo;

import java.util.Collection;

/**
 * Created by BALASASiDHAR on 25-May-15.
 */
public interface AzuquaAllFlosRequest {
    void onResponse(Collection<Flo> flosCollection);
    void onErrorResponse(String error);
}
