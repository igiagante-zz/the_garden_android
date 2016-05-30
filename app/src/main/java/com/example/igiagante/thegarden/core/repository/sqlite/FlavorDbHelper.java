package com.example.igiagante.thegarden.core.repository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract.FlavorEntry;

/**
 * @author Ignacio Giagante, on 27/5/16.
 */
public class FlavorDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "flavor.db";
    public static final int DATABASE_VERSION = 1;

    public FlavorDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + FlavorEntry.TABLE_NAME + " (" +
                FlavorEntry._ID + " INTEGER PRIMARY KEY, " +
                FlavorEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FlavorEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FlavorEntry.TABLE_NAME);
        onCreate(db);
    }
}
