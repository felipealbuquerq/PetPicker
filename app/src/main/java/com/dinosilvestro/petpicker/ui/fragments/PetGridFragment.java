package com.dinosilvestro.petpicker.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.adapters.PetAdapter;
import com.dinosilvestro.petpicker.fetch.FetchData;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.parcels.PetParcel;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetGridFragment extends Fragment {

    public PetGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_pet_grid, container, false);

        RecyclerView mPetRecyclerView = (RecyclerView) rootView.findViewById(R.id.petRecyclerView);
        CardView mEmptyCardView = (CardView) rootView.findViewById(R.id.emptyCardView);
        TextView mEmptyTextView = (TextView) rootView.findViewById(R.id.petGridEmptyTextView);

        // If there isn't any pet data available for the selected shelter...
        if (!FetchData.mPetFlag) {
            // ...display a message saying there are no pets available.
            mEmptyCardView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(R.string.empty_adoption_grid_text);
        } else {
            Intent intent = getActivity().getIntent();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(Keys.GET_PETS);
            PetParcel[] petParcels = Arrays.copyOf(parcelables, parcelables.length, PetParcel[].class);

            PetAdapter adapter = new PetAdapter(getActivity(), petParcels);
            mPetRecyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            mPetRecyclerView.setLayoutManager(layoutManager);

            mPetRecyclerView.setHasFixedSize(true);

            String shelterName = intent.getStringExtra(Keys.NEW_SHELTER_NAME);

            // Set title of actionbar to the name of the shelter
            getActivity().setTitle(shelterName);
        }

        return rootView;
    }

}
