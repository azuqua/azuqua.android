package com.azuqua.androidAPI;

import java.util.ArrayList;

public class Org {
    private final String name;
    private final String accessKey;
    private final String accessSecret;
    private final ArrayList flos;

    public Org(String name, String accessKey, String accessSecret, ArrayList flos){
        this.name = name;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.flos = flos;
    }

    //Get Name
    public String getName(){
        return this.name;
    }

    //Get AccessKey
    public String getAccessKey(){
        return this.accessKey;
    }

    //Get AccessSecret
    public String getAccessSecret(){
        return this.accessSecret;
    }

    //Get Flos
    public ArrayList<Flo> getFlos(){
        return this.flos;
    }
}
