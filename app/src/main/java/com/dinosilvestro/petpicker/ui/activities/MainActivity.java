package com.dinosilvestro.petpicker.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.fetch.Constants;
import com.dinosilvestro.petpicker.fetch.FetchAddressIntentService;
import com.dinosilvestro.petpicker.fetch.FetchData;
import com.dinosilvestro.petpicker.fetch.Keys;
import com.dinosilvestro.petpicker.parcels.ShelterParcel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int REQUEST_LOCATION = 101;
    public static String mDefaultZipCode;
    protected Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure action bar is actually there, hide it if it is.
        // If not, don't worry about it.
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);

        Button findSheltersButton = (Button) findViewById((R.id.getSheltersButtonwithGps));

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        findSheltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there isn't any shelter data available...
                if (!FetchData.mShelterFlag) {
                    // Try to get the user's location again
                    fetchLastLocation();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ShelterListActivity.class);
                    intent.putExtra(Keys.GET_SHELTERS, ShelterParcel.getShelters());
                    startActivity(intent);
                }
            }
        });
    }

    // Check to make sure user is connected to the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual
            fetchLastLocation();
        }
    }

    private void fetchLastLocation() {
        if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.i("Test Tag", "Location Permission has not been granted");
            } else {
                // Get the last known location of the user
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                // In cases when the last location is not found - request it
                if (mLastLocation == null) {
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(5000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                    // Start intent service with newly obtained last location
                    startIntentService();
                } else {
                    // Last location is already known. Start the intent service with that
                    startIntentService();
                }
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                fetchLastLocation();
            } else {
                // Permission was denied or request was cancelled
                requestManualLocationDialog();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void requestManualLocationDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Manual Location Entry");
        alertDialog.setMessage("Please enter a valid zip/postal code to continue.");
        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(5);
        input.setFilters(FilterArray);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDefaultZipCode = input.getText().toString();
                FetchData.getShelterData(mDefaultZipCode);
            }
        });
        alertDialog.setNegativeButton("CANCEL", null);
        alertDialog.show();
    }

    protected void startIntentService() {
        // We have a last location, start intent service
        if (mLastLocation != null) {
            Intent intent = new Intent(this, FetchAddressIntentService.class);
            AddressResultReceiver resultReceiver = new AddressResultReceiver(new Handler());
            intent.putExtra(Constants.RECEIVER, resultReceiver);
            intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
            startService(intent);
        } else {
            // Location cannot be determined
            // No need to start service
            // Fall back to manual location entry
            requestManualLocationDialog();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        startIntentService();
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            // If an address has been found, start accessing the Petfinder API
            if (resultCode == Constants.SUCCESS_RESULT) {
                mDefaultZipCode = addressOutput;
                FetchData.getShelterData(mDefaultZipCode);
            }
        }
    }
}
