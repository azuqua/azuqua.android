package com.azuqua.androidAPI;

public class Flo {
    private final String alias;
    private final String name;

    public Flo(String alias, String name){
        this.alias = alias;
        this.name = name;
    }

    public void invoke() throws  Exception{
        //   Azuqua.invokeFlo(this.alias);
    }

    public String getAlias(){
        return this.alias;
    }

    public String getName(){
        return this.name;
    }
}