package com.dinosilvestro.petpicker.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.ui.fragments.DualPaneShelterFragment;
import com.dinosilvestro.petpicker.ui.fragments.ShelterListFragment;

public class ShelterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        // Checking to see if user is using a tablet
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);

        if (!isTablet) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ShelterListFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new DualPaneShelterFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.menu_item_share).setVisible(false);
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