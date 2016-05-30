package com.example.igiagante.thegarden.repository.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDbHelper;

import java.util.HashSet;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class TestDb extends AndroidTestCase {

    void deleteTheDatabase() {
        mContext.deleteDatabase(FlavorDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(FlavorContract.FlavorEntry.TABLE_NAME);

        mContext.deleteDatabase(FlavorDbHelper.DATABASE_NAME);

        FlavorDbHelper flavorDbHelper = new FlavorDbHelper(mContext);
        SQLiteDatabase db = flavorDbHelper.getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + FlavorContract.FlavorEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> flavorColumnHashSet = new HashSet<String>();
        flavorColumnHashSet.add(FlavorContract.FlavorEntry._ID);
        flavorColumnHashSet.add(FlavorContract.FlavorEntry.COLUMN_NAME);
        flavorColumnHashSet.add(FlavorContract.FlavorEntry.COLUMN_IMAGE_URL);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            flavorColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                flavorColumnHashSet.isEmpty());
        db.close();
    }
}