package org.azuqua.android.api.models;

/**
 * Created by sasidhar on 07-Oct-15.
 */
public class LoginInfo {
    private String email;
    private String password;

    public LoginInfo() {
        // empty constructor
    }

    public LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
