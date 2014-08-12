package com.azuqua.androidAPI;

import java.util.List;

public class Org {
    private final String name;
    private final String accessKey;
    private final String accessSecret;
    private List<Flo> flos;

    public Org(String name, String accessKey, String accessSecret){
        this.name = name;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    //Get Org's name
    public String getName(){
        return this.name;
    }

    public void getFlos(){

    }


}
