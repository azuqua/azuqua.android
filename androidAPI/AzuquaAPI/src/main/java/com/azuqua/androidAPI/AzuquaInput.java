package com.azuqua.androidAPI;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AzuquaInput implements Parcelable{
    private String name;
    private String type;
    private int position;
    private ArrayList<String> options;

    public AzuquaInput(){}

    public AzuquaInput(String name, String type, int position, ArrayList<String> options){
        this.name = name;
        this.type = type;
        this.position = position;
        this.options = options;
    }

    public AzuquaInput(Parcel in){
        String[] stringData = new String[2];

        in.readStringArray(stringData);
        this.name = stringData[0];
        this.type = stringData[1];
        this.position = in.readInt();

        this.options  =  in.readArrayList(String.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeStringArray(new String[] {
                this.name,
                this.type
        });

        out.writeInt(this.position);
        out.writeList(this.options);
    }

    public static final Creator<AzuquaInput> CREATOR = new Creator<AzuquaInput>() {
        public AzuquaInput createFromParcel(Parcel in){
            return new AzuquaInput(in);
        }

        public AzuquaInput[] newArray(int size){
            return new AzuquaInput[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
