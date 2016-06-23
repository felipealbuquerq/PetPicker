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
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZip;

    public ShelterParcel() {
    }

    protected ShelterParcel(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mPhone = in.readString();
        mEmail = in.readString();
        mAddress = in.readString();
        mCity = in.readString();
        mState = in.readString();
        mZip = in.readString();
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

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getZip() {
        return mZip;
    }

    public void setZip(String zip) {
        mZip = zip;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mPhone);
        parcel.writeString(mEmail);
        parcel.writeString(mAddress);
        parcel.writeString(mCity);
        parcel.writeString(mState);
        parcel.writeString(mZip);
    }
}
