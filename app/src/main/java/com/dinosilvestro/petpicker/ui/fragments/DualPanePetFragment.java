package com.dinosilvestro.petpicker.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinosilvestro.petpicker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DualPanePetFragment extends Fragment {


    public DualPanePetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dual_pane, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();

        // Left pane (grid of pets)
        PetGridFragment petGridFragment = new PetGridFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.leftPlaceholder, petGridFragment)
                .commit();


        // Right pane (details of selected pet)
        PetDetailFragment petDetailFragment = new PetDetailFragment();

        // Empty bundle
        Bundle bundle = new Bundle();
        petDetailFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.rightPlaceholder, petDetailFragment)
                .commit();

        return view;
    }

}
