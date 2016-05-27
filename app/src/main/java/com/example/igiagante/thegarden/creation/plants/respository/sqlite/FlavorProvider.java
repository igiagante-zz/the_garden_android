package com.example.igiagante.thegarden.creation.plants.respository.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @author Ignacio Giagante, on 27/5/16.
 */
public class FlavorProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FlavorDbHelper mOpenHelper;

    public static final int FLAVOR = 1;
    public static final int FLAVOR_ID = 2;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FlavorContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FlavorContract.PATH_FLAVOR, FLAVOR);
        matcher.addURI(authority, FlavorContract.PATH_FLAVOR + "/#", FLAVOR_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new FlavorDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            // "movie"
            case FLAVOR: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FlavorContract.FlavorEntry.TABLE_NAME,
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

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case FLAVOR:
                return FlavorContract.FlavorEntry.CONTENT_TYPE;
            case FLAVOR_ID:
                return FlavorContract.FlavorEntry.CONTENT_TYPE_ITEM;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FLAVOR: {
                long _id = db.insert(FlavorContract.FlavorEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = FlavorContract.FlavorEntry.buildFlavorUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if ( null == selection ) selection = "1";
        switch (match) {
            case FLAVOR:
                rowsDeleted = db.delete(
                        FlavorContract.FlavorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        if ( null == selection ) selection = "1";
        switch (match) {
            case FLAVOR:
                rowsUpdated = db.update(FlavorContract.FlavorEntry.TABLE_NAME, values, selection,
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
