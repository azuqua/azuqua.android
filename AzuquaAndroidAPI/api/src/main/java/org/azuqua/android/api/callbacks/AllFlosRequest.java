package org.azuqua.android.api.callbacks;

import org.azuqua.android.api.models.Flo;

import java.util.ArrayList;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public interface AllFlosRequest {
    void onResponse(ArrayList<Flo> floList);
    void onError(String error);
}
