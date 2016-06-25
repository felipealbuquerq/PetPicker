package com.dinosilvestro.petpicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetGridActivity extends AppCompatActivity {

    @BindView(R.id.petRecyclerView)
    RecyclerView mPetRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_grid);
        ButterKnife.bind(this);

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
