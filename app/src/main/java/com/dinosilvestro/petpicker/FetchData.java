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

    public static boolean mShelterFlag = false;
    public static boolean mPetFlag = false;

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
                    fetchShelters(jsonData);
                    mShelterFlag = true;
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                    mShelterFlag = false;
                }
            }
        });
    }

    public static void getPetData(String shelterId) {
        String getPetsUrl =
                "http://api.petfinder.com/shelter.getPets?key="
                        + Keys.apiKey
                        + "&id="
                        + shelterId
                        + "&format=json";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getPetsUrl)
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
                    fetchPets(jsonData);
                    mPetFlag = true;
                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                    mPetFlag = false;
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

    public static PetParcel[] getPets(String jsonData) throws JSONException {
        JSONObject petData = new JSONObject(jsonData);
        JSONObject petFinder = petData.getJSONObject("petfinder");
        JSONObject pets = petFinder.getJSONObject("pets");
        JSONArray pet = pets.getJSONArray("pet");

        PetParcel[] petParcel = new PetParcel[pet.length()];

        for (int i = 0; i < pet.length(); i++) {
            JSONObject jsonPet = pet.getJSONObject(i);
            PetParcel newPetParcel = new PetParcel();
            newPetParcel.setId(jsonPet.getString("id"));
            newPetParcel.setName(jsonPet.getString("name"));
            newPetParcel.setStatus(jsonPet.getString("status"));
            newPetParcel.setSex(jsonPet.getString("sex"));
            newPetParcel.setSize(jsonPet.getString("size"));
            newPetParcel.setAge(jsonPet.getString("age"));
            newPetParcel.setAnimal(jsonPet.getString("animal"));
            newPetParcel.setDescription(jsonPet.getString("description"));

            JSONObject media = jsonPet.getJSONObject("media");
            JSONObject photos = media.getJSONObject("photos");
            JSONArray photo = photos.getJSONArray("photo");

            for (int index = 0; index < photo.length(); index++) {
                JSONObject jsonMedia = photo.getJSONObject(2); //large full body pic
                newPetParcel.setMedia(jsonMedia.getString("$t"));
            }

            petParcel[i] = newPetParcel;
        }
        return petParcel;
    }


    public static ShelterParcel fetchShelters(String jsonData) throws JSONException {
        ShelterParcel shelterParcel = new ShelterParcel();
        shelterParcel.setShelters(getShelters(jsonData));
        return shelterParcel;
    }

    public static PetParcel fetchPets(String jsonData) throws JSONException {
        PetParcel petParcel = new PetParcel();
        petParcel.setPets(getPets(jsonData));
        return petParcel;
    }
}
