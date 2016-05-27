package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.creation.plants.respository.sqlite.FlavorContract;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 26/5/16.
 */
public class FlavorAdapter extends RecyclerViewCursorAdapter<FlavorAdapter.FlavorViewHolder> {

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
    public void onBindViewHolder(FlavorViewHolder holder, Cursor cursor) {
        holder.bindData(cursor);
    }

    @Override
    public int getItemCount() {
        return resourcesIds != null ? resourcesIds.length : 0;
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

        public void bindData(final Cursor cursor)
        {
            final String imageUrl = cursor.getString(cursor.getColumnIndex(FlavorContract.FlavorEntry.COLUMN_IMAGE_URL));
            setImageView(imageUrl);
        }

        private void setImageView(String imageUrl) {
            imageView.setImageURI(Uri.parse(imageUrl));
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
