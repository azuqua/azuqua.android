package org.azuqua.android.api.callbacks;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public interface AsyncRequest {
    void onResponse(String response);
    void onError(String error);
}
