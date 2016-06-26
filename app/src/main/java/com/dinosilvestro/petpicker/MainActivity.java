package com.dinosilvestro.petpicker;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.getSheltersButtonwithGps)
    Button mButton;

    @BindView(R.id.zipEditText)
    EditText mZipEditText;

    @BindView(R.id.searchWithZipImageButton)
    ImageButton mImageButton;

    private String mDefaultZipCode = "32816";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (isNetworkAvailable()) {
            FetchData.getShelterData(mDefaultZipCode);

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ShelterListActivity.class);
                    intent.putExtra(Keys.GET_SHELTERS, ShelterParcel.getShelters());
                    startActivity(intent);
                }
            });

            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FetchData.getShelterData(mZipEditText.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), ShelterListActivity.class);
                    intent.putExtra(Keys.GET_SHELTERS, ShelterParcel.getShelters());
                    startActivity(intent);
                }
            });

        } else {
            Toast.makeText(MainActivity.this, R.string.network_unavailable_toast,
                    Toast.LENGTH_LONG).show();
        }
    }

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
}
