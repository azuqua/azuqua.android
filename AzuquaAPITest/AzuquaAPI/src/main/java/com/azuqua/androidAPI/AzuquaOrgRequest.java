package com.azuqua.androidAPI;

import com.azuqua.androidAPI.model.Org;

import java.util.Collection;

/**
 * Created by BALASASiDHAR on 25-May-15.
 */
public interface AzuquaOrgRequest {
    void onResponse(Collection<Org> orgsCollection);
    void onErrorResponse(String error);
}
