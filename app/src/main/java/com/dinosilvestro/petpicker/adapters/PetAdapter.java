package com.dinosilvestro.petpicker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.parcels.PetParcel;
import com.dinosilvestro.petpicker.ui.activities.PetDetailActivity;
import com.dinosilvestro.petpicker.ui.fragments.PetDetailFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetAdapterViewHolder> {

    private PetParcel[] mPetParcel;
    private Context mContext;

    public PetAdapter(Context context, PetParcel[] petParcel) {
        mContext = context;
        mPetParcel = petParcel;
    }

    @Override
    public PetAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pet_grid_item, parent, false);
        return new PetAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PetAdapterViewHolder holder, int position) {
        holder.bindPet(mPetParcel[position]);
    }

    @Override
    public int getItemCount() {
        return mPetParcel.length;
    }

    public class PetAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPetGridImageView;
        private ProgressBar mProgressBar;
        private String mId;
        private String mName;
        private String mStatus;
        private String mSex;
        private String mSize;
        private String mAge;
        private String mAnimal;
        private String mDescription;
        private String mMedia;

        public PetAdapterViewHolder(View itemView) {
            super(itemView);
            mPetGridImageView = (ImageView) itemView.findViewById(R.id.petGridImageView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            itemView.setOnClickListener(this);
        }

        public void bindPet(PetParcel petParcel) {
            Picasso.with(mContext)
                    .load(petParcel.getMedia())
                    .into(mPetGridImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            // In the rare case that a pet does not have a pic, display app icon
                            mPetGridImageView.setImageResource(R.mipmap.ic_launcher);
                        }
                    });

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

            // Checking to see if user is using a tablet
            boolean isTablet = mContext.getResources().getBoolean(R.bool.is_tablet);

            if (!isTablet) {
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
            } else {
                FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                PetDetailFragment petDetailFragment = new PetDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putString(Keys.PET_ID, mId);
                bundle.putString(Keys.PET_NAME, mName);
                bundle.putString(Keys.PET_STATUS, mStatus);
                bundle.putString(Keys.PET_SEX, mSex);
                bundle.putString(Keys.PET_SIZE, mSize);
                bundle.putString(Keys.PET_AGE, mAge);
                bundle.putString(Keys.PET_ANIMAL, mAnimal);
                bundle.putString(Keys.PET_DESCRIPTION, mDescription);
                bundle.putString(Keys.PET_MEDIA, mMedia);
                petDetailFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.rightPlaceholder, petDetailFragment)
                        .commit();
            }
        }
    }
}
