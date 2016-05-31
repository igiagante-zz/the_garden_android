package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorContract;
import com.facebook.drawee.view.SimpleDraweeView;

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

    public FlavorAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlavorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.image_flavor, parent, false);
        return new FlavorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FlavorViewHolder)holder).setImageView(flavors.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Inner class to hold a reference to each flavor of RecyclerView
     */
    public class FlavorViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView imageView;

        public FlavorViewHolder(View flavorView) {
            super(flavorView);
            imageView = (SimpleDraweeView) flavorView.findViewById(R.id.image_flavor_id);
        }

        private void setImageView(String imageUrl) {
            imageView.setImageURI(Uri.fromFile(new File(imageUrl)));
            //Picasso.with(mContext).load(resourceId).into(imageView);
        }
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = (ArrayList<Flavor>)flavors;
        notifyDataSetChanged();
    }

}
