package com.dinosilvestro.petpicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShelterListActivity extends AppCompatActivity {

    @BindView(R.id.shelterRecyclerView)
    RecyclerView mRecyclerView;
    private ShelterParcel[] mShelters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelters);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.GET_SHELTERS);
        mShelters = Arrays.copyOf(parcelables, parcelables.length, ShelterParcel[].class);

        ShelterAdapter adapter = new ShelterAdapter(this, mShelters);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }
}