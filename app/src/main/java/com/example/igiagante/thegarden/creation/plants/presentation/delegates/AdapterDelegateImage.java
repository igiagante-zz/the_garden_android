package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author Ignacio Giagante, on 18/5/16.
 */
public class AdapterDelegateImage implements AdapterDelegate<AdapterDelegateImage.ImageViewHolder, ViewTypeImage> {

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ImageViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, ViewTypeImage item) {
        holder.setImagePath(item.getImagePath());
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mImage;

        public ImageViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_gallery, parent, false));
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.image_gallery_id);
        }

        public void setImagePath(String imagePath) {
            mImage.setImageURI(Uri.parse(imagePath));
        }
    }
}
