package com.example.igiagante.thegarden.core.repository.sqlite;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract.FlavorEntry;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Used to separate low level data access API or operations from high level business service.
 * @author Ignacio Giagante, on 10/5/16.
 */
public class FlavorDao {

    private final Context mContext;

    @Inject
    public FlavorDao(Context context){
        this.mContext = context;
    }

    private String[] allColumnsFlavor = {
            FlavorEntry._ID,
            FlavorEntry.COLUMN_NAME,
            FlavorEntry.COLUMN_IMAGE_URL,
            FlavorEntry.COLUMN_LOCAL_PATH,
            FlavorEntry.COLUMN_MONGO_ID };

    /**
     * Persist a flavor object into the database.
     * @param flavor Object to be persisted.
     * @return long id from object persisted.
     */
    public long createFlavor(Flavor flavor) {

        long flavorId;

        //check if the flavor is already in database
        Cursor flavorCursor = mContext.getContentResolver().query(
                FlavorEntry.CONTENT_URI,
                new String[]{ FlavorEntry._ID },
                FlavorEntry._ID + " = ?",
                new String[]{ flavor.getId() },
                null);

        if (flavorCursor != null && flavorCursor.moveToFirst()) {
            int flavorIdIndex = flavorCursor.getColumnIndex(FlavorEntry._ID);
            flavorId = flavorCursor.getLong(flavorIdIndex);
        } else {

            ContentValues flavorValues = new ContentValues();

            flavorValues.put(FlavorEntry.COLUMN_NAME, flavor.getName());
            flavorValues.put(FlavorEntry.COLUMN_IMAGE_URL, flavor.getImageUrl());
            flavorValues.put(FlavorEntry.COLUMN_LOCAL_PATH, flavor.getLocalPath());
            flavorValues.put(FlavorEntry.COLUMN_MONGO_ID, flavor.getMongoId());

            Uri insertedUri = mContext.getContentResolver().insert(FlavorEntry.CONTENT_URI,
                    flavorValues
            );
            flavorId = ContentUris.parseId(insertedUri);
        }

        flavorCursor.close();

        return flavorId;
    }

    public void deleteAll() {
        mContext.getContentResolver().delete(
                FlavorEntry.CONTENT_URI,
                null,
                null
        );
    }

    /**
     * Add a flavor collection to Sqlite
     * @param flavors flavor list
     * @return number of rows inserted
     */
    public int add(List<Flavor> flavors) {
        return mContext.getContentResolver().bulkInsert(FlavorEntry.CONTENT_URI, getContentValues(flavors));
    }

    public void update(Flavor flavor) {
        mContext.getContentResolver().update(
                FlavorEntry.CONTENT_URI,
                getContentValuesForOneFlavor(flavor),
                FlavorEntry._ID + " = ?",
                new String[] { flavor.getId() } );
    }

    private ContentValues[] getContentValues(List<Flavor> flavors) {

        ContentValues [] values = new ContentValues[flavors.size()];

        for (int i = 0; i < flavors.size(); i++) {
            values[i] = getContentValuesForOneFlavor(flavors.get(i));
        }
        return values;
    }

    private ContentValues getContentValuesForOneFlavor(Flavor flavor) {
        ContentValues flavorValues = new ContentValues();

        flavorValues.put(FlavorEntry.COLUMN_NAME, flavor.getName());
        flavorValues.put(FlavorEntry.COLUMN_IMAGE_URL,flavor.getImageUrl().replace(Settings.DOMAIN, ""));
        flavorValues.put(FlavorEntry.COLUMN_LOCAL_PATH, flavor.getLocalPath());
        flavorValues.put(FlavorEntry.COLUMN_MONGO_ID, flavor.getId());

        return flavorValues;
    }


    /**
     * Gets a flavor from database.
     * @param flavorId id from the object to be requested.
     * @return movie.
     */
    public Flavor getFlavor(String flavorId) {

        Cursor data = mContext.getContentResolver().query(
                FlavorEntry.buildFlavorUri(Long.parseLong(flavorId)),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        if (!data.moveToFirst()) {
            return null;
        }

        return createFlavorFromCursor(data);
    }

    /**
     * Gets all the flavors from database.
     * @return ArrayList<Flavor>.
     */
    public ArrayList<Flavor> getFlavors() {

        ArrayList<Flavor> flavors = new ArrayList<>();

        Cursor flavorCursor = mContext.getContentResolver().query(
                FlavorEntry.CONTENT_URI,
                allColumnsFlavor,
                null,
                null,
                null);

        if(flavorCursor != null) {
            flavorCursor.moveToFirst();
            while(!flavorCursor.isAfterLast()){

                Flavor flavor = new Flavor();

                setFlavorCursorData(flavorCursor, flavor);

                flavors.add(flavor);
                flavorCursor.moveToNext();
            }
        }
        return flavors;
    }

    private Flavor setFlavorCursorData(Cursor flavorCursor, Flavor flavor){

        int id = flavorCursor.getColumnIndex(FlavorEntry._ID);
        int nameIndex = flavorCursor.getColumnIndex(FlavorEntry.COLUMN_NAME);
        int imageUrlIndex = flavorCursor.getColumnIndex(FlavorEntry.COLUMN_IMAGE_URL);
        int localPathIndex = flavorCursor.getColumnIndex(FlavorEntry.COLUMN_LOCAL_PATH);
        int mongoIdIndex = flavorCursor.getColumnIndex(FlavorEntry.COLUMN_MONGO_ID);

        flavor.setId(String.valueOf(id));
        flavor.setName(flavorCursor.getString(nameIndex));
        flavor.setImageUrl(Settings.DOMAIN + flavorCursor.getString(imageUrlIndex));
        flavor.setLocalPath(flavorCursor.getString(localPathIndex));
        flavor.setMongoId(flavorCursor.getString(mongoIdIndex));

        return flavor;
    }


    /**
     * Creates an object book from one Cursor.
     *
     * @param cursor Cursor object.
     * @return Flavor object.
     */
    public Flavor createFlavorFromCursor(Cursor cursor) {

        Flavor flavor = new Flavor();
        flavor.setId(Long.toHexString(cursor.getLong(cursor.getColumnIndex(FlavorEntry._ID))));
        flavor.setName(cursor.getString(cursor.getColumnIndex(FlavorEntry.COLUMN_NAME)));
        flavor.setImageUrl(cursor.getString(cursor.getColumnIndex(FlavorEntry.COLUMN_IMAGE_URL)));
        flavor.setLocalPath(cursor.getString(cursor.getColumnIndex(FlavorEntry.COLUMN_LOCAL_PATH)));
        flavor.setMongoId(cursor.getString(cursor.getColumnIndex(FlavorEntry.COLUMN_MONGO_ID)));

        cursor.close();

        return flavor;
    }
}
