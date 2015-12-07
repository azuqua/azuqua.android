package org.azuqua.android.api.models;

import java.util.List;

/**
 * Created by sasidhar on 29-Oct-15.
 */
public class User {
    private int id;
    private String email;
    private String company;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String access_secret;
    private String access_key;
    private List<Org> orgs;

    public User() {
        //empty constructor
    }

    public User(int id, String email, String company, String first_name, String middle_name, String last_name, String access_key, String access_secret, List<Org> orgs) {
        this.id = id;
        this.email = email;
        this.company = company;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.access_key = access_key;
        this.access_secret = access_secret;
        this.orgs = orgs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAccess_secret() {
        return access_secret;
    }

    public void setAccess_secret(String access_secret) {
        this.access_secret = access_secret;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public void setOrgs(List<Org> orgs) {
        this.orgs = orgs;
    }

    public List<Org> getOrgs() {
        return orgs;
    }
}
