package com.azuqua.androidAPI.model;

/**
 * Created by BALASASiDHAR on 25-Apr-15.
 */
public class Org {

    private String name;
    private String access_key;
    private String access_secret;

    public Org(String name, String access_key, String access_secret){
        this.name = name;
        this.access_key = access_key;
        this.access_secret = access_secret;
    }


    public void setName(String name) {
        this.name = name;
    }

    //Get Name
    public String getName(){
        return this.name;
    }

    public void setaccess_key(String access_key) {
        this.access_key = access_key;
    }

    public void setaccess_secret(String access_secret) {
        this.access_secret = access_secret;
    }


    //Get access_key
    public String getaccess_key(){
        return this.access_key;
    }

    //Get access_secret
    public String getaccess_secret(){
        return this.access_secret;
    }
}
