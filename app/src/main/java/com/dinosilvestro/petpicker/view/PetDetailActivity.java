package com.dinosilvestro.petpicker.view;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.model.Keys;
import com.dinosilvestro.petpicker.model.PetContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {

    @BindView(R.id.petStatusTextView)
    TextView mStatusTextView;
    @BindView(R.id.petSexTextView)
    TextView mSexTextView;
    @BindView(R.id.petSizeTextView)
    TextView mSizeTextView;
    @BindView(R.id.petAgeTextView)
    TextView mAgeTextView;
    @BindView(R.id.petAnimalTextView)
    TextView mAnimalTextView;
    @BindView(R.id.petDescriptionTextView)
    TextView mDescriptionTextView;
    @BindView(R.id.petMediaImageView)
    ImageView mMediaImageView;

    @BindView(R.id.pickActionButton)
    FloatingActionButton mPickActionButton;

    // Id of the pet to get details from
    private String mPetId;
    private String mPetName;
    private String mPetStatus;
    private String mPetSex;
    private String mPetSize;
    private String mPetAge;
    private String mPetAnimal;
    private String mPetDescription;
    private String mPetMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        if (intent != null) {

            mPetId = intent.getStringExtra(Keys.PET_ID);

            mPetName = intent.getStringExtra(Keys.PET_NAME);
            if (!mPetName.contains("{}")) { // Check to make sure this field is not empty
                setTitle(mPetName);
            }

            mPetStatus = intent.getStringExtra(Keys.PET_STATUS);
            if (!mPetStatus.contains("{}")) { // Check to make sure this field is not empty
                mStatusTextView.setText(String.format("STATUS: %s", mPetStatus));
            }

            mPetSex = intent.getStringExtra(Keys.PET_SEX);
            if (!mPetSex.contains("{}")) { // Check to make sure this field is not empty
                mSexTextView.setText(String.format("SEX: %s", mPetSex));
            }

            mPetSize = intent.getStringExtra(Keys.PET_SIZE);
            if (!mPetSize.contains("{}")) { // Check to make sure this field is not empty
                mSizeTextView.setText(String.format("SIZE: %s", mPetSize));
            }

            mPetAge = intent.getStringExtra(Keys.PET_AGE);
            if (!mPetAge.contains("{}")) { // Check to make sure this field is not empty
                mAgeTextView.setText(String.format("AGE: %s", mPetAge));
            }

            mPetAnimal = intent.getStringExtra(Keys.PET_ANIMAL);
            if (!mPetAnimal.contains("{}")) { // Check to make sure this field is not empty
                mAnimalTextView.setText(String.format("ANIMAL: %s", mPetAnimal));
            }

            mPetDescription = intent.getStringExtra(Keys.PET_DESCRIPTION);
            if (!mPetDescription.contains("{}")) { // Check to make sure this field is not empty
                mDescriptionTextView.setText(String.format("DESCRIPTION: %s", mPetDescription));
            }

            mPetMedia = intent.getStringExtra(Keys.PET_MEDIA);

            Picasso.with(this)
                    .load(intent.getStringExtra(Keys.PET_MEDIA))
                    .into(mMediaImageView);
        }

        mPickActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = PetContract.PetEntry.CONTENT_URI.buildUpon()
                        .appendPath(mPetId).build();
                try {
                    final Cursor petPicksCursor = getApplicationContext()
                            .getContentResolver()
                            .query(uri, null, null, null, null);

                    final Cursor databaseCursor = getApplicationContext()
                            .getContentResolver()
                            .query(PetContract.PetEntry.CONTENT_URI, null, null, null, null);

                    assert databaseCursor != null;

                    if ((petPicksCursor != null) && (!(petPicksCursor.moveToNext()))) {
                        ContentValues contentValues = saveValues();
                        Uri insertedUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, contentValues);
                        long id = ContentUris.parseId(insertedUri);
                        if (id != -1) {
                            Snackbar.make(view, mPetName + " has been added to your pet picks", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        removePetPick();
                        Snackbar.make(view, mPetName + " has been removed from your pet picks", Snackbar.LENGTH_SHORT).show();
                    }
                    if (petPicksCursor != null) {
                        petPicksCursor.close();
                        databaseCursor.close();
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }

    public void removePetPick() {
        Uri uri = PetContract.PetEntry.CONTENT_URI
                .buildUpon()
                .appendPath(mPetId).build();
        getContentResolver().delete(uri, mPetId, null);
    }

    public ContentValues saveValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PetContract.PetEntry.COLUMN_PET_ID, mPetId);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_NAME, mPetName);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_STATUS, mPetStatus);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_SEX, mPetSex);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_SIZE, mPetSize);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_AGE, mPetAge);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_ANIMAL, mPetAnimal);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_DESCRIPTION, mPetDescription);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_MEDIA, mPetMedia);

        return contentValues;
    }
}
