package com.example.igiagante.thegarden.home.plants.presentation;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.show_plant.presentation.GetPlantDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author giagante on 5/5/16.
 *         Create an adapter for RecycleView Plants
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


    private List<Image> mImages;

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

        mImages = plantHolder.getImages();
        holder.setImages(mImages);

        Image mainImage = plantHolder.getMainImage();

        if (mainImage != null) {
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
        holder.mHeight.setText(highLabel + ": " + String.valueOf(plantHolder.getSize()));
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
        if (!mPlants.isEmpty()) {
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

            itemView.setOnClickListener(v -> startGetPlantDataActivity(getAdapterPosition()));

            mSharePlantButton.setOnClickListener(v -> sendEmail());
        }

        private void startGetPlantDataActivity(int adapterPosition) {
            PlantHolder plantHolder = mPlants.get(adapterPosition);
            Intent intent = new Intent(mContext, GetPlantDataActivity.class);
            intent.putExtra(SHOW_PLANT_KEY, plantHolder);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

        private void sendEmail() {
            createAttachmentAndSendEmail();
        }

        public void setImages(List<Image> mImages) {
            this.mImages = mImages;
        }

        private void createShareIntent(List<String> filesPaths) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            String subject = mContext.getResources().getString(R.string.subject_email);

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getEmailText());

            ArrayList<Uri> uris = new ArrayList<>();

            for (String file : filesPaths)
            {
                File fileIn = new File(file);
                Uri u = Uri.fromFile(fileIn);
                uris.add(u);
            }

            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            mContext.startActivity(Intent.createChooser(shareIntent, mContext.getString(R.string.share_plant_info)));
        }

        private void createAttachmentAndSendEmail() {

            Observable<List<String>> downloadObservable = Observable.create(
                    sub -> {

                        ArrayList<String> filesPaths = new ArrayList<>();
                        ArrayList<String> urls = new ArrayList<>();

                        for (Image image : mImages) {
                            urls.add(image.getUrl());
                        }

                        OutputStream output = null;

                        File folder = new File(Environment.getExternalStorageDirectory() + "/plants");
                        folder.mkdirs();

                        for (int i = 0; i < urls.size(); i++) {

                            try {

                                File tempFile = new File(folder.getAbsolutePath(), "image" + i + ".jpeg");
                                String filePath = tempFile.getAbsolutePath();

                                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(urls.get(i)).getContent());
                                output = new BufferedOutputStream(new FileOutputStream(filePath));
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

                                output.flush();
                                output.close();

                                filesPaths.add(tempFile.getAbsolutePath());

                            } catch (Exception e) {
                                sub.onError(e);
                            }
                        }
                        sub.onNext(filesPaths);
                        sub.onCompleted();
                    }
            );

            Subscriber<List<String>> mySubscriber = new Subscriber<List<String>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(List<String> filesPaths) {
                    createShareIntent(filesPaths);
                }
            };

            downloadObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mySubscriber);
        }

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
    }
}
