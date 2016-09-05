package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import com.fuck_boilerplate.rx_paparazzo.entities.size.Size;

/**
 * @author Ignacio Giagante, on 5/9/16.
 */
public class CustomMaxSize implements Size {

    private int maxImageSize = 1024;

    public CustomMaxSize() {
    }

    public CustomMaxSize(int maxSize) {
        this.maxImageSize = maxSize;
    }

    public int getMaxImageSize() {
        return maxImageSize;
    }
}
