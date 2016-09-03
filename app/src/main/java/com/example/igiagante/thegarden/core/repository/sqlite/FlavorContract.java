package com.example.igiagante.thegarden.core.repository.sqlite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Ignacio Giagante, on 27/5/16.
 */
public class FlavorContract {

    // The "Content authority" is a name for the entire content provider
    public static final String CONTENT_AUTHORITY = "com.igiagante.provider.flavors";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths
    public static final String PATH_FLAVOR = "flavor";

    /* Inner class that defines the contents of the movie table */
    public static final class FlavorEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FLAVOR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLAVOR;
        public static final String CONTENT_TYPE_ITEM =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLAVOR;

        public static final String TABLE_NAME = "flavor";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_IMAGE_URL = "imageUrl";

        public static final String COLUMN_LOCAL_PATH = "localPath";

        public static final String COLUMN_MONGO_ID = "mongoId";

        public static Uri buildFlavorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
