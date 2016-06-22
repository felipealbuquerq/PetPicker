package com.dinosilvestro.petpicker;

import android.os.Parcel;
import android.os.Parcelable;

public class Shelters implements Parcelable {
    public static final Creator<Shelters> CREATOR = new Creator<Shelters>() {
        @Override
        public Shelters createFromParcel(Parcel in) {
            return new Shelters(in);
        }

        @Override
        public Shelters[] newArray(int size) {
            return new Shelters[size];
        }
    };
    private String mId;
    private String mName;

    public Shelters() {
    }

    protected Shelters(Parcel in) {
        mId = in.readString();
        mName = in.readString();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


    @Override
    public int describeContents() {
        return 0; // Not using this
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mName);
    }
}
