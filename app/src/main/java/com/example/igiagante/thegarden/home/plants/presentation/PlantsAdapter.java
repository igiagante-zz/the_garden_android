package com.example.igiagante.thegarden.home.plants.presentation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.show_plant.presentation.GetPlantDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
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

    public static final String SHOW_PLANT_KEY = "SHOW_PLANT";

    private List<PlantHolder> mPlants;
    private final LayoutInflater layoutInflater;
    private Context mContext;

    /**
     * Reference to MainActivity
     */
    private WeakReference<OnEditPlant> onEditPlant;

    private OnDeletePlant onDeletePlant;

    /**
     * Save the position from the last plant which was deleted. If the use case `delete plant` finishes
     * successfully, then the plants list should be updated.
     */
    private int plantDeletedPosition;

    public interface OnEditPlant {
        void editPlant(PlantHolder plantHolder);
    }

    public interface OnDeletePlant {
        void showDeletePlantDialog(int position);
        void deletePlant(String plantId);
    }

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
        // TODO - check this. Sth the model is null
        holder.mSeedDate.setText(seedDateLabel + ": " + plantHolder.getSeedDate());
        String genotypeLabel = mContext.getString(R.string.genotype);
        holder.mGenotype.setText(genotypeLabel + ": " + plantHolder.getGenotype());
        String harvestLabel = mContext.getString(R.string.harvest);
        holder.mHarvest.setText(harvestLabel + ": " + String.valueOf(plantHolder.getHarvest()));
        String highLabel = mContext.getString(R.string.height);
        holder.mHigh.setText(highLabel + ": " + String.valueOf(plantHolder.getSize()));
        String floweringTimeLabel = mContext.getString(R.string.flower);
        holder.mFloweringTime.setText(floweringTimeLabel + ": " + plantHolder.getFloweringTime());

        holder.mEditButton.setOnClickListener(v -> onEditPlant.get().editPlant(plantHolder));
        holder.mDeleteButton.setOnClickListener(v -> onDeletePlant.showDeletePlantDialog(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return (this.mPlants != null) ? this.mPlants.size() : 0;
    }

    public void setPlants(Collection<PlantHolder> mPlants) {
        this.mPlants = (List<PlantHolder>) mPlants;
        this.notifyDataSetChanged();
    }

    public void deletePlant(int position) {
        this.plantDeletedPosition = position;
        PlantHolder plantHolder = mPlants.get(position);
        this.onDeletePlant.deletePlant(plantHolder.getPlantId());
    }

    /**
     * Remove plant from the adapter's list
     */
    public void removePlant() {
        if(!mPlants.isEmpty()) {
            this.mPlants.remove(plantDeletedPosition);
            this.notifyItemRemoved(plantDeletedPosition);
        }
    }

    public void setOnEditPlant(OnEditPlant onEditPlant) {
        this.onEditPlant = new WeakReference<>(onEditPlant);
    }

    public void setOnDeletePlant(OnDeletePlant onDeletePlant) {
        this.onDeletePlant = onDeletePlant;
    }

    class PlantViewHolder extends RecyclerView.ViewHolder {

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

        @Bind(R.id.edit_plant_button)
        Button mEditButton;

        @Bind(R.id.plant_delete_button_id)
        Button mDeleteButton;

        public PlantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> startGetPlantDataActivity(getAdapterPosition()));
        }

        private void startGetPlantDataActivity(int adapterPosition) {
            PlantHolder plantHolder = mPlants.get(adapterPosition);
            Intent intent = new Intent(mContext, GetPlantDataActivity.class);
            intent.putExtra(SHOW_PLANT_KEY, plantHolder);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Activity activity = (MainActivity)onEditPlant.get();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOnClickListener(view ->
                                mContext.startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()));
            } else {
                mContext.startActivity(intent);
            }
        }
    }
}
