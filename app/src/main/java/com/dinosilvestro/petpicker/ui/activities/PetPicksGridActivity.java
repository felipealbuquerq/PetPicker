package com.dinosilvestro.petpicker.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.ui.fragments.PetPicksGridFragment;

public class PetPicksGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new PetPicksGridFragment())
                .commit();

        // Adds up navigation to actionbar in case user gets to this activity from the shortcut
        // or widget
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
