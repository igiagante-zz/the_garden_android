package com.example.igiagante.thegarden.home.plants.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.show_plant.presentation.GetPlantDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author giagante on 5/5/16.
 *         Create an adapter for RecycleView Plants
 */
public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> implements Filterable {

    public static final String SHOW_PLANT_KEY = "SHOW_PLANT";

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

    private ArrayList<PlantHolder> mPlants;

    private ArrayList<PlantHolder> filteredPlantList;

    private List<Image> mImages;

    private PlantFilter plantFilter;

    private OnSendEmail onSendEmail;

    public interface OnSendEmail {
        void sendEmail(String emailText, ArrayList<String> urls);
    }

    public interface OnEditPlant {
        void editPlant(PlantHolder plantHolder);
    }

    public interface OnDeletePlant {
        void showDeletePlantDialog(int position);
        void deletePlant(String plantId);
    }

    public PlantsAdapter(Context context, OnSendEmail onSendEmail) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mImages = Collections.emptyList();
        this.mPlants = new ArrayList<>();
        this.onSendEmail = onSendEmail;
    }

    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.card_view_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantViewHolder holder, int position) {

        final PlantHolder plantHolder = this.filteredPlantList.get(position);

        mImages = plantHolder.getImages();
        holder.setImages(mImages);

        Image mainImage = plantHolder.getMainImage();

        if (mainImage != null) {
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
        String highLabel = mContext.getString(R.string.height);
        holder.mHeight.setText(highLabel + ": " + String.valueOf(plantHolder.getSize()));
        String floweringTimeLabel = mContext.getString(R.string.flower);
        holder.mFloweringTime.setText(floweringTimeLabel + ": " + plantHolder.getFloweringTime());

        holder.mEditButton.setOnClickListener(v -> onEditPlant.get().editPlant(plantHolder));
        holder.mDeleteButton.setOnClickListener(v -> onDeletePlant.showDeletePlantDialog(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return (this.filteredPlantList != null) ? this.filteredPlantList.size() : 0;
    }

    public void setPlants(Collection<PlantHolder> mPlants) {
        this.mPlants = (ArrayList<PlantHolder>) mPlants;
        this.filteredPlantList = new ArrayList<>(this.mPlants);
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
        if (!mPlants.isEmpty()) {
            this.mPlants.remove(plantDeletedPosition);
            this.filteredPlantList.remove(plantDeletedPosition);
            this.notifyItemRemoved(plantDeletedPosition);
        }
    }

    public void setOnEditPlant(OnEditPlant onEditPlant) {
        this.onEditPlant = new WeakReference<>(onEditPlant);
    }

    public void setOnDeletePlant(OnDeletePlant onDeletePlant) {
        this.onDeletePlant = onDeletePlant;
    }

    @Override
    public Filter getFilter() {
        if (plantFilter == null) {
            plantFilter = new PlantFilter(this, mPlants);
        }
        return plantFilter;
    }

    private static class PlantFilter extends Filter {

        private final PlantsAdapter adapter;

        private final List<PlantHolder> originalList;

        private final List<PlantHolder> filteredList;

        private PlantFilter(PlantsAdapter adapter, List<PlantHolder> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            filteredList.clear();

            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final PlantHolder plant : originalList) {
                    if (plant.getName().contains(filterPattern)) {
                        filteredList.add(plant);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredPlantList.clear();
            List<PlantHolder> values = (List<PlantHolder>) results.values;
            adapter.filteredPlantList.addAll(values);
            adapter.notifyDataSetChanged();
        }
    }

    class PlantViewHolder extends RecyclerView.ViewHolder {

        private List<Image> mImages;

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
        TextView mHeight;

        @Bind(R.id.flower_time_id)
        TextView mFloweringTime;

        @Bind(R.id.edit_plant_button)
        Button mEditButton;

        @Bind(R.id.plant_delete_button_id)
        Button mDeleteButton;

        @Bind(R.id.share_plant_button)
        Button mSharePlantButton;

        public PlantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if(checkInternet()) {
                    startGetPlantDataActivity(getAdapterPosition());
                } else {
                    showMessageNoInternetConnection();
                }
            });

            mSharePlantButton.setOnClickListener(v -> sendEmail(getEmailText()));
        }

        private void startGetPlantDataActivity(int adapterPosition) {
            PlantHolder plantHolder = mPlants.get(adapterPosition);
            Intent intent = new Intent(mContext, GetPlantDataActivity.class);
            intent.putExtra(SHOW_PLANT_KEY, plantHolder);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

        private void sendEmail(String emailText) {
            onSendEmail.sendEmail(emailText, getUrls());
        }

        public void setImages(List<Image> mImages) {
            this.mImages = mImages;
        }

        private ArrayList<String> getUrls() {
            ArrayList<String> urls = new ArrayList<>();
            for(Image image : mImages) {
                urls.add(image.getUrl());
            }
            return  urls;
        }

        @NonNull
        private String getEmailText() {

            StringBuilder builder = new StringBuilder();
            builder.append(mContext.getString(R.string.email_plant_name, mPlantName.getText()));
            builder.append("\n");
            builder.append(mGenotype.getText());
            builder.append("\n");
            builder.append(mHeight.getText());
            builder.append("\n");
            builder.append(mSeedDate.getText());
            builder.append("\n");
            builder.append(mFloweringTime.getText());
            builder.append("\n");

            return builder.toString();
        }

        private boolean checkInternet() {
            boolean isConnected;
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

            return isConnected;
        }

        private void showMessageNoInternetConnection() {
            String string = mContext.getString(R.string.there_is_not_internet_connection);
            Toast toast = Toast.makeText(mContext, string, Toast.LENGTH_LONG);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            textView.setGravity(Gravity.CENTER);
            toast.show();
        }
    }
}
