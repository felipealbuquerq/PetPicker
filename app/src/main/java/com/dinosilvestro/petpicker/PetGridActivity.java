package com.dinosilvestro.petpicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetGridActivity extends AppCompatActivity {

    @BindView(R.id.petRecyclerView)
    RecyclerView mPetRecyclerView;
    @BindView(R.id.emptyCardView)
    CardView mEmptyCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_grid);
        ButterKnife.bind(this);

        if (!FetchData.mPetFlag) {
            mEmptyCardView.setVisibility(View.VISIBLE);
        } else {
            Intent intent = getIntent();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(Keys.GET_PETS);
            PetParcel[] petParcels = Arrays.copyOf(parcelables, parcelables.length, PetParcel[].class);

            PetAdapter adapter = new PetAdapter(this, petParcels);
            mPetRecyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
            mPetRecyclerView.setLayoutManager(layoutManager);

            mPetRecyclerView.setHasFixedSize(true);

            String shelterName = intent.getStringExtra(Keys.NEW_SHELTER_NAME);
            setTitle(shelterName);
        }
    }
}
