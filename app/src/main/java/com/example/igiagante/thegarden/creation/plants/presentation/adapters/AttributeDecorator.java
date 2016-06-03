package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class AttributeDecorator extends RecyclerView.ItemDecoration {

    private final int mSpace;

    public AttributeDecorator(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
    }
}
