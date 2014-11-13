package com.azuqua.androidAPI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Flo implements Serializable {
    private String id;
    private String name;
    private String alias;
    private String description;
    private boolean active;
    private int offset;
    private List<ArrayList<AzuquaInput>> state;

    public Flo(){}

    public Flo(String id, String name, String alias, String description, Boolean active, int offset, List state) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.active = active;
        this.offset = offset;
        this.state = state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArrayList<AzuquaInput>> getState() {
        return state;
    }

    public void setState(List<ArrayList<AzuquaInput>> state) {
        this.state = state;
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    //Get Id
    public String getId(){
        return this.id;
    }

    //Get Name
    public String getName(){
        return this.name;
    }

    //Get Alias
    public String getAlias(){
        return this.alias;
    }

    //Get Description
    public String getDescription(){
        return this.description;
    }

    //Get Active
    public boolean getActive(){
        return this.active;
    }

    //Get Offset
    public int getOffset(){
        return  this.offset;
    }

}
