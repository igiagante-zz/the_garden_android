package com.example.igiagante.thegarden.repository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract.FlavorEntry;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDbHelper;

import java.util.Map;
import java.util.Set;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class TestUtilities extends AndroidTestCase {

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createFlavorValues() {

        ContentValues flavorValues = new ContentValues();

        flavorValues.put(FlavorEntry.COLUMN_NAME, "Lemon");
        flavorValues.put(FlavorEntry.COLUMN_IMAGE_URL, "url");

        return flavorValues;
    }

    static long insertFlavorValues(Context mContext) {

        FlavorDbHelper dbHelper = new FlavorDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createFlavorValues();

        long flavorRowId = db.insert(FlavorEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert Flavor Values", flavorRowId != -1);

        return flavorRowId;
    }

    static Flavor buildFlavor(String id){

        Flavor flavor = new Flavor();
        flavor.setId(id);
        flavor.setName("Lemon");
        flavor.setImageUrl("url");

        return flavor;
    }


    static void cleanDataBase(Context mContext){

        mContext.getContentResolver().delete(
                FlavorEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                FlavorEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Flavor table during delete", 0, cursor.getCount());
        cursor.close();
    }
}
