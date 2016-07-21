package com.example.igiagante.thegarden.home.gardens.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.adapter.delegate.AdapterDelegate;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.IViewType;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeButton;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeConstans;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;
import com.example.igiagante.thegarden.home.gardens.presentation.delegates.AdapterDelegateButtonAddGarden;
import com.example.igiagante.thegarden.home.gardens.presentation.delegates.AdapterDelegateGarden;
import com.example.igiagante.thegarden.home.gardens.presentation.viewTypes.ViewTypeGarden;

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

    private SparseArray<AdapterDelegate> adapterDelegates = new SparseArray<>(2);
    private List<IViewType> items = new LinkedList<>();

    public NavigationGardenAdapter(Context context, AdapterDelegateButtonAddGarden.OnGardenDialog onGardenDialog,
                                   AdapterDelegateGarden.OnClickGardenListener onClickLongListener) {
        this.mContext = context;

        // add adapter delegates
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_BUTTON, new AdapterDelegateButtonAddGarden(mContext, onGardenDialog));
        adapterDelegates.put(ViewTypeConstans.VIEW_TYPE_GARDEN, new AdapterDelegateGarden(onClickLongListener));

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
        return items == null ? 0 : items.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    /**
     * Set the items collection using gardens' names and add the button again.
     *
     * @param gardens List of Gardens
     */
    public void setGardens(List<GardenHolder> gardens) {
        this.items.clear();
        this.items.addAll(getGardenCollection(gardens));
        this.items.add(new ViewTypeButton());
        notifyDataSetChanged();
    }

    public IViewType getItem(int position) {
        return this.items.get(position);
    }

    public void removeGarden(int position) {
        if(items.size() > 1) {
            this.items.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * If the garden exists, it will be updated. In other case, it will be persisted.
     * @param garden Garden Object
     */
    public void addOrUpdateGarden(Garden garden) {
        int position = existGarden(garden);
        if (position != -1) {
            updateItem(garden, position);
        } else {
            addGarden(garden);
        }
    }

    /**
     * Update garden data
     * @param garden Garden Object
     */
    private void updateItem(Garden garden, int position) {
        ViewTypeGarden viewTypeGarden = (ViewTypeGarden) items.get(position);
        if (viewTypeGarden.getId().equals(garden.getId())) {
            viewTypeGarden.setName(garden.getName());
            viewTypeGarden.setStartDate(garden.getStartDate());
            viewTypeGarden.setPlants((ArrayList<Plant>) garden.getPlants());
            notifyItemChanged(position);
        }
    }

    /**
     * Add new garden to the list
     * @param garden Garden Object
     */
    private void addGarden(Garden garden) {
        this.items.remove(items.size() - 1);
        this.items.add(createViewTypeGarden(garden));
        this.items.add(new ViewTypeButton());
        this.notifyDataSetChanged();
    }

    /**
     * Check if the garden is already on the list
     * @param garden Garden Object
     * @return garden's position within list or -1 in case doesn't exist
     */
    private int existGarden(Garden garden) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ViewTypeGarden) {
                ViewTypeGarden viewTypeGarden = (ViewTypeGarden) items.get(i);
                if (viewTypeGarden.getId().equals(garden.getId())) {
                    return i;
                }
            }
        }
        return -1;
    }

    private Collection<ViewTypeGarden> getGardenCollection(List<GardenHolder> gardens) {

        ArrayList<ViewTypeGarden> viewTypeGardens = new ArrayList<>();

        for (GardenHolder gardenHolder : gardens) {
            viewTypeGardens.add(createViewTypeGarden(gardenHolder.getModel()));
        }

        return viewTypeGardens;
    }

    private ViewTypeGarden createViewTypeGarden(Garden garden) {
        ViewTypeGarden viewTypeGarden = new ViewTypeGarden();
        viewTypeGarden.setId(garden.getId());
        viewTypeGarden.setName(garden.getName());
        viewTypeGarden.setStartDate(garden.getStartDate());
        viewTypeGarden.setPlants((ArrayList<Plant>) garden.getPlants());
        return viewTypeGarden;
    }

}
