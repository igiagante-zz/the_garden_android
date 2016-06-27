package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.GalleryAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.viewTypes.ViewTypeImage;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

/**
 * @author Ignacio Giagante, on 18/5/16.
 */
public class AdapterDelegateImage implements AdapterDelegate<AdapterDelegateImage.ImageViewHolder, ViewTypeImage> {

    private GalleryAdapter.OnDeleteImage onDeleteImage;
    private GalleryAdapter.OnShowImages onShowImages;
    private Context mContext;

    public AdapterDelegateImage(Context context, GalleryAdapter.OnDeleteImage onDeleteImage,
                                GalleryAdapter.OnShowImages onShowImages) {
        this.onDeleteImage = onDeleteImage;
        this.mContext = context;
        this.onShowImages = onShowImages;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ImageViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, ViewTypeImage item) {
        holder.setImagePath(item.getImagePath());
        holder.setPositionImage(item.getPositionSelected());
    }

    /**
     * Inner class to hold a reference to each ImageViewHolder of RecyclerView
     */
    class ImageViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mImage;
        Button mDeleteButton;
        int positionImage;
        String imagePath;

        public ImageViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_gallery, parent, false));
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.image_gallery_id);

            mImage.setOnClickListener(view -> onShowImages.onShowImages(positionImage));

            mDeleteButton = (Button) itemView.findViewById(R.id.delete_button_id);
            mDeleteButton.setOnClickListener(view -> onDeleteImage.deleteImage(positionImage));
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
            if(imagePath.contains("http")) {
                mImage.setImageURI(Uri.parse(imagePath));
            } else {
                mImage.setImageURI(Uri.fromFile(new File(imagePath)));
            }
        }

        public void setPositionImage(int positionImage) {
            this.positionImage = positionImage;
        }
    }
}
