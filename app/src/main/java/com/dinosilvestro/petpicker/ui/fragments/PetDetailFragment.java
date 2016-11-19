package com.dinosilvestro.petpicker.ui.fragments;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.data.PetContract;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetDetailFragment extends Fragment {

    private static final String PREFS_FILE = "com.dinosilvestro.petpicker.preferences";
    private static final String KEY_PET_PICK = "key_pet_pick";
    private static String mPetName;
    private static String mPetMedia;
    TextView mStatusTextView;
    TextView mSexTextView;
    TextView mSizeTextView;
    TextView mAgeTextView;
    TextView mAnimalTextView;
    TextView mDescriptionTextView;
    ImageView mMediaImageView;
    ProgressBar mProgressBar;
    FloatingActionButton mPickActionButton;
    private String mPetId;
    private String mPetStatus;
    private String mPetSex;
    private String mPetSize;
    private String mPetAge;
    private String mPetAnimal;
    private String mPetDescription;
    private FirebaseAnalytics mFirebaseAnalytics;
    //Trying a different method of saving for pet picks
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public PetDetailFragment() {
        // Required empty public constructor
    }

    // Getters to pass into parent activity's menu share option
    public static String getPetName() {
        return mPetName;
    }

    public static String getPetMedia() {
        return mPetMedia;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_pet_detail, container, false);

        // Initialize views
        mStatusTextView = (TextView) rootView.findViewById(R.id.petStatusTextView);
        mSexTextView = (TextView) rootView.findViewById(R.id.petSexTextView);
        mSizeTextView = (TextView) rootView.findViewById(R.id.petSizeTextView);
        mAgeTextView = (TextView) rootView.findViewById(R.id.petAgeTextView);
        mAnimalTextView = (TextView) rootView.findViewById(R.id.petAnimalTextView);
        mDescriptionTextView = (TextView) rootView.findViewById(R.id.petDescriptionTextView);
        mMediaImageView = (ImageView) rootView.findViewById(R.id.petMediaImageView);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mPickActionButton = (FloatingActionButton) rootView.findViewById(R.id.pickActionButton);

        mSharedPreferences = getActivity().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        // Currently used for phone mode
        Intent intent = getActivity().getIntent();

        // Arguments passed in for dual-pane tablet mode
        Bundle bundle = this.getArguments();

        if (intent.getStringExtra(Keys.PET_ID) != null) {

            mPetId = intent.getStringExtra(Keys.PET_ID);

            mPetName = intent.getStringExtra(Keys.PET_NAME);
            if (!mPetName.contains("{}")) { // Check to make sure this field is not empty
                getActivity().setTitle(mPetName);
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

            Picasso.with(getActivity())
                    .load(intent.getStringExtra(Keys.PET_MEDIA))
                    .into(mMediaImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            // In the rare case that a pet does not have a pic, display app icon
                            mMediaImageView.setImageResource(R.mipmap.ic_launcher);
                        }
                    });

            // Determine whether the user has picked this pet before and change the favorite fab
            // to reflect that
            isPickedPet();

        } else if (bundle.getString(Keys.PET_ID) != null) {

            mProgressBar.setVisibility(View.VISIBLE);
            mPickActionButton.setVisibility(View.VISIBLE);

            mPetId = bundle.getString(Keys.PET_ID);

            mPetName = bundle.getString(Keys.PET_NAME);
            if (!mPetName.contains("{}")) { // Check to make sure this field is not empty
                getActivity().setTitle(mPetName);
            }

            mPetStatus = bundle.getString(Keys.PET_STATUS);
            if (!mPetStatus.contains("{}")) { // Check to make sure this field is not empty
                mStatusTextView.setText(String.format("STATUS: %s", mPetStatus));
            }

            mPetSex = bundle.getString(Keys.PET_SEX);
            if (!mPetSex.contains("{}")) { // Check to make sure this field is not empty
                mSexTextView.setText(String.format("SEX: %s", mPetSex));
            }

            mPetSize = bundle.getString(Keys.PET_SIZE);
            if (!mPetSize.contains("{}")) { // Check to make sure this field is not empty
                mSizeTextView.setText(String.format("SIZE: %s", mPetSize));
            }

            mPetAge = bundle.getString(Keys.PET_AGE);
            if (!mPetAge.contains("{}")) { // Check to make sure this field is not empty
                mAgeTextView.setText(String.format("AGE: %s", mPetAge));
            }

            mPetAnimal = bundle.getString(Keys.PET_ANIMAL);
            if (!mPetAnimal.contains("{}")) { // Check to make sure this field is not empty
                mAnimalTextView.setText(String.format("ANIMAL: %s", mPetAnimal));
            }

            mPetDescription = bundle.getString(Keys.PET_DESCRIPTION);
            if (!mPetDescription.contains("{}")) { // Check to make sure this field is not empty
                mDescriptionTextView.setText(String.format("DESCRIPTION: %s", mPetDescription));
            }

            mPetMedia = bundle.getString(Keys.PET_MEDIA);

            Picasso.with(getActivity())
                    .load(bundle.getString(Keys.PET_MEDIA))
                    .into(mMediaImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            // In the rare case that a pet does not have a pic, display app icon
                            mMediaImageView.setImageResource(R.mipmap.ic_launcher);
                        }
                    });

            // Determine whether the user has picked this pet before and change the favorite fab
            // to reflect that
            isPickedPet();

        } else {
            mSizeTextView.setText("Select a pet on the left to see more details");
            mProgressBar.setVisibility(View.INVISIBLE);
            mPickActionButton.setVisibility(View.INVISIBLE);
        }

        mPickActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
                Uri uri = PetContract.PetEntry.CONTENT_URI.buildUpon()
                        .appendPath(mPetId).build();
                try {
                    final Cursor petPicksCursor = getActivity().getApplicationContext()
                            .getContentResolver()
                            .query(uri, null, null, null, null);

                    final Cursor databaseCursor = getActivity().getApplicationContext()
                            .getContentResolver()
                            .query(PetContract.PetEntry.CONTENT_URI, null, null, null, null);

                    assert databaseCursor != null;

                    if ((petPicksCursor != null) && (!(petPicksCursor.moveToNext()))) {
                        ContentValues contentValues = saveValues();
                        Uri insertedUri = getActivity().getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, contentValues);
                        long id = ContentUris.parseId(insertedUri);
                        if (id != -1) {
                            Snackbar.make(view, mPetName + " has been added to your pet picks", Snackbar.LENGTH_SHORT).show();
                            //Add pet to shared preferences file and set fab drawable
                            mEditor.putString(KEY_PET_PICK + mPetId, mPetId);
                            mEditor.apply();
                            mPickActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                        }
                    } else {
                        removePetPick();
                        Snackbar.make(view, mPetName + " has been removed from your pet picks", Snackbar.LENGTH_SHORT).show();
                        //Remove pet from shared preferences file and set fab drawable
                        mEditor.remove(mPetId);
                        mEditor.apply();
                        mPickActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    }
                    if (petPicksCursor != null) {
                        petPicksCursor.close();
                        databaseCursor.close();
                    }
                } catch (Exception ignored) {
                }
            }
        });

        return rootView;
    }


    public void removePetPick() {
        Uri uri = PetContract.PetEntry.CONTENT_URI
                .buildUpon()
                .appendPath(mPetId).build();
        getActivity().getContentResolver().delete(uri, mPetId, null);
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

        // Add relevant values to Firebase Analytics
        mFirebaseAnalytics.setUserProperty("pet_sex", mPetSex);
        mFirebaseAnalytics.setUserProperty("pet_size", mPetSize);
        mFirebaseAnalytics.setUserProperty("pet_age", mPetAge);
        mFirebaseAnalytics.setUserProperty("pet_animal", mPetAnimal);

        return contentValues;
    }

    public void animate() {
        mPickActionButton.setScaleX(0);
        mPickActionButton.setScaleY(0);
        mPickActionButton.animate().scaleX(1).scaleY(1).start();
    }

    // Checking shared preferences file to see if this pet has been picked
    public void isPickedPet() {
        if (mSharedPreferences.contains(KEY_PET_PICK + mPetId)) {
            mPickActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            mPickActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
    }
}
