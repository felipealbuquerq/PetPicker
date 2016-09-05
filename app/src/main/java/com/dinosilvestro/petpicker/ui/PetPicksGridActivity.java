package com.dinosilvestro.petpicker.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.adapters.PetPicksAdapter;
import com.dinosilvestro.petpicker.data.PetContract;
import com.dinosilvestro.petpicker.parcels.PetParcel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetPicksGridActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.petRecyclerView)
    RecyclerView mPetRecyclerView;
    @BindView(R.id.emptyCardView)
    CardView mEmptyCardView;
    @BindView(R.id.petGridEmptyTextView)
    TextView mEmptyTextView;

    private ArrayList<PetParcel> mArrayPetParcel = new ArrayList<>();
    private PetPicksAdapter mPetPicksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_grid);
        ButterKnife.bind(this);

        setTitle("My Pet Picks");

        if (mArrayPetParcel == null || mArrayPetParcel.size() == 0) {
            Cursor cursor = getApplicationContext()
                    .getContentResolver()
                    .query(PetContract.PetEntry.CONTENT_URI, null, null, null, null);
            onLoadFinished(null, cursor);

            mPetPicksAdapter = new PetPicksAdapter(this, mArrayPetParcel);
            mPetRecyclerView.setAdapter(mPetPicksAdapter);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
            mPetRecyclerView.setLayoutManager(layoutManager);

            mPetRecyclerView.setHasFixedSize(true);

        }
        if (mArrayPetParcel == null || mArrayPetParcel.size() == 0) {
            mEmptyCardView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(R.string.pet_grid_empty_text);
        }
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