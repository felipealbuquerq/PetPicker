package com.dinosilvestro.petpicker.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.model.Keys;
import com.dinosilvestro.petpicker.view.PetDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PetPicksAdapter extends RecyclerView.Adapter<PetPicksAdapter.PetPicksAdapterViewHolder> {

    ArrayList<PetParcel> mPetParcel;
    private Context mContext;


    public PetPicksAdapter(Context context, ArrayList<PetParcel> petParcels) {
        mContext = context;
        mPetParcel = petParcels;
    }


    @Override
    public PetPicksAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pet_grid_item, parent, false);
        return new PetPicksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PetPicksAdapterViewHolder holder, int position) {
        holder.bindPet(mPetParcel.get(position));
    }

    @Override
    public int getItemCount() {
        return mPetParcel.size();
    }

    public class PetPicksAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPetGridImageView;
        private String mId;
        private String mName;
        private String mStatus;
        private String mSex;
        private String mSize;
        private String mAge;
        private String mAnimal;
        private String mDescription;
        private String mMedia;

        public PetPicksAdapterViewHolder(View itemView) {
            super(itemView);
            mPetGridImageView = (ImageView) itemView.findViewById(R.id.petGridImageView);
            itemView.setOnClickListener(this);
        }

        public void bindPet(PetParcel petParcel) {
            Picasso.with(mContext)
                    .load(petParcel.getMedia())
                    .into(mPetGridImageView);

            mId = trimEverythingElse(petParcel.getId());
            mName = trimEverythingElse(petParcel.getName());
            mStatus = trimEverythingElse(petParcel.getStatus());
            mSex = trimEverythingElse(petParcel.getSex());
            mSize = trimEverythingElse(petParcel.getSize());
            mAge = trimEverythingElse(petParcel.getAge());
            mAnimal = trimEverythingElse(petParcel.getAnimal());
            mDescription = trimEverythingElse(petParcel.getDescription());
            mMedia = petParcel.getMedia();
        }

        private String trimEverythingElse(String string) {
            String name = string.replace("{\"$t\":\"", "");
            return name.replace("\"}", "");
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, PetDetailActivity.class);
            intent.putExtra(Keys.PET_ID, mId);
            intent.putExtra(Keys.PET_NAME, mName);
            intent.putExtra(Keys.PET_STATUS, mStatus);
            intent.putExtra(Keys.PET_SEX, mSex);
            intent.putExtra(Keys.PET_SIZE, mSize);
            intent.putExtra(Keys.PET_AGE, mAge);
            intent.putExtra(Keys.PET_ANIMAL, mAnimal);
            intent.putExtra(Keys.PET_DESCRIPTION, mDescription);
            intent.putExtra(Keys.PET_MEDIA, mMedia);
            mContext.startActivity(intent);
        }
    }
}
