package com.dinosilvestro.petpicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        public PetAdapterViewHolder(View itemView) {
            super(itemView);
            mPetGridImageView = (ImageView) itemView.findViewById(R.id.petGridImageView);
            itemView.setOnClickListener(this);
        }

        public void bindPet(PetParcel petParcel) {
            Picasso.with(mContext)
                    .load(petParcel.getMedia())
                    .into(mPetGridImageView);
        }

        private String trimEverythingElse(String string) {
            String name = string.replace("{\"$t\":\"", "");
            return name.replace("\"}", "");
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
