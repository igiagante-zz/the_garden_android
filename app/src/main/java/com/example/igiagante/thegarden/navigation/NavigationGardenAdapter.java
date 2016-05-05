package com.example.igiagante.thegarden.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Garden;

import java.util.ArrayList;

/**
 * Create a view adapter for Garden RecycleView
 * @author igiagante on 5/5/16.
 */
public class NavigationGardenAdapter extends RecyclerView.Adapter<NavigationGardenAdapter.ItemViewHolder> {

    private ArrayList<Garden> gardens;

    public NavigationGardenAdapter(ArrayList<Garden> gardens) {
        this.gardens = gardens;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_garden, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Garden garden = gardens.get(position);
        holder.mGardenName.setText(garden.getName());
    }

    @Override
    public int getItemCount() {
        return gardens == null ? 0 : gardens.size();
    }

    /**
     * Inner class to hold the reference to each recycle view item
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mGardenName;

        public ItemViewHolder(View v) {
            super(v);
            mGardenName = (TextView) v.findViewById(R.id.garden_id);
        }
    }
}
