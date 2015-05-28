package com.azuqua.androidAPI.model;

import com.azuqua.androidAPI.AsyncResponse;
import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.AzuquaException;
import com.azuqua.androidAPI.AzuquaFloRequest;
import com.azuqua.androidAPI.Routes;
import com.google.gson.Gson;

/**
 * Created by BALASASiDHAR on 25-Apr-15.
 */
public class Flo {

    private static Gson gson = new Gson();

    private int id;
    private String alias;
    private String name;
    private Boolean active;
    private Boolean published;
    private String description;
    private String created;
    private String updated;
    private String version;
    private String security_level;
    private String client_token;
    private String[] inputs;

    private Azuqua azuqua;

    public Flo(int id, String alias, String name, String version, Boolean active, Boolean published, String security_level, String client_token,String description, String created, String updated, String[] inputs){
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.version = version;
        this.active = active;
        this.published = published;
        this.security_level = security_level;
        this.client_token =  client_token;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.inputs = inputs;
    }

    public Flo(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdated() {
        return updated;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setClient_token(String client_token) {
        this.client_token = client_token;
    }

    public String getClient_token() {
        return client_token;
    }

    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    public String[] getInputs() {
        return inputs;
    }

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
        System.out.println(Flo.class.getName() + "." + method + ": " + msg);
    }

    public String invoke(String json, AsyncResponse response) throws AzuquaException {
        String method = "invoke";
        String path = Routes.FLO_RUN.replace(":alias", this.alias);
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

    public String read(final AzuquaFloRequest request){
        String method = "read";
        String path = Routes.FLO_READ.replace(":alias", this.alias);
        o(method, "path " + path);
        String out = null;
        try {
            out = azuqua.makeRequest("GET", path, "", new AsyncResponse() {
                @Override
                public void onResponse(String response) {
                    request.onResponse(gson.fromJson(response,Flo.class));
                }

                @Override
                public void onErrorResponse(String error) {
                    request.onErrorResponse(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public String enable(final AzuquaFloRequest request) {
        String method = "read";
        String path = Routes.FLO_ENABLE.replace(":alias", this.alias);
        o(method, "path " + path);
        String out = null;
        try {
            out = azuqua.makeRequest("GET", path, "", new AsyncResponse() {
                @Override
                public void onResponse(String response) {
                    request.onResponse(gson.fromJson(response,Flo.class));
                }

                @Override
                public void onErrorResponse(String error) {
                    request.onErrorResponse(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public String disable(final AzuquaFloRequest request) {
        String method = "read";
        String path = Routes.FLO_DISABLE.replace(":alias", this.alias);
        o(method, "path " + path);
        String out = null;
        try {
            out = azuqua.makeRequest("GET", path, "", new AsyncResponse() {
                @Override
                public void onResponse(String response) {
                    request.onResponse(gson.fromJson(response,Flo.class));
                }

                @Override
                public void onErrorResponse(String error) {
                    request.onErrorResponse(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}
