package com.example.igiagante.thegarden.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {

    private boolean disallowIntercept;
    private final OnRecyclerViewItemClickListener listener;
    private final GestureDetector gestureDetector;

    public RecyclerViewItemClickListener(@NonNull Context context, @NonNull OnRecyclerViewItemClickListener listener) {
        disallowIntercept = false;
        this.listener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(final RecyclerView view, MotionEvent e) {
        final View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (view.isEnabled() && childView != null && gestureDetector.onTouchEvent(e) && !disallowIntercept) {
            final int position = view.getChildAdapterPosition(childView);
            listener.onRecyclerViewItemClick(view, childView, position, 0);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        // We don't do anything
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.disallowIntercept = disallowIntercept;
    }

    public interface OnRecyclerViewItemClickListener {
        /**
         * Callback method to be invoked when an item in this AdapterView has been clicked.
         *
         * @param parent          The RecyclerView where the click happened
         * @param view            The view within the RecyclerView that was clicked (this will be a view provided by the adapter)
         * @param adapterPosition The position of the view in the adapter.
         * @param id              The row id of the item that was clicked.
         */
        void onRecyclerViewItemClick(@NonNull RecyclerView parent, @NonNull View view, int adapterPosition, long id);
    }
}