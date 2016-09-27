package org.azuqua.android.api.callbacks;

import org.azuqua.android.api.models.AzuquaError;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public interface AsyncRequest {
    void onResponse(String response);
    void onError(AzuquaError error);
}
