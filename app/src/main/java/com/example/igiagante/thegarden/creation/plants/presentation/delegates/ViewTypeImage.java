package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

import com.example.igiagante.thegarden.core.domain.entity.Image;

/**
 * @author Ignacio Giagante, on 18/5/16.
 */
public class ViewTypeImage implements IViewType {

    private Image image;

    @Override
    public int getViewType() {
        return ViewTypeConstans.VIEW_TYPE_IMAGE;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
