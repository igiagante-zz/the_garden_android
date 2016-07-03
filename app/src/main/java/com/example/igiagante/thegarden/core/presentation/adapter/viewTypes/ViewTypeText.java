package com.example.igiagante.thegarden.core.presentation.adapter.viewTypes;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class ViewTypeText implements IViewType  {

    private String text;

    @Override
    public int getViewType() {
        return ViewTypeConstans.VIEW_TYPE_TEXT;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
