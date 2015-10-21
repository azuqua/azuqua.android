package org.azuqua.android.api.callbacks;

import org.azuqua.android.api.models.Org;

import java.util.List;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public interface OrgListRequest {
    void onResponse(List<Org> orgList);
    void onError(String error);
}
