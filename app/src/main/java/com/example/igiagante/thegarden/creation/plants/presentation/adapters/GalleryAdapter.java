package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.AdapterDelegate;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.AdapterDelegateButton;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.AdapterDelegateImage;
import com.example.igiagante.thegarden.creation.plants.presentation.viewType.IViewType;
import com.example.igiagante.thegarden.creation.plants.presentation.viewType.ViewTypeButton;
import com.example.igiagante.thegarden.creation.plants.presentation.viewType.ViewTypeConstans;
import com.example.igiagante.thegarden.creation.plants.presentation.viewType.ViewTypeImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 10/5/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnExecutePickerImage mPicker;
    private final Context mContext;

    private SparseArray<AdapterDelegate> adapterDelegates = new SparseArray<>(2);
    private List<IViewType> items = new LinkedList<>();

    public interface OnExecutePickerImage {
        void pickImages();
    }

    public interface OnDeleteImage {
        void deleteImage(int positionSelected);
    }

    public interface OnShowImages {
        void onShowImages(int pictureSelected);
    }

    @Inject
    public GalleryAdapter(Context context, OnExecutePickerImage picker, OnDeleteImage deleteImage,
                          OnShowImages onShowImages) {
        this.mPicker = picker;
        this.mContext = context;

        // add adapter delegates
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_BUTTON, new AdapterDelegateButton(mContext, mPicker));
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_IMAGE,
                new AdapterDelegateImage(mContext, deleteImage, onShowImages));

        // add first item -> button
        items.add(new ViewTypeButton());
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterDelegates.get(viewType).onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IViewType item = items.get(position);
        adapterDelegates.get(item.getViewType()).onBindViewHolder(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setImagesPath(List<String> filesPaths) {
        this.items.clear();
        this.items.addAll(getImagesCollection(filesPaths));
        this.items.add(new ViewTypeButton());
        notifyDataSetChanged();
    }

    public void addImagesPaths(List<String> filesPaths) {
        notifyImagesCollection(getImagesCollection(filesPaths));
    }

    public void deleteImage(int position) {
        this.items.remove(position);
        notifyItemRemoved(position);
    }

    private void notifyImagesCollection(Collection<ViewTypeImage> imagesCollection) {

        if(!imagesCollection.isEmpty()) {
            this.items.addAll(0, imagesCollection);
            this.notifyItemRangeInserted(0, imagesCollection.size());
        }
    }

    private Collection<ViewTypeImage> getImagesCollection(List<String> folderPaths) {

        ArrayList<ViewTypeImage> viewTypeImages = new ArrayList<>();

        for(int i = 0; i < folderPaths.size(); i++) {
            ViewTypeImage viewTypeImage = new ViewTypeImage();
            viewTypeImage.setImagePath(folderPaths.get(i));
            viewTypeImage.setPositionSelected(i);
            viewTypeImages.add(viewTypeImage);
        }

        return  viewTypeImages;
    }

}
