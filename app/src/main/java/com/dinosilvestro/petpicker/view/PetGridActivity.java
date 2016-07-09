package com.dinosilvestro.petpicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.controller.PetAdapter;
import com.dinosilvestro.petpicker.controller.PetParcel;
import com.dinosilvestro.petpicker.model.FetchData;
import com.dinosilvestro.petpicker.model.Keys;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetGridActivity extends AppCompatActivity {

    @BindView(R.id.petRecyclerView)
    RecyclerView mPetRecyclerView;
    @BindView(R.id.emptyCardView)
    CardView mEmptyCardView;
    @BindView(R.id.petGridEmptyTextView)
    TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_grid);
        ButterKnife.bind(this);

        if (!FetchData.mPetFlag) {
            mEmptyCardView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(R.string.empty_adoption_grid_text);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_pet_picks) {
            Intent intent = new Intent(this, PetPicksGridActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
