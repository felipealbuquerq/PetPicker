package com.dinosilvestro.petpicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        public TextView mShelterTextView;

        public ShelterAdapterViewHolder(View itemView) {
            super(itemView);

            mShelterTextView = (TextView) itemView.findViewById(R.id.shelterTextView);

            itemView.setOnClickListener(this);
        }

        public void bindShelter(ShelterParcel shelters) {
            mShelterTextView.setText(trimShelterName(shelters));
        }

        @Override
        public void onClick(View v) {
        }

        public String trimShelterName(ShelterParcel shelters) {
            String name = shelters.getName();
            String name1 = name.replace("{\"$t\":\"", "");
            return name1.replace("\"}", "");
        }
    }
}