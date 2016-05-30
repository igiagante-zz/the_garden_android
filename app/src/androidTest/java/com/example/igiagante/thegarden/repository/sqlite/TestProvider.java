package com.example.igiagante.thegarden.repository.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDbHelper;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class TestProvider extends AndroidTestCase {

    public void deleteAllRecords() {
        TestUtilities.cleanDataBase(mContext);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        deleteAllRecords();
    }

    public void testGetType() {

        final long TEST_MOVIE_ID = 12345;

        // content://com.igiagante.provider.flavors
        String type = mContext.getContentResolver().getType(FlavorContract.FlavorEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.igiagante.provider.flavors
        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
                FlavorContract.FlavorEntry.CONTENT_TYPE, type);

        // content://com.igiagante.provider.flavors
        type = mContext.getContentResolver().getType(FlavorContract.FlavorEntry.buildFlavorUri(TEST_MOVIE_ID));
        // vnd.android.cursor.dir/com.igiagante.provider.flavors
        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
                FlavorContract.FlavorEntry.CONTENT_TYPE_ITEM, type);

    }

    public void testFlavorQuery() {

        // insert our test records into the database
        FlavorDbHelper dbHelper = new FlavorDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        TestUtilities.insertFlavorValues(mContext);

        // retrieve values
        ContentValues flavorValues = TestUtilities.createFlavorValues();

        db.close();

        // Test the basic content provider query
        Cursor flavorCursor = mContext.getContentResolver().query(
                FlavorContract.FlavorEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        TestUtilities.validateCursor("testQuery", flavorCursor, flavorValues);
    }
}