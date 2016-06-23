package com.dinosilvestro.petpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShelterDetailActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        if (intent != null) {

            String name = intent.getStringExtra(Keys.NAME);
            if (!name.contains("{}")) { // Check to make sure this field is not empty
                setTitle(name);
            }

            String phone = intent.getStringExtra(Keys.PHONE);
            if (!phone.contains("{}")) { // Check to make sure this field is not empty
                mPhoneTextView.setText(String.format("Phone: %s", phone));
            }

            String email = intent.getStringExtra(Keys.EMAIL);
            if (!email.contains("{}")) { // Check to make sure this field is not empty
                mEmailTextView.setText(String.format("Email: %s", email));
            }

            String address = intent.getStringExtra(Keys.ADDRESS);
            if (!address.contains("{}")) { // Check to make sure this field is not empty
                mAddressTextView.setText(String.format("Address: %s", address));
            }

            String city = intent.getStringExtra(Keys.CITY);
            if (!city.contains("{}")) { // Check to make sure this field is not empty
                mCityTextView.setText(String.format("City: %s", city));
            }

            String state = intent.getStringExtra(Keys.STATE);
            if (!state.contains("{}")) { // Check to make sure this field is not empty
                mStateTextView.setText(String.format("State: %s", state));
            }

            String zip = intent.getStringExtra(Keys.ZIP);
            if (!zip.contains("{}")) { // Check to make sure this field is not empty
                mZipTextView.setText(String.format("Zip: %s", zip));
            }
        }
    }
}
