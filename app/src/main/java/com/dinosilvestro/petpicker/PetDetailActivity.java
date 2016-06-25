package com.dinosilvestro.petpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {

    @BindView(R.id.petStatusTextView)
    TextView mStatusTextView;
    @BindView(R.id.petSexTextView)
    TextView mSexTextView;
    @BindView(R.id.petSizeTextView)
    TextView mSizeTextView;
    @BindView(R.id.petAgeTextView)
    TextView mAgeTextView;
    @BindView(R.id.petAnimalTextView)
    TextView mAnimalTextView;
    @BindView(R.id.petDescriptionTextView)
    TextView mDescriptionTextView;
    @BindView(R.id.petMediaImageView)
    ImageView mMediaImageView;
    // Id of the pet to get details from
    private String mPetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        if (intent != null) {

            mPetId = intent.getStringExtra(Keys.SHELTER_ID);

            String name = intent.getStringExtra(Keys.PET_NAME);
            if (!name.contains("{}")) { // Check to make sure this field is not empty
                setTitle(name);
            }

            String status = intent.getStringExtra(Keys.PET_STATUS);
            if (!status.contains("{}")) { // Check to make sure this field is not empty
                mStatusTextView.setText(String.format("Status: %s", status));
            }

            String sex = intent.getStringExtra(Keys.PET_SEX);
            if (!sex.contains("{}")) { // Check to make sure this field is not empty
                mSexTextView.setText(String.format("Sex: %s", sex));
            }

            String size = intent.getStringExtra(Keys.PET_SIZE);
            if (!size.contains("{}")) { // Check to make sure this field is not empty
                mSizeTextView.setText(String.format("Size: %s", size));
            }

            String age = intent.getStringExtra(Keys.PET_AGE);
            if (!age.contains("{}")) { // Check to make sure this field is not empty
                mAgeTextView.setText(String.format("Age: %s", age));
            }

            String animal = intent.getStringExtra(Keys.PET_ANIMAL);
            if (!animal.contains("{}")) { // Check to make sure this field is not empty
                mAnimalTextView.setText(String.format("Animal Type: %s", animal));
            }

            String description = intent.getStringExtra(Keys.PET_DESCRIPTION);
            if (!description.contains("{}")) { // Check to make sure this field is not empty
                mDescriptionTextView.setText(String.format("Description: %s", description));
            }

            Picasso.with(this)
                    .load(intent.getStringExtra(Keys.PET_MEDIA))
                    .into(mMediaImageView);
        }
    }
}
