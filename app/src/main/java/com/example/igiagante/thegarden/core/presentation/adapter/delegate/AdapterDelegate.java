package com.example.igiagante.thegarden.core.presentation.adapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.IViewType;

/**
 * @param <T> the type of adapter's data source i.e. List<Accessory>
 */
public interface AdapterDelegate<VH extends RecyclerView.ViewHolder, T extends IViewType> {

    VH onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(VH holder, T item);
}
