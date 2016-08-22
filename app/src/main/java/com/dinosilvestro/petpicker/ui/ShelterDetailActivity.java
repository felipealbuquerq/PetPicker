package com.dinosilvestro.petpicker.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.fetch.FetchData;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.parcels.PetParcel;

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
    @BindView(R.id.emailButton)
    ImageButton mEmailImageButton;
    @BindView(R.id.phoneButton)
    ImageButton mPhoneImageButton;
    @BindView(R.id.navigateButton)
    ImageButton mNavigationImageButton;

    private String mNewShelterName;
    private String mPhone;
    private String mAddress;
    private String mState;
    private String mCity;
    private String mEmail;

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

            mPhone = intent.getStringExtra(Keys.SHELTER_PHONE);
            if (!mPhone.contains("{}")) { // Check to make sure this field is not empty
                mPhoneTextView.setText(String.format("PHONE: %s", mPhone));
            }

            mEmail = intent.getStringExtra(Keys.SHELTER_EMAIL);
            if (!mEmail.contains("{}")) { // Check to make sure this field is not empty
                mEmailTextView.setText(String.format("EMAIL: %s", mEmail));
            }

            mAddress = intent.getStringExtra(Keys.SHELTER_ADDRESS);
            if (!mAddress.contains("{}")) { // Check to make sure this field is not empty
                mAddressTextView.setText(String.format("ADDRESS: %s", mAddress));
            }

            mCity = intent.getStringExtra(Keys.SHELTER_CITY);
            if (!mCity.contains("{}")) { // Check to make sure this field is not empty
                mCityTextView.setText(String.format("CITY: %s", mCity));
            }

            mState = intent.getStringExtra(Keys.SHELTER_STATE);
            if (!mState.contains("{}")) { // Check to make sure this field is not empty
                mStateTextView.setText(String.format("STATE: %s", mState));
            }

            String zip = intent.getStringExtra(Keys.SHELTER_ZIP);
            if (!zip.contains("{}")) { // Check to make sure this field is not empty
                mZipTextView.setText(String.format("ZIP: %s", zip));
            }
        }

        FetchData.getPetData(mShelterId);

        mEmailImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail(mEmail);
            }
        });

        mPhoneImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber(mPhone);
            }
        });

        mNavigationImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = mAddress + mCity + mState;
                String removedSpacesLocation = location.replace(" ", "+");
                Uri geolocation = Uri.parse("geo:0,0?q=" + removedSpacesLocation);
                showMap(geolocation);
            }
        });

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

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String address) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
