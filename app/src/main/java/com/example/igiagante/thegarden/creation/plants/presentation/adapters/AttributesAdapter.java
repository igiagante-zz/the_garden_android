package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.ui.TagView;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.AttributeHolder;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 6/2/16.
 */
public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.AttributeViewHolder> {

    private ArrayList<AttributeHolder> attributeHolders;
    private Context mContext;
    private final LayoutInflater layoutInflater;

    public AttributesAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public AttributeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tag_view_item, parent, false);
        return new AttributeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttributeViewHolder holder, int position) {
        holder.tagView.setTagName(attributeHolders.get(position).getTagName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Inner class to hold a reference to each flavor of RecyclerView
     */
    public class AttributeViewHolder extends RecyclerView.ViewHolder {

        TagView tagView;
        int attributePosition;

        public AttributeViewHolder(View flavorView) {
            super(flavorView);
            tagView = (TagView) flavorView.findViewById(R.id.image_flavor_id);
        }
    }

    public void setAttributeHolders(ArrayList<AttributeHolder> attributeHolders) {
        this.attributeHolders = attributeHolders;
    }
}
