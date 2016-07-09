package com.dinosilvestro.petpicker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pet.db";
    private static final int DATABASE_VERSION = 1;

    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Check this
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " +
                PetContract.PetEntry.TABLE_NAME + " (" +
                PetContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PetContract.PetEntry.COLUMN_PET_ID + " INTEGER NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_STATUS + " TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_SEX + " TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_SIZE + " TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_AGE + " TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_ANIMAL + " TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_DESCRIPTION + " TEXT NOT NULL, " +
                PetContract.PetEntry.COLUMN_PET_MEDIA + " TEXT NOT NULL); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "
                + PetContract.PetEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}