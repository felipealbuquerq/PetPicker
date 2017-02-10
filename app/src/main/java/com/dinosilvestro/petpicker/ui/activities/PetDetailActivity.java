package com.dinosilvestro.petpicker.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.ui.fragments.PetDetailFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PetDetailActivity extends AppCompatActivity {

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

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                //Toast.makeText(mContext, R.string.unable_to_download_meme_toast_text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}
