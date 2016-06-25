package com.dinosilvestro.petpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

            String phone = intent.getStringExtra(Keys.SHELTER_PHONE);
            if (!phone.contains("{}")) { // Check to make sure this field is not empty
                mPhoneTextView.setText(String.format("Phone: %s", phone));
            }

            String email = intent.getStringExtra(Keys.SHELTER_EMAIL);
            if (!email.contains("{}")) { // Check to make sure this field is not empty
                mEmailTextView.setText(String.format("Email: %s", email));
            }

            String address = intent.getStringExtra(Keys.SHELTER_ADDRESS);
            if (!address.contains("{}")) { // Check to make sure this field is not empty
                mAddressTextView.setText(String.format("Address: %s", address));
            }

            String city = intent.getStringExtra(Keys.SHELTER_CITY);
            if (!city.contains("{}")) { // Check to make sure this field is not empty
                mCityTextView.setText(String.format("City: %s", city));
            }

            String state = intent.getStringExtra(Keys.SHELTER_STATE);
            if (!state.contains("{}")) { // Check to make sure this field is not empty
                mStateTextView.setText(String.format("State: %s", state));
            }

            String zip = intent.getStringExtra(Keys.SHELTER_ZIP);
            if (!zip.contains("{}")) { // Check to make sure this field is not empty
                mZipTextView.setText(String.format("Zip: %s", zip));
            }
        }

        FetchData.getPetData(mShelterId);

        mGetPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PetGridActivity.class);
                intent.putExtra(Keys.GET_PETS, PetParcel.getPets());
                startActivity(intent);
            }
        });
    }
}
