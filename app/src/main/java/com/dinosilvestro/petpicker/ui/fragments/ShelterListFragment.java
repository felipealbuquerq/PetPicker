package com.dinosilvestro.petpicker.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.adapters.ShelterAdapter;
import com.dinosilvestro.petpicker.fetch.FetchData;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.parcels.ShelterParcel;
import com.dinosilvestro.petpicker.ui.activities.MainActivity;

import java.util.Arrays;

public class ShelterListFragment extends Fragment {

    public ShelterListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_shelters, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.shelterRecyclerView);
        CardView mShelterEmptyCardView = (CardView) rootView.findViewById(R.id.shelterEmptyCardView);

        if (!FetchData.mShelterFlag) {
            mShelterEmptyCardView.setVisibility(View.VISIBLE);
        } else {
            Intent intent = getActivity().getIntent();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(Keys.GET_SHELTERS);
            ShelterParcel[] shelters = Arrays.copyOf(parcelables, parcelables.length, ShelterParcel[].class);

            ShelterAdapter adapter = new ShelterAdapter(getActivity(), shelters);
            mRecyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setHasFixedSize(true);
        }

        // Set title of actionbar to the zip code passed in
        getActivity().setTitle(getString(R.string.pet_shelters_near_text) + MainActivity.mDefaultZipCode);

        return rootView;
    }
}
