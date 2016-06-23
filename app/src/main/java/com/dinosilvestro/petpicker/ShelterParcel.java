package com.dinosilvestro.petpicker;

import android.os.Parcel;
import android.os.Parcelable;

public class ShelterParcel implements Parcelable {
    public static final Creator<ShelterParcel> CREATOR = new Creator<ShelterParcel>() {
        @Override
        public ShelterParcel createFromParcel(Parcel in) {
            return new ShelterParcel(in);
        }

        @Override
        public ShelterParcel[] newArray(int size) {
            return new ShelterParcel[size];
        }
    };
    private String mId;
    private String mName;

    public ShelterParcel() {
    }

    protected ShelterParcel(Parcel in) {
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
