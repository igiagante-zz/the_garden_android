package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 26/5/16.
 */
public class FlavorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private final LayoutInflater layoutInflater;

    private ArrayList<Flavor> flavors;

    private ArrayList<Flavor> flavorsSelected;

    private OnAddFlavor onAddFlavor;

    public interface OnAddFlavor {
        void addFlavor(int flavorId);
    }

    public FlavorAdapter(Context context, OnAddFlavor onAddFlavor) {
        this.mContext = context;
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
        ((FlavorViewHolder)holder).setFlavorPosition(position);
        ((FlavorViewHolder)holder).setImageView(flavors.get(position).getImageUrl());
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
        }

        private void changeBorder(String imageUrl) {
            GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
            RoundingParams roundingParams = hierarchy.getRoundingParams();
            if(roundingParams.getBorderWidth() == 0) {
                roundingParams.setBorder(mContext.getResources().getColor(R.color.colorPrimaryDark), 8);
            } else {
                roundingParams.setBorder(mContext.getResources().getColor(R.color.white), 0);
            }

            hierarchy.setRoundingParams(roundingParams);
            imageView.setHierarchy(hierarchy);
            imageView.setImageURI(Uri.parse(imageUrl));
        }

        public void setFlavorPosition(int flavorPosition) {
            this.flavorPosition = flavorPosition;
        }
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = (ArrayList<Flavor>)flavors;
        notifyDataSetChanged();
    }

    public void setFlavorsSelected(ArrayList<Flavor> flavorsSelected) {
        this.flavorsSelected = flavorsSelected;
    }

    public ArrayList<Flavor> getFlavors() {
        return flavors;
    }
}
