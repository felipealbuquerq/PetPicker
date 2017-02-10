package com.dinosilvestro.petpicker.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.ui.fragments.PetDetailFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PetDetailActivity extends AppCompatActivity {

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new PetDetailFragment())
                .commit();
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
                handlePetPick();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    // Use the Picasso library to save and share the selected pet
    private void handlePetPick() {
        Picasso.with(this).load(PetDetailFragment.getPetMedia()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                //Check if user has granted the storage permission
                mBitmap = bitmap;
                storagePermissionCheck();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                //Toast.makeText(mContext, R.string.unable_to_download_meme_toast_text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void saveAndSharePetPick(Bitmap bitmap) {
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                bitmap, PetDetailFragment.getPetMedia(),
                "Pet Pick " + PetDetailFragment.getPetName()));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I found " + PetDetailFragment.getPetName() + " on the Pet Picker app.");
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share pet pick to..."));
    }

    private void storagePermissionCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission has not been granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Keys.PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            // Permission has been granted before
            saveAndSharePetPick(mBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Keys.PERMISSION_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission has been granted for the first time
                    saveAndSharePetPick(mBitmap);

                } else {

                    // Permission denied
                    Toast.makeText(this,
                            "Please enable the storage permission to save and share your pet pick.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
