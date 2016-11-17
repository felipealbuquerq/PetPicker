package com.dinosilvestro.petpicker.ui.fragments;


import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.adapters.PetPicksAdapter;
import com.dinosilvestro.petpicker.data.PetContract;
import com.dinosilvestro.petpicker.parcels.PetParcel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetPicksGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<PetParcel> mArrayPetParcel = new ArrayList<>();
    private PetPicksAdapter mPetPicksAdapter;

    public PetPicksGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_pet_grid, container, false);

        RecyclerView mPetRecyclerView = (RecyclerView) rootView.findViewById(R.id.petRecyclerView);
        CardView mEmptyCardView = (CardView) rootView.findViewById(R.id.emptyCardView);
        TextView mEmptyTextView = (TextView) rootView.findViewById(R.id.petGridEmptyTextView);

        getActivity().setTitle("My Pet Picks");

        if (mArrayPetParcel == null || mArrayPetParcel.size() == 0) {
            Cursor cursor = getActivity().getApplicationContext()
                    .getContentResolver()
                    .query(PetContract.PetEntry.CONTENT_URI, null, null, null, null);
            onLoadFinished(null, cursor);

            mPetPicksAdapter = new PetPicksAdapter(getActivity(), mArrayPetParcel);
            mPetRecyclerView.setAdapter(mPetPicksAdapter);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            mPetRecyclerView.setLayoutManager(layoutManager);

            mPetRecyclerView.setHasFixedSize(true);

        }

        if (mArrayPetParcel == null || mArrayPetParcel.size() == 0) {
            mEmptyCardView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(R.string.pet_grid_empty_text);
        }

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        while (cursor.moveToNext()) {
            PetParcel petParcel = new PetParcel();

            petParcel.setId(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_ID)));
            petParcel.setName(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME)));
            petParcel.setStatus(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_STATUS)));
            petParcel.setSex(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_SEX)));
            petParcel.setSize(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_SIZE)));
            petParcel.setAge(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_AGE)));
            petParcel.setAnimal(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_ANIMAL)));
            petParcel.setDescription(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_DESCRIPTION)));
            petParcel.setMedia(cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_MEDIA)));

            mArrayPetParcel.add(petParcel);
        }
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPetPicksAdapter.notifyDataSetChanged();
    }

}
