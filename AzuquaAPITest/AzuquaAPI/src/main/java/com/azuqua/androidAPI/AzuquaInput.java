package com.azuqua.androidAPI;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AzuquaInput implements Parcelable{
    private String name;
    private String type;
    private String description;
    private String[] options;

    public AzuquaInput(Parcel in){
        String[] stringData = new String[3];

        in.readStringArray(stringData);
        this.name = stringData[0];
        this.description = stringData[1];
        this.type = stringData[2];

        ArrayList<String> tempOptions = new ArrayList<String>();
        for(int i = 3; i < stringData.length; i++){
            tempOptions.add(stringData[i]);
        }

        this.options = tempOptions.toArray(new String[tempOptions.size()]);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeStringArray(new String[] {
                this.name,
                this.description,
                this.type
        });

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

}
