package com.example.igiagante.thegarden.creation.plants.presentation;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.AdapterDelegate;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.AdapterDelegateButton;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.AdapterDelegateImage;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.IViewType;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.ViewTypeButton;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.ViewTypeConstans;
import com.example.igiagante.thegarden.creation.plants.presentation.delegates.ViewTypeImage;

import java.io.File;
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

    @Inject
    public GalleryAdapter(Context context, OnExecutePickerImage picker) {
        this.mPicker = picker;
        this.mContext = context;

        // add adapter delegates
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_BUTTON, new AdapterDelegateButton(mContext, mPicker));
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_IMAGE, new AdapterDelegateImage());

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
        notifyImagesCollection(getImagesCollection(filesPaths));
    }

    private void notifyImagesCollection(Collection<ViewTypeImage> imagesCollection) {
        int size = items.size() - 1;
        this.items.addAll(imagesCollection);
        this.notifyItemRangeInserted(size, imagesCollection.size());
    }

    private Collection<ViewTypeImage> getImagesCollection(List<String> folderPaths) {

        ArrayList<ViewTypeImage> viewTypeImages = new ArrayList<>();

        for(String path : folderPaths) {
            ViewTypeImage viewTypeImage = new ViewTypeImage();
            viewTypeImage.setImagePath(path);
            viewTypeImages.add(viewTypeImage);
        }
        return  viewTypeImages;
    }

}
