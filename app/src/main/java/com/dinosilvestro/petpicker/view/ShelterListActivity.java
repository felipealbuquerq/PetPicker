package com.dinosilvestro.petpicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.controller.ShelterAdapter;
import com.dinosilvestro.petpicker.controller.ShelterParcel;
import com.dinosilvestro.petpicker.model.FetchData;
import com.dinosilvestro.petpicker.model.Keys;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShelterListActivity extends AppCompatActivity {

    @BindView(R.id.shelterRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.shelterEmptyCardView)
    CardView mShelterEmptyCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelters);
        ButterKnife.bind(this);

        if (!FetchData.mShelterFlag) {
            mShelterEmptyCardView.setVisibility(View.VISIBLE);
        } else {
            Intent intent = getIntent();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(Keys.GET_SHELTERS);
            ShelterParcel[] shelters = Arrays.copyOf(parcelables, parcelables.length, ShelterParcel[].class);

            ShelterAdapter adapter = new ShelterAdapter(this, shelters);
            mRecyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setHasFixedSize(true);
        }

        // Set title of actionbar to the zip code passed in
        setTitle(getString(R.string.pet_shelters_near_text) + MainActivity.mDefaultZipCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_pet_picks) {
            Intent intent = new Intent(this, PetPicksGrid.class);
            startActivity(intent);
        }
        return true;
    }
}