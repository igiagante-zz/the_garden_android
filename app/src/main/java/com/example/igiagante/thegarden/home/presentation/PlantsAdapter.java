
/**
 * Created by igiagante on 2/5/16.
 */

package com.example.igiagante.thegarden.home.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Plant;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> {

    private List<Plant> plantsCollection;
    private final LayoutInflater layoutInflater;

    @Inject
    public PlantsAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.plantsCollection = Collections.emptyList();
    }

    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantViewHolder holder, int position) {
        final Plant plant = this.plantsCollection.get(position);
        holder.textViewName.setText(plant.getName());
        holder.textViewSize.setText(String.valueOf(plant.getSize()));
    }

    @Override
    public int getItemCount() {
        return (this.plantsCollection != null) ? this.plantsCollection.size() : 0;
    }

    public void setPlantsCollection(Collection<Plant> plantsCollection) {
        this.plantsCollection = (List<Plant>) plantsCollection;
        this.notifyDataSetChanged();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name)
        TextView textViewName;
        @Bind(R.id.size)
        TextView textViewSize;

        public PlantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
