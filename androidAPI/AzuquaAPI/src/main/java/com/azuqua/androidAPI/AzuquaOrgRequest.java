package com.azuqua.androidAPI;

import com.azuqua.androidAPI.model.Org;

import java.util.List;

/**
 * Created by BALASASiDHAR on 25-May-15.
 */
public interface AzuquaOrgRequest {
    void onResponse(List<Org> orgsCollection);
    void onErrorResponse(String error);
}
