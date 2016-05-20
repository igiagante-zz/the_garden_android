package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.creation.plants.presentation.viewType.IViewType;

/**
 * @param <T> the type of adapters data source i.e. List<Accessory>
 */
public interface AdapterDelegate<VH extends RecyclerView.ViewHolder, T extends IViewType> {

    VH onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(VH holder, T item);
}
