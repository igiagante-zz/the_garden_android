package com.example.igiagante.thegarden.core.repository.sqlite;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract.FlavorEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Used to separate low level data accessing API or operations from high level business service.
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
            FlavorEntry.COLUMN_IMAGE_URL };

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
                new String[]{FlavorEntry._ID},
                FlavorEntry._ID + " = ?",
                new String[]{ flavor.getId() },
                null);

        if (flavorCursor != null && flavorCursor.moveToFirst()) {
            int flavorIdIndex = flavorCursor.getColumnIndex(FlavorEntry._ID);
            flavorId = flavorCursor.getLong(flavorIdIndex);
        } else {

            ContentValues flavorValues = new ContentValues();

            flavorValues.put(FlavorEntry._ID, flavor.getId());
            flavorValues.put(FlavorEntry.COLUMN_NAME, flavor.getName());
            flavorValues.put(FlavorEntry.COLUMN_IMAGE_URL,flavor.getImageUrl());

            Uri insertedUri = mContext.getContentResolver().insert(FlavorEntry.CONTENT_URI,
                    flavorValues
            );
            flavorId = ContentUris.parseId(insertedUri);
        }

        flavorCursor.close();

        return flavorId;
    }

    /**
     * Add a flavor collection to Sqlite
     * @param flavors flavor list
     * @return number of rows inserted
     */
    public int add(List<Flavor> flavors) {
        return mContext.getContentResolver().bulkInsert(FlavorEntry.CONTENT_URI, getContentValues(flavors));
    }

    private ContentValues[] getContentValues(List<Flavor> flavors) {

        ContentValues [] values = new ContentValues[flavors.size()];

        for (int i = 0; i < flavors.size(); i++) {

            ContentValues flavorValues = new ContentValues();

            flavorValues.put(FlavorEntry.COLUMN_NAME, flavors.get(i).getName());
            flavorValues.put(FlavorEntry.COLUMN_IMAGE_URL,flavors.get(i).getImageUrl());

            values[i] = flavorValues;
        }

        return values;
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

    private Flavor setFlavorCursorData(Cursor movieCursor, Flavor flavor){

        int id = movieCursor.getColumnIndex(FlavorEntry._ID);
        int nameIndex = movieCursor.getColumnIndex(FlavorEntry.COLUMN_NAME);
        int imageUrlIndex = movieCursor.getColumnIndex(FlavorEntry.COLUMN_IMAGE_URL);

        flavor.setId(String.valueOf(id));
        flavor.setName(movieCursor.getString(nameIndex));
        flavor.setImageUrl(movieCursor.getString(imageUrlIndex));

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
        flavor.setId(cursor.getString(cursor.getColumnIndex(FlavorEntry._ID)));
        flavor.setName(cursor.getString(cursor.getColumnIndex(FlavorEntry.COLUMN_NAME)));
        flavor.setImageUrl(cursor.getString(cursor.getColumnIndex(FlavorEntry.COLUMN_IMAGE_URL)));

        cursor.close();

        return flavor;
    }
}
