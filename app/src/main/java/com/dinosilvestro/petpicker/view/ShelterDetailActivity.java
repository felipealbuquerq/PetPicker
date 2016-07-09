package com.dinosilvestro.petpicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.controller.PetParcel;
import com.dinosilvestro.petpicker.model.FetchData;
import com.dinosilvestro.petpicker.model.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShelterDetailActivity extends AppCompatActivity {

    // ID of shelter to get pets from
    String mShelterId;

    @BindView(R.id.phoneTextView)
    TextView mPhoneTextView;
    @BindView(R.id.emailTextView)
    TextView mEmailTextView;
    @BindView(R.id.addressTextView)
    TextView mAddressTextView;
    @BindView(R.id.cityTextView)
    TextView mCityTextView;
    @BindView(R.id.stateTextView)
    TextView mStateTextView;
    @BindView(R.id.zipTextView)
    TextView mZipTextView;
    @BindView(R.id.getPetsButton)
    Button mGetPetsButton;

    private String mNewShelterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();

        if (intent != null) {

            mShelterId = intent.getStringExtra(Keys.SHELTER_ID);

            String name = intent.getStringExtra(Keys.SHELTER_NAME);
            if (!name.contains("{}")) { // Check to make sure this field is not empty
                setTitle(name);
            }

            mNewShelterName = name;

            String phone = intent.getStringExtra(Keys.SHELTER_PHONE);
            if (!phone.contains("{}")) { // Check to make sure this field is not empty
                mPhoneTextView.setText(String.format("PHONE: %s", phone));
            }

            String email = intent.getStringExtra(Keys.SHELTER_EMAIL);
            if (!email.contains("{}")) { // Check to make sure this field is not empty
                mEmailTextView.setText(String.format("EMAIL: %s", email));
            }

            String address = intent.getStringExtra(Keys.SHELTER_ADDRESS);
            if (!address.contains("{}")) { // Check to make sure this field is not empty
                mAddressTextView.setText(String.format("ADDRESS: %s", address));
            }

            String city = intent.getStringExtra(Keys.SHELTER_CITY);
            if (!city.contains("{}")) { // Check to make sure this field is not empty
                mCityTextView.setText(String.format("CITY: %s", city));
            }

            String state = intent.getStringExtra(Keys.SHELTER_STATE);
            if (!state.contains("{}")) { // Check to make sure this field is not empty
                mStateTextView.setText(String.format("STATE: %s", state));
            }

            String zip = intent.getStringExtra(Keys.SHELTER_ZIP);
            if (!zip.contains("{}")) { // Check to make sure this field is not empty
                mZipTextView.setText(String.format("ZIP: %s", zip));
            }
        }

        FetchData.getPetData(mShelterId);

        mGetPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PetGridActivity.class);
                intent.putExtra(Keys.GET_PETS, PetParcel.getPets());
                intent.putExtra(Keys.NEW_SHELTER_NAME, mNewShelterName);
                startActivity(intent);
            }
        });
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