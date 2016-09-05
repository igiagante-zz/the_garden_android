package com.example.igiagante.thegarden.show_plant.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 14/6/16.
 */
public class PlantFlavorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;

    /**
     * A list with holders which contain the model and extra data
     */
    private List<Flavor> flavors;

    public PlantFlavorAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlavorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.image_flavor_show_plant, parent, false);
        return new FlavorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FlavorViewHolder) holder).setImageView(flavors.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return flavors != null ? flavors.size() : 0;
    }

    /**
     * Inner class to hold a reference to each flavor of RecyclerView
     */
    public class FlavorViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imageView;

        public FlavorViewHolder(View flavorView) {
            super(flavorView);
            imageView = (SimpleDraweeView) flavorView.findViewById(R.id.image_flavor_id);
        }

        public void setImageView(String imageUrl) {
            imageView.setImageURI(Uri.parse(imageUrl));
        }

    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
        notifyDataSetChanged();
    }
}
