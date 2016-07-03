package com.example.igiagante.thegarden.home.plants.presentation.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.adapter.delegate.AdapterDelegate;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeText;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class AdapterDelegateText implements AdapterDelegate<AdapterDelegateText.TextHolder, ViewTypeText> {

    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent) {
        return new TextHolder(parent);
    }

    @Override
    public void onBindViewHolder(TextHolder holder, ViewTypeText item) {
        holder.setGardenName(item.getText());
    }

    /**
     * Inner class to hold the reference to each recycle view item
     */
    public class TextHolder extends RecyclerView.ViewHolder {

        public TextView mGardenName;

        public TextHolder(View v) {
            super(v);
            mGardenName = (TextView) v.findViewById(R.id.garden_id);
        }

        public void setGardenName(String name) {
            mGardenName.setText(name);
        }
    }
}
