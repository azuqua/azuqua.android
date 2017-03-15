package org.azuqua.android.api.models;

/**
 * Created by sasidhar on 24/09/16.
 */

public class AzuquaError {
    private int statusCode;
    private String errorMessage;

    public AzuquaError() {
    }

    public AzuquaError(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
