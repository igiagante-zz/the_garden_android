package com.example.igiagante.thegarden.creation.plants.presentation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;

/**
 * Helper class to create a blurred image of the current screen
 * <p/>
 */
public final class BlurBuilder {

    private BlurBuilder() {
        // private constructor
    }

    private static final int QUALITY = 70;

    public static byte[] captureScreen(@NonNull View v) {
        byte[] result = null;
        try {
            v.setDrawingCacheEnabled(true);
            Bitmap background = v.getDrawingCache();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            background.compress(Bitmap.CompressFormat.JPEG, QUALITY, bs);
            v.setDrawingCacheEnabled(false);
            result = bs.toByteArray();
        } catch (Exception e) {
            Log.d(BlurBuilder.class.getSimpleName(), "Error creating blur background", e);
        }
        return result;
    }
}
