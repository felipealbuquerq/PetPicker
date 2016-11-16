package com.dinosilvestro.petpicker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.parcels.ShelterParcel;
import com.dinosilvestro.petpicker.ui.activities.ShelterDetailActivity;

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ShelterAdapterViewHolder> {

    private ShelterParcel[] mShelters;
    private Context mContext;

    public ShelterAdapter(Context context, ShelterParcel[] shelters) {
        mContext = context;
        mShelters = shelters;
    }

    @Override
    public ShelterAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shelter_list_item, parent, false);
        return new ShelterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShelterAdapterViewHolder holder, int position) {
        holder.bindShelter(mShelters[position]);
    }

    @Override
    public int getItemCount() {
        return mShelters.length;
    }

    public class ShelterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mShelterTextView;
        private String mId;
        private String mName;
        private String mPhone;
        private String mEmail;
        private String mAddress;
        private String mCity;
        private String mState;
        private String mZip;

        public ShelterAdapterViewHolder(View itemView) {
            super(itemView);
            mShelterTextView = (TextView) itemView.findViewById(R.id.shelterTextView);
            itemView.setOnClickListener(this);
        }

        public void bindShelter(ShelterParcel shelters) {
            mShelterTextView.setText(trimShelterName(shelters));
            mId = trimEverythingElse(shelters.getId());
            mName = trimEverythingElse(shelters.getName());
            mPhone = trimEverythingElse(shelters.getPhone());
            mEmail = trimEverythingElse(shelters.getEmail());
            mAddress = trimEverythingElse(shelters.getAddress());
            mCity = trimEverythingElse(shelters.getCity());
            mState = trimEverythingElse(shelters.getState());
            mZip = trimEverythingElse(shelters.getZip());
        }

        private String trimEverythingElse(String string) {
            String name = string.replace("{\"$t\":\"", "");
            return name.replace("\"}", "");
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ShelterDetailActivity.class);
            intent.putExtra(Keys.SHELTER_ID, mId);
            intent.putExtra(Keys.SHELTER_NAME, mName);
            intent.putExtra(Keys.SHELTER_PHONE, mPhone);
            intent.putExtra(Keys.SHELTER_EMAIL, mEmail);
            intent.putExtra(Keys.SHELTER_ADDRESS, mAddress);
            intent.putExtra(Keys.SHELTER_CITY, mCity);
            intent.putExtra(Keys.SHELTER_STATE, mState);
            intent.putExtra(Keys.SHELTER_ZIP, mZip);
            mContext.startActivity(intent);
        }

        public String trimShelterName(ShelterParcel shelters) {
            String name = shelters.getName();
            String name1 = name.replace("{\"$t\":\"", "");
            return name1.replace("\"}", "");
        }
    }
}