package com.example.igiagante.thegarden.home.plants.presentation;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author giagante on 5/5/16.
 * Create an adapter for RecycleView Plants
 */
public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> {

    private List<PlantHolder> mPlants;
    private final LayoutInflater layoutInflater;
    private Context mContext;

    @Inject
    public PlantsAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPlants = Collections.emptyList();
    }

    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.card_view_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantViewHolder holder, int position) {
        final PlantHolder plantHolder = this.mPlants.get(position);

        Image mainImage = plantHolder.getMainImage();

        if(mainImage != null) {
            String thumbnailUrl = mainImage.getThumbnailUrl();
            holder.mPlantImage.setImageURI(Uri.parse(thumbnailUrl));
        }

        holder.mPlantName.setText(plantHolder.getName());
        String seedDateLabel = mContext.getString(R.string.seedDate);
        holder.mSeedDate.setText(seedDateLabel + ": " + plantHolder.getSeedDate());
        String genotypeLabel = mContext.getString(R.string.genotype);
        holder.mGenotype.setText(genotypeLabel + ": " + plantHolder.getGenotype());
        String harvestLabel = mContext.getString(R.string.harvest);
        holder.mHarvest.setText(harvestLabel + ": " + String.valueOf(plantHolder.getHarvest()));
        String highLabel = mContext.getString(R.string.high);
        holder.mHigh.setText(highLabel + ": " + String.valueOf(plantHolder.getSize()));
        String floweringTimeLabel = mContext.getString(R.string.flower);
        holder.mFloweringTime.setText(floweringTimeLabel + ": " + plantHolder.getFloweringTime());
    }

    @Override
    public int getItemCount() {
        return (this.mPlants != null) ? this.mPlants.size() : 0;
    }

    public void setPlants(Collection<PlantHolder> mPlants) {
        this.mPlants = (List<PlantHolder>) mPlants;
        this.notifyDataSetChanged();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.main_image_plant)
        SimpleDraweeView mPlantImage;

        @Bind(R.id.plant_name)
        TextView mPlantName;

        @Bind(R.id.seed_date_id)
        TextView mSeedDate;

        @Bind(R.id.plant_genotype_id)
        TextView mGenotype;

        @Bind(R.id.harvest_id)
        TextView mHarvest;

        @Bind(R.id.high_id)
        TextView mHigh;

        @Bind(R.id.flower_time_id)
        TextView mFloweringTime;

        public PlantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
