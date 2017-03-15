package org.azuqua.android.api.callbacks;

import org.azuqua.android.api.models.AzuquaError;
import org.azuqua.android.api.models.User;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public interface LoginRequest {
    void onResponse(User user);
    void onError(AzuquaError error);
}
