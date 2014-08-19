package com.azuqua.androidAPI;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Flo implements Parcelable {
    private String id;
    private String name;
    private String alias;
    private String description;
    private String[] inputs;
    private boolean active;

    public Flo(String id, String name, String alias, String description, Boolean active, String[] inputs) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.active = active;
        this.inputs = inputs;
    }

    public Flo(Parcel in){
        String[] stringData = new String[4];
        boolean[] booleanData = new boolean[1];

        in.readStringArray(stringData);
        this.id = stringData[0];
        this.name = stringData[1];
        this.alias = stringData[2];
        this.description = stringData[3];

        //Hack
        ArrayList<String> tempInputs = new ArrayList<String>();
        for(int i = 4; i < stringData.length; i++){
            tempInputs.add(stringData[i]);
        }

        this.inputs = tempInputs.toArray(new String[tempInputs.size()]);

        in.readBooleanArray(booleanData);
        this.active = booleanData[0];
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeStringArray(new String[] {
                this.id,
                this.name,
                this.alias,
                this.description
        });

        out.writeStringArray(this.inputs);

        out.writeBooleanArray(new boolean[] {
                this.active
        });

    }

    public static final Creator<Flo> CREATOR = new Creator<Flo>() {
        public Flo createFromParcel(Parcel in){
            return new Flo(in);
        }

        public Flo[] newArray(int size){
            return new Flo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    //Get Inputs
    public String[] getInputs() {
        return inputs;
    }

}
