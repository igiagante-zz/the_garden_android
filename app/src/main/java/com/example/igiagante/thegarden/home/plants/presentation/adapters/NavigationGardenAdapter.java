package com.example.igiagante.thegarden.home.plants.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.adapter.delegate.AdapterDelegate;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.IViewType;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeButton;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeConstans;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeText;
import com.example.igiagante.thegarden.creation.plants.presentation.viewTypes.ViewTypeImage;
import com.example.igiagante.thegarden.home.plants.presentation.delegates.AdapterDelegateButtonAddGarden;
import com.example.igiagante.thegarden.home.plants.presentation.delegates.AdapterDelegateText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Create a view adapter for Garden RecycleView
 *
 * @author Ignacio Giagante, on 5/5/16.
 */
public class NavigationGardenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Garden> gardens;

    private SparseArray<AdapterDelegate> adapterDelegates = new SparseArray<>(2);
    private List<IViewType> items = new LinkedList<>();

    public NavigationGardenAdapter(ArrayList<Garden> gardens) {
        this.gardens = gardens;

        // add adapter delegates
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_BUTTON, new AdapterDelegateButtonAddGarden(mContext));
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_TEXT, new AdapterDelegateText());

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
        return gardens == null ? 0 : gardens.size();
    }

    /**
     * Set the items collection using gardens' names and add the button again.
     * @param gardens List of Gardens
     */
    public void setGardens(List<String> gardens) {
        this.items.clear();
        this.items.addAll(getGardenCollection(gardens));
        this.items.add(new ViewTypeButton());
        notifyDataSetChanged();
    }

    private Collection<ViewTypeText> getGardenCollection(List<String> gardens) {

        ArrayList<ViewTypeText> viewTypeTexts = new ArrayList<>();

        for (String name : gardens) {
            ViewTypeText viewTypeText = new ViewTypeText();
            viewTypeText.setText(name);
            viewTypeTexts.add(viewTypeText);
        }

        return viewTypeTexts;
    }

}
