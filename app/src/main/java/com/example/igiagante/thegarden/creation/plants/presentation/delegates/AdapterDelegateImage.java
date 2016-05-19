package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.facebook.drawee.view.SimpleDraweeView;

import me.crosswall.photo.pick.util.UriUtil;

/**
 * @author Ignacio Giagante, on 18/5/16.
 */
public class AdapterDelegateImage implements AdapterDelegate<AdapterDelegateImage.ImageViewHolder, ViewTypeImage> {

    private Context mContext;

    public AdapterDelegateImage(Context context) {
        this.mContext = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ImageViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, ViewTypeImage item) {
        holder.setImage(item.getFolderPath());
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mImage;

        public ImageViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_gallery, parent, false));
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.image_gallery_id);
        }

        public void setImage(String folderPath) {
            //mImage.setImageURI(Uri.parse(image.getUrl()));

            Uri uri = UriUtil.generatorUri(folderPath, UriUtil.LOCAL_FILE_SCHEME);
            Glide.with(mContext)
                    .load(uri)
                    .centerCrop()
                    .placeholder(mImage.getDrawable())
                    .thumbnail(0.3f)
                    .error(me.crosswall.photo.pick.R.drawable.default_error)
                    .into(mImage);
        }
    }
}
