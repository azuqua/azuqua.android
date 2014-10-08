package com.azuqua.androidAPI;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AzuquaInput implements Parcelable{
    private String name;
    private String type;
    private int position;
    private String[] options;

    public AzuquaInput(){}

    public AzuquaInput(String name, String type, int position, String[] options){
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

        ArrayList<String> tempOptions = new ArrayList<String>();
        for(int i = 2; i < stringData.length; i++){
            tempOptions.add(stringData[i]);
        }

        this.options = tempOptions.toArray(new String[tempOptions.size()]);
        this.position = in.readInt();
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
        out.writeStringArray(this.options);
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

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
