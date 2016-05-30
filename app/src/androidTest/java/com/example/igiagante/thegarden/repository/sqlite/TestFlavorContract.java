package com.example.igiagante.thegarden.repository.sqlite;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class TestFlavorContract extends AndroidTestCase {

    private static final long TEST_FLAVOR_ID = 12345;

    public void testBuildMovieUri() {
        Uri movieUri = FlavorContract.FlavorEntry.buildFlavorUri(TEST_FLAVOR_ID);
        assertNotNull("Error: Null Uri returned.", movieUri);
        assertEquals("Error: Flavor Uri doesn't match our expected result",
                movieUri.toString(),
                "content://com.igiagante.provider.flavors/flavor/12345");
    }
}
