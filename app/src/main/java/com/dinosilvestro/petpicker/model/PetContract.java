package com.dinosilvestro.petpicker.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class PetContract {
    public static final String CONTENT_AUTHORITY = "com.dinosilvestro.petpicker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS = "pets";
    public static final String PATH_PET = "pet";

    public static final class PetEntry implements BaseColumns {

        public static final String TABLE_NAME = "PETS";
        public static final String COLUMN_PET_ID = "PET_ID";
        public static final String COLUMN_PET_NAME = "PET_NAME";
        public static final String COLUMN_PET_STATUS = "PET_STATUS";
        public static final String COLUMN_PET_SEX = "PET_SEX";
        public static final String COLUMN_PET_SIZE = "PET_SIZE";
        public static final String COLUMN_PET_AGE = "PET_AGE";
        public static final String COLUMN_PET_ANIMAL = "PET_ANIMAL";
        public static final String COLUMN_PET_DESCRIPTION = "PET_DESCRIPTION";
        public static final String COLUMN_PET_MEDIA = "PET_MEDIA";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PETS).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/"
                + CONTENT_AUTHORITY
                + "/"
                + PATH_PETS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/"
                + CONTENT_AUTHORITY
                + "/"
                + PATH_PET;

        public static Uri buildPetPicksUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Check this
        public static String getPetFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
