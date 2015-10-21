package org.azuqua.android.api.callbacks;

import org.azuqua.android.api.models.Flo;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public interface FloDataRequest {
    void onResponse(Flo floData);
    void onError(String error);
}
