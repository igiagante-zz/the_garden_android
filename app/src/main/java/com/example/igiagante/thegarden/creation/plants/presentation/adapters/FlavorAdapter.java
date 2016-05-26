package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.igiagante.thegarden.R;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 26/5/16.
 */
public class FlavorAdapter extends RecyclerView.Adapter<FlavorAdapter.FlavorViewHolder> {

    private Context mContext;
    private final LayoutInflater layoutInflater;

    private ArrayList<Uri> urls;
    private int [] resourcesIds;

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
    public void onBindViewHolder(FlavorViewHolder holder, int position) {
        holder.setImageView(resourcesIds[position]);
    }

    @Override
    public int getItemCount() {
        return resourcesIds != null ? resourcesIds.length : 0;
    }

    // inner class to hold a reference to each flavor image of RecyclerView
    public class FlavorViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView imageView;

        public FlavorViewHolder(View v) {
            super(v);
            imageView = (SimpleDraweeView) v.findViewById(R.id.image_flavor_id);
        }

        public void setImageView(int resourceId) {
            //imageView.setImageURI(url);

            Uri uri = new Uri.Builder()
                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                    .path(String.valueOf(resourceId))
                    .build();
        // uri looks like res:/123456789
            imageView.setImageURI(uri);

            //Picasso.with(mContext).load(resourceId).into(imageView);
        }
    }

    public void setResourcesIds(int[] resourcesIds) {
        this.resourcesIds = resourcesIds;
        notifyDataSetChanged();
    }

    public void setUrls(ArrayList<Uri> urls) {
        this.urls = urls;
        notifyDataSetChanged();
    }


}
