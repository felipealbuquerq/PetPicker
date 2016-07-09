package com.dinosilvestro.petpicker.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class PetProvider extends ContentProvider {

    public static final int PET = 200;
    public static final int PET_WITH_ID = 201;
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private static final String mPetId =
            PetContract.PetEntry.TABLE_NAME + "."
                    + PetContract.PetEntry.COLUMN_PET_ID
                    + " = ? ";
    private PetDbHelper mPetDbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PetContract.CONTENT_AUTHORITY;

        //CHECK this
        uriMatcher.addURI(authority, PetContract.PATH_PETS, PET);
        uriMatcher.addURI(authority, PetContract.PATH_PETS + "/#", PET_WITH_ID);

        return uriMatcher;
    }

    private Cursor getPetByPetId(Uri uri, String[] projection, String sortOrder) {
        String petId = PetContract.PetEntry.getPetFromUri(uri);
        String[] selectionArgs = new String[]{petId};
        String selection = mPetId;

        return mPetDbHelper.getReadableDatabase().query(PetContract.PetEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mPetDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case PET:
                rowsDeleted = db.delete(
                        PetContract.PetEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PET_WITH_ID:
                rowsDeleted = db.delete(
                        PetContract.PetEntry.TABLE_NAME,
                        PetContract.PetEntry.COLUMN_PET_ID + "=?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = mUriMatcher.match(uri);

        switch (match) {
            case PET_WITH_ID:
                return PetContract.PetEntry.CONTENT_ITEM_TYPE;
            case PET:
                return PetContract.PetEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mPetDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PET: {
                long _id = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);
                returnUri = PetContract.PetEntry.buildPetPicksUri(_id);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mPetDbHelper = new PetDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        Cursor retCursor;
        switch (mUriMatcher.match(uri)) {

            case PET_WITH_ID: {
                retCursor = getPetByPetId(uri, projection, sortOrder);
                break;
            }

            case PET: {
                retCursor = mPetDbHelper.getReadableDatabase().query(
                        PetContract.PetEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(@NonNull Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {

        final SQLiteDatabase db = mPetDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case PET:
                rowsUpdated = db.update(PetContract.PetEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}