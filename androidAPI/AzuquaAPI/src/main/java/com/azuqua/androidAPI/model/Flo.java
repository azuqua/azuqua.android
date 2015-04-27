package com.azuqua.androidAPI.model;

import com.azuqua.androidAPI.AsyncResponse;
import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.AzuquaException;

/**
 * Created by BALASASiDHAR on 25-Apr-15.
 */
public class Flo {
    private static boolean DEBUG = true;
    private String name;
    private String alias;
    private Azuqua azuqua;

    public Flo(String name, String alias){
        this.name = name;
        this.alias = alias;
    }

    public Flo(){}

    public String getName(){ return name; }
    public String getAlias(){ return alias; }
    public void setName(String name){ this.name = name; }
    public void setAlias(String alias){ this.alias = alias; }
    public void setAzuqua(Azuqua azuqua) { this.azuqua = azuqua; }


    /**
     * Wrapper for System.out.println.
     * @param objects
     */
    public static void o(String method, String msg) {
        if (DEBUG) {
            System.out.println(Flo.class.getName() + "." + method + ": " + msg);
        }
    }

    public String invoke(String json, AsyncResponse response) throws AzuquaException {
        String method = "invoke";
        String path = Azuqua.invokeRoute.replace(":id", this.alias);
        o(method, "path " + path);
        o(method, "json " + json);
        String out = null;
        try {
            out = azuqua.makeRequest("POST", path, json, response);
        } catch (Exception e) {
            throw new AzuquaException(e);
        }
        return out;
    }
}
