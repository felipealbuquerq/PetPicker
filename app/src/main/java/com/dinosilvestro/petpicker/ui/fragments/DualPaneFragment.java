package com.dinosilvestro.petpicker.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinosilvestro.petpicker.R;

public class DualPaneFragment extends Fragment {

    public DualPaneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dual_pane, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();

        // Left pane (list of shelters)
        ShelterListFragment ingredientsFragment = new ShelterListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.leftPlaceholder, ingredientsFragment)
                .commit();


        // Right pane (details of shelter)
        ShelterDetailFragment directionsFragment = new ShelterDetailFragment();

        // Empty bundle
        Bundle bundle = new Bundle();
        directionsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.rightPlaceholder, directionsFragment)
                .commit();

        return view;
    }

}
