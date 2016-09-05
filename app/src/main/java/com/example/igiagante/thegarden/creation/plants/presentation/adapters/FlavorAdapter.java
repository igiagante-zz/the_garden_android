package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.FlavorHolder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 26/5/16.
 */
public class FlavorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;

    /**
     * A list with holders which contain the model and extra data
     */
    private ArrayList<FlavorHolder> flavors;

    private OnAddFlavor onAddFlavor;

    public interface OnAddFlavor {
        void addFlavor(int flavorId);
    }

    public FlavorAdapter(Context context, OnAddFlavor onAddFlavor) {
        this.onAddFlavor = onAddFlavor;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlavorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.image_flavor, parent, false);
        return new FlavorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FlavorViewHolder) holder).setFlavorPosition(position);
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
        int flavorPosition;

        public FlavorViewHolder(View flavorView) {
            super(flavorView);
            imageView = (SimpleDraweeView) flavorView.findViewById(R.id.image_flavor_id);
        }

        public void setImageView(String imageUrl) {
            imageView.setImageURI(Uri.parse(imageUrl));
            imageView.setClickable(true);
            imageView.setOnClickListener(view -> {
                onAddFlavor.addFlavor(flavorPosition);
                changeBorder(imageUrl);
            });

            setBorderOnImage(flavors.get(flavorPosition).isSelected(), imageUrl);
        }

        private void changeBorder(String imageUrl) {

            GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
            RoundingParams roundingParams = hierarchy.getRoundingParams();

            setBorderOnImage(roundingParams.getBorderWidth() == 0, imageUrl);
        }

        /**
         * Set the border with its corresponding color. If the flavor was selected, the color should be
         * green, but white.
         *
         * @param selected indicate if the flavor was selected
         * @param imageUrl image url
         */
        private void setBorderOnImage(boolean selected, String imageUrl) {
            if (selected) {
                setBorder(imageView, imageUrl, R.color.colorPrimary, 15);
            } else {
                setBorder(imageView, imageUrl, R.color.white, 0);
            }
        }

        /**
         * Create a border for the view
         *
         * @param imageView view
         * @param imageUrl  image url
         * @param color     color of the border
         * @param width     size of the border
         */
        private void setBorder(SimpleDraweeView imageView, String imageUrl, int color, float width) {
            GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
            RoundingParams roundingParams = hierarchy.getRoundingParams();
            roundingParams.setBorder(color, width);
            hierarchy.setRoundingParams(roundingParams);
            imageView.setHierarchy(hierarchy);
            imageView.setImageURI(Uri.parse(imageUrl));
        }

        public void setFlavorPosition(int flavorPosition) {
            this.flavorPosition = flavorPosition;
        }
    }

    public void setFlavors(ArrayList<FlavorHolder> flavors) {
        this.flavors = flavors;
        notifyDataSetChanged();
    }

    public ArrayList<FlavorHolder> getFlavors() {
        return flavors;
    }
}
