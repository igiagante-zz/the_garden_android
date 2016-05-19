package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

/**
 * @author Ignacio Giagante, on 18/5/16.
 */
public class ViewTypeImage implements IViewType {

    private String imagePath;

    @Override
    public int getViewType() {
        return ViewTypeConstans.VIEW_TYPE_IMAGE;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
