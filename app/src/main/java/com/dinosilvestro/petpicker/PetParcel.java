package com.dinosilvestro.petpicker;

import android.os.Parcel;
import android.os.Parcelable;

public class PetParcel implements Parcelable {
    public static final Creator<PetParcel> CREATOR = new Creator<PetParcel>() {
        @Override
        public PetParcel createFromParcel(Parcel in) {
            return new PetParcel(in);
        }

        @Override
        public PetParcel[] newArray(int size) {
            return new PetParcel[size];
        }
    };

    private static PetParcel[] mPets;
    private String mId;
    private String mName;
    private String mStatus;
    private String mSex;
    private String mSize;
    private String mAge;
    private String mAnimal;
    private String mDescription;
    private String mMedia;

    protected PetParcel(Parcel in) {
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

    public PetParcel() {

    }

    public static PetParcel[] getPets() {
        return mPets;
    }

    public void setPets(PetParcel[] pets) {
        mPets = pets;
    }

    @Override
    public int describeContents() {
        return 0; // Not using this
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
}
