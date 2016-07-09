package com.dinosilvestro.petpicker.controller;

import android.os.Parcel;
import android.os.Parcelable;

public class PetPicksParcel implements Parcelable {

    //Parcel Creator
    public static final Parcelable.Creator<PetPicksParcel> CREATOR = new Parcelable.Creator<PetPicksParcel>() {

        @Override
        public PetPicksParcel createFromParcel(Parcel parcel) {
            return new PetPicksParcel(parcel);
        }

        @Override
        public PetPicksParcel[] newArray(int i) {
            return new PetPicksParcel[i];
        }
    };

    private String mId;
    private String mName;
    private String mStatus;
    private String mSex;
    private String mSize;
    private String mAge;
    private String mAnimal;
    private String mDescription;
    private String mMedia;

    public PetPicksParcel() {
    }

    public PetPicksParcel(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mStatus = in.readString();
        mSex = in.readString();
        mSize = in.readString();
        mAge = in.readString();
        mAnimal = in.readString();
        mDescription = in.readString();
        mMedia = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mStatus);
        parcel.writeString(mSex);
        parcel.writeString(mSize);
        parcel.writeString(mAge);
        parcel.writeString(mAnimal);
        parcel.writeString(mDescription);
        parcel.writeString(mMedia);
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public String getAnimal() {
        return mAnimal;
    }

    public void setAnimal(String animal) {
        mAnimal = animal;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getMedia() {
        return mMedia;
    }

    public void setMedia(String media) {
        mMedia = media;
    }

    @Override
    public int describeContents() {
        return 0; // Not using this
    }
}