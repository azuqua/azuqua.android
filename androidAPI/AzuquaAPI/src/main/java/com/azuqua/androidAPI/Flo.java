package com.azuqua.androidAPI;

import android.os.Parcel;
import android.os.Parcelable;

public class Flo implements Parcelable {
    private String id;
    private String name;
    private String alias;
    private String description;
    private boolean active;

    public Flo(String id, String name, String alias, String description, Boolean active) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.active = active;
    }

    public Flo(Parcel in){
        String[] stringData = new String[4];
        boolean[] booleanData = new boolean[1];

        in.readStringArray(stringData);
        this.id = stringData[0];
        this.name = stringData[1];
        this.alias = stringData[2];
        this.description = stringData[3];

        in.readBooleanArray(booleanData);
        this.active = booleanData[0];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeStringArray(new String[] {
                this.id,
                this.name,
                this.alias,
                this.description
        });

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
    
}
