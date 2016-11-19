package com.dinosilvestro.petpicker.ui.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.fetch.FetchData;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.parcels.PetParcel;
import com.dinosilvestro.petpicker.ui.activities.PetGridActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShelterDetailFragment extends Fragment {

    private String mShelterId;
    private String mNewShelterName;
    private String mPhone;
    private String mAddress;
    private String mState;
    private String mCity;
    private String mEmail;

    public ShelterDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_shelter_detail, container, false);

        // Setting up views
        TextView mPhoneTextView = (TextView) rootView.findViewById(R.id.phoneTextView);
        TextView mEmailTextView = (TextView) rootView.findViewById(R.id.emailTextView);
        TextView mAddressTextView = (TextView) rootView.findViewById(R.id.addressTextView);
        TextView mCityTextView = (TextView) rootView.findViewById(R.id.cityTextView);
        TextView mStateTextView = (TextView) rootView.findViewById(R.id.stateTextView);
        TextView mZipTextView = (TextView) rootView.findViewById(R.id.zipTextView);
        Button mGetPetsButton = (Button) rootView.findViewById(R.id.getPetsButton);
        ImageButton mEmailImageButton = (ImageButton) rootView.findViewById(R.id.emailButton);
        ImageButton mPhoneImageButton = (ImageButton) rootView.findViewById(R.id.phoneButton);
        ImageButton mNavigationImageButton = (ImageButton) rootView.findViewById(R.id.navigateButton);

        // Currently used for phone mode
        Intent intent = getActivity().getIntent();

        // Arguments passed in for dual-pane tablet mode
        Bundle bundle = this.getArguments();

        if (intent.getStringExtra(Keys.SHELTER_ID) != null) {

            mShelterId = intent.getStringExtra(Keys.SHELTER_ID);

            String name = intent.getStringExtra(Keys.SHELTER_NAME);
            if (!name.contains("{}")) { // Check to make sure this field is not empty
                getActivity().setTitle(name);
            }

            mNewShelterName = name;

            mPhone = intent.getStringExtra(Keys.SHELTER_PHONE);
            if (!mPhone.contains("{}")) { // Check to make sure this field is not empty
                mPhoneTextView.setText(String.format("PHONE: %s", mPhone));
                // Click listener only works if a phone number is available
                mPhoneImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialPhoneNumber(mPhone);
                    }
                });
            }

            mEmail = intent.getStringExtra(Keys.SHELTER_EMAIL);
            if (!mEmail.contains("{}")) { // Check to make sure this field is not empty
                mEmailTextView.setText(String.format("EMAIL: %s", mEmail));
                // Click listener only works if an email address is available
                mEmailImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        composeEmail(mEmail);
                    }
                });
            }

            mAddress = intent.getStringExtra(Keys.SHELTER_ADDRESS);
            if (!mAddress.contains("{}")) { // Check to make sure this field is not empty
                mAddressTextView.setText(String.format("ADDRESS: %s", mAddress));
                // Click listener only works if an address is available
                mNavigationImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String location = mAddress + " " + mCity + ", " + mState;
                        Uri geolocation = Uri.parse("geo:0,0?q=" + location);
                        showMap(geolocation);
                    }
                });
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

        } else if (bundle.getString(Keys.SHELTER_ID) != null) {

            mShelterId = bundle.getString(Keys.SHELTER_ID);

            mPhone = bundle.getString(Keys.SHELTER_PHONE);
            if (!mPhone.contains("{}")) { // Check to make sure this field is not empty
                mPhoneTextView.setText(String.format("PHONE: %s", mPhone));
                // Click listener only works if a phone number is available
                mPhoneImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialPhoneNumber(mPhone);
                    }
                });
            }

            mEmail = bundle.getString(Keys.SHELTER_EMAIL);
            if (!mEmail.contains("{}")) { // Check to make sure this field is not empty
                mEmailTextView.setText(String.format("EMAIL: %s", mEmail));
                // Click listener only works if an email address is available
                mEmailImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        composeEmail(mEmail);
                    }
                });
            }

            mAddress = bundle.getString(Keys.SHELTER_ADDRESS);
            if (!mAddress.contains("{}")) { // Check to make sure this field is not empty
                mAddressTextView.setText(String.format("ADDRESS: %s", mAddress));
                // Click listener only works if an address is available
                mNavigationImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String location = mAddress + " " + mCity + ", " + mState;
                        Uri geolocation = Uri.parse("geo:0,0?q=" + location);
                        showMap(geolocation);
                    }
                });
            }

            mCity = bundle.getString(Keys.SHELTER_CITY);
            if (!mCity.contains("{}")) { // Check to make sure this field is not empty
                mCityTextView.setText(String.format("CITY: %s", mCity));
            }

            mState = bundle.getString(Keys.SHELTER_STATE);
            if (!mState.contains("{}")) { // Check to make sure this field is not empty
                mStateTextView.setText(String.format("STATE: %s", mState));
            }

            String zip = bundle.getString(Keys.SHELTER_ZIP);
            if (!zip.contains("{}")) { // Check to make sure this field is not empty
                mZipTextView.setText(String.format("ZIP: %s", zip));
            }
        } else {
            mAddressTextView.setText("Select a shelter on the left to see more details");
        }

        FetchData.getPetData(mShelterId);

        mGetPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PetGridActivity.class);
                intent.putExtra(Keys.GET_PETS, PetParcel.getPets());
                intent.putExtra(Keys.NEW_SHELTER_NAME, mNewShelterName);
                startActivity(intent);
            }
        });

        return rootView;
    }


    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String address) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
