package org.azuqua.android.api.models;


/**
 * Created by sasidhar on 14-Oct-15.
 */

public class Org {
    private long id;
    private String name;
    private String access_key;
    private String access_secret;
    private int isSelected;

    public Org(String name, String access_key, String access_secret) {
        this.name = name;
        this.access_key = access_key;
        this.access_secret = access_secret;
    }

    public Org() {
        // empty constructor
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public void setAccess_secret(String access_secret) {
        this.access_secret = access_secret;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccess_key() {
        return access_key;
    }

    public String getAccess_secret() {
        return access_secret;
    }

    public int getIsSelected() {
        return isSelected;
    }
}
