package com.dinosilvestro.petpicker.ui.activities;

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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.data.PetContract;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "com.dinosilvestro.petpicker.preferences";
    private static final String KEY_PET_PICK = "key_pet_pick";
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
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
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

    private FirebaseAnalytics mFirebaseAnalytics;

    //Trying a different method of saving for pet picks
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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

            isPickedPet();
        }

        mPickActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
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

        // Add relevant values to Firebase Analytics
        mFirebaseAnalytics.setUserProperty("pet_sex", mPetSex);
        mFirebaseAnalytics.setUserProperty("pet_size", mPetSize);
        mFirebaseAnalytics.setUserProperty("pet_age", mPetAge);
        mFirebaseAnalytics.setUserProperty("pet_animal", mPetAnimal);

        return contentValues;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_pet_picks:
                Intent intent = new Intent(this, PetPicksGridActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(mPetMedia));
                sendIntent.setType("image/jpeg");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "I found " + mPetName + " on the Pet Picker app.");
                Toast.makeText(this, "Works best with the default Messenger app", Toast.LENGTH_SHORT).show();
                startActivity(Intent.createChooser(sendIntent, "Share pet pick to..."));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
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
