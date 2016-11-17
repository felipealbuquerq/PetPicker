package com.dinosilvestro.petpicker.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.ui.fragments.PetDetailFragment;

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
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(PetDetailFragment.getPetMedia()));
                sendIntent.setType("image/jpeg");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "I found " + PetDetailFragment.getPetName() + " on the Pet Picker app.");
                Toast.makeText(this, "Works best with the default Messenger app", Toast.LENGTH_SHORT).show();
                startActivity(Intent.createChooser(sendIntent, "Share pet pick to..."));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
