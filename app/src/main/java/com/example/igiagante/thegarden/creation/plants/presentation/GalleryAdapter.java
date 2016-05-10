package com.example.igiagante.thegarden.creation.plants.presentation;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * @author igiagante on 10/5/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Image> imagesCollection;
    private final LayoutInflater layoutInflater;
    private final OnExecutePickerImage mPicker;
    private final Context mContext;

    public interface OnExecutePickerImage {
        void pickImages();
    }

    @Inject
    public GalleryAdapter(Context context, OnExecutePickerImage picker) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imagesCollection = Collections.emptyList();
        this.mPicker = picker;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        // Return 1 if the position is not the first neither the last
        return (position == 0 || position == imagesCollection.size()) ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view;

        if (viewType == 0) {
            view = this.layoutInflater.inflate(R.layout.add_first_time_image_button_view, parent, false);
            return new ButtonAddImageHolder(view, mContext);
        } else {
            view = this.layoutInflater.inflate(R.layout.image_gallery, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ImageViewHolder) {
            final Image image = this.imagesCollection.get(position);
            ((ImageViewHolder) holder).mImage.setImageURI(Uri.parse(image.getThumbnailUrl()));
        }
    }

    @Override
    public int getItemCount() {
        return (this.imagesCollection != null) ? this.imagesCollection.size() : 0;
    }

    public void setImagesCollection(Collection<Image> imagesCollection) {
        this.imagesCollection = (List<Image>) imagesCollection;
        this.notifyDataSetChanged();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mImage;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mImage = (SimpleDraweeView) itemView.findViewById(R.id.image_gallery_id);
        }
    }

    class ButtonAddImageHolder extends RecyclerView.ViewHolder {

        Button mButtonAddImage;
        Context mContext;

        public ButtonAddImageHolder(View itemView, Context context) {
            super(itemView);
            this.mContext = context;

            mButtonAddImage = new Button(context);
            mButtonAddImage.setBackground(context.getResources().getDrawable(R.drawable.button_add_image, null));

            mButtonAddImage.setOnClickListener(view -> mPicker.pickImages());
        }
    }
}
