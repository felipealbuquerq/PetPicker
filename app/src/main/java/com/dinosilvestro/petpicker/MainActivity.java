package com.dinosilvestro.petpicker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    final String mTempZip = "32816";
    private Shelters[] mShelters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getShelterData(mTempZip);
    }

    private void getShelterData(String zip) {
        String completeUrl =
                "http://api.petfinder.com/shelter.find?key="
                        + ApiKey.apiKey
                        + "&location="
                        + zip
                        + "&format=json";


        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(completeUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mShelters = getShelters(jsonData);
                        } else {
                            //alertUserAboutError();
                        }
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Network is unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    private Shelters[] getShelters(String jsonData) throws JSONException {
        JSONObject shelterData = new JSONObject(jsonData);
        JSONObject petFinder = shelterData.getJSONObject("petfinder");
        JSONObject shelters = petFinder.getJSONObject("shelters");
        JSONArray shelter = shelters.getJSONArray("shelter");

        Shelters[] shelterParcel = new Shelters[shelter.length()];

        for (int i = 0; i < shelter.length(); i++) {
            JSONObject jsonShelter = shelter.getJSONObject(i);
            Shelters shelters1 = new Shelters();
            shelters1.setId(jsonShelter.getString("id"));
            shelters1.setName(jsonShelter.getString("name"));
            Log.i(TAG, "From JSON: " + shelters1.getName());

            shelterParcel[i] = shelters1;

        }

        return shelterParcel;
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
