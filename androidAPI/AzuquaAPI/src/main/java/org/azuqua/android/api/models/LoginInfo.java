package org.azuqua.android.api.models;

/**
 * Created by sasidhar on 07-Oct-15.
 */
public class LoginInfo {
    private String email;
    private String password;
    private String partner_name;

    public LoginInfo(){
        // empty constructor
    }

    public LoginInfo(String email, String password, String partner_name ){
        this.email = email;
        this.password = password;
        this.partner_name = partner_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPartner_name() {
        return partner_name;
    }
}
