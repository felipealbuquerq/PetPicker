package com.dinosilvestro.petpicker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchData {

    private static final String TAG = FetchData.class.getSimpleName();

    public static void getShelterData(String zip) {
        String completeUrl =
                "http://api.petfinder.com/shelter.find?key="
                        + Keys.apiKey
                        + "&location="
                        + zip
                        + "&format=json";

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
                        fetchShelters(jsonData);
                    } else {
                        //alertUserAboutError();
                    }
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });
    }

    public static ShelterParcel[] getShelters(String jsonData) throws JSONException {
        JSONObject shelterData = new JSONObject(jsonData);
        JSONObject petFinder = shelterData.getJSONObject("petfinder");
        JSONObject shelters = petFinder.getJSONObject("shelters");
        JSONArray shelter = shelters.getJSONArray("shelter");

        ShelterParcel[] shelterParcel = new ShelterParcel[shelter.length()];

        for (int i = 0; i < shelter.length(); i++) {
            JSONObject jsonShelter = shelter.getJSONObject(i);
            ShelterParcel newShelterParcel = new ShelterParcel();
            newShelterParcel.setId(jsonShelter.getString("id"));
            newShelterParcel.setName(jsonShelter.getString("name"));
            newShelterParcel.setPhone(jsonShelter.getString("phone"));
            newShelterParcel.setEmail(jsonShelter.getString("email"));
            newShelterParcel.setAddress(jsonShelter.getString("address1"));
            newShelterParcel.setCity(jsonShelter.getString("city"));
            newShelterParcel.setState(jsonShelter.getString("state"));
            newShelterParcel.setZip(jsonShelter.getString("zip"));
            shelterParcel[i] = newShelterParcel;
        }

        return shelterParcel;
    }


    public static ShelterParcel fetchShelters(String jsonData) throws JSONException {
        ShelterParcel shelterParcel = new ShelterParcel();
        shelterParcel.setShelters(getShelters(jsonData));
        return shelterParcel;
    }
}
