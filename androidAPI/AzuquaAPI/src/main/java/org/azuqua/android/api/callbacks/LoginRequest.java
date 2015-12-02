package org.azuqua.android.api.callbacks;

import org.azuqua.android.api.models.Org;
import org.azuqua.android.api.models.User;

import java.util.List;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public interface LoginRequest {
    void onResponse(User user);
    void onError(String error);
}
