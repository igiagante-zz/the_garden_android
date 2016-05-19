package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

/**
 * @author Ignacio Giagante, on 18/5/16.
 */
public class ViewTypeImage implements IViewType {

    private String folderPath;

    @Override
    public int getViewType() {
        return ViewTypeConstans.VIEW_TYPE_IMAGE;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
