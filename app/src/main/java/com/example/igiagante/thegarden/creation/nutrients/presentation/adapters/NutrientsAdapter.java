package com.example.igiagante.thegarden.creation.nutrients.presentation.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientsAdapter extends RecyclerView.Adapter<NutrientsAdapter.NutrientViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<Nutrient> nutrients;

    private OnNutrientSelected mOnNutrientSelected;
    private OnDeleteNutrient mOnDeleteNutrient;

    /**
     * Save the position from the last nutrient which was deleted. If the use case `delete plant` finishes
     * successfully, then the nutrients list should be updated.
     */
    private int nutrientDeletedPosition;

    public interface OnNutrientSelected {
        void showNutrientDetails(Nutrient nutrient);
    }

    public interface OnDeleteNutrient {
        void showDeleteNutrientDialog(int position);

        void deleteNutrient(String nutrientId);
    }

    public NutrientsAdapter(Context context, OnNutrientSelected onNutrientSelected, OnDeleteNutrient onDeleteNutrient) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.nutrients = new ArrayList<>();
        this.mOnNutrientSelected = onNutrientSelected;
        this.mOnDeleteNutrient = onDeleteNutrient;
    }

    @Override
    public NutrientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.card_view_nutrient, parent, false);
        return new NutrientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NutrientViewHolder holder, int position) {
        final Nutrient nutrient = this.nutrients.get(position);

        List<Image> images = nutrient.getImages();
        if (!images.isEmpty()) {
            Image mainImage = images.get(0);
            if (mainImage != null) {
                String thumbnailUrl = mainImage.getThumbnailUrl();
                holder.mNutrientImage.setImageURI(Uri.parse(thumbnailUrl));
            }
        }

        holder.setPosition(position);
        holder.mNutrientName.setText(nutrient.getName());
        holder.npk.setText(mContext.getString(R.string.nutrient_npk, nutrient.getNpk()));
        holder.ph.setText(mContext.getString(R.string.nutrient_ph, String.valueOf(nutrient.getPh())));

        holder.mDeleteButton.setOnClickListener(v -> mOnDeleteNutrient.showDeleteNutrientDialog(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return (this.nutrients != null) ? this.nutrients.size() : 0;
    }

    /**
     * Set list of nutrients
     *
     * @param nutrients
     */
    public void setNutrients(ArrayList<Nutrient> nutrients) {
        this.nutrients = new ArrayList<>(nutrients);
        this.notifyItemRangeInserted(0, nutrients.size() - 1);
    }

    /**
     * Delete nutrient
     *
     * @param position
     */
    public void deleteNutrient(int position) {
        this.nutrientDeletedPosition = position;
        this.mOnDeleteNutrient.deleteNutrient(this.nutrients.get(position).getId());
    }

    public void removeNutrient() {
        if (!nutrients.isEmpty()) {
            this.nutrients.remove(nutrientDeletedPosition);
            this.notifyItemRemoved(nutrientDeletedPosition);
        }
    }

    /**
     * Check if the nutrient is already on the list
     *
     * @param nutrientId Nutrient Id
     * @return -1 if it does not exist or nutrient list's position
     */
    public int existNutrient(String nutrientId) {
        if (!nutrients.isEmpty()) {
            for (int i = 0; i < nutrients.size(); i++) {
                if (nutrients.get(i).getId().equals(nutrientId)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Add nutrient to the nutrients list
     *
     * @param nutrient Nutrient Object
     */
    public void addNutrient(Nutrient nutrient) {
        this.nutrients.add(nutrient);
        notifyDataSetChanged();
    }

    /**
     * Update nutrient from the list
     *
     * @param nutrient Nutrient Object
     */
    public void updateNutrient(Nutrient nutrient, int nutrientPosition) {
        Nutrient nutrientFromList = nutrients.get(nutrientPosition);
        nutrientFromList.setName(nutrient.getName());
        nutrientFromList.setPh(nutrient.getPh());
        nutrientFromList.setNpk(nutrient.getNpk());
        nutrientFromList.setDescription(nutrient.getDescription());
        nutrientFromList.setImages(nutrient.getImages());
        nutrientFromList.setResourcesIds(nutrient.getResourcesIds());
        notifyItemChanged(nutrientPosition);
    }

    // inner class to hold a reference to each card_view of RecyclerView
    public class NutrientViewHolder extends RecyclerView.ViewHolder {

        int position;

        @Bind(R.id.nutrient_image)
        SimpleDraweeView mNutrientImage;

        @Bind(R.id.nutrient_name)
        TextView mNutrientName;

        @Bind(R.id.nutrient_npk)
        TextView npk;

        @Bind(R.id.nutrient_ph)
        TextView ph;

        @Bind(R.id.nutrient_delete_button_id)
        Button mDeleteButton;

        public NutrientViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
            v.setOnClickListener(v1 -> mOnNutrientSelected.showNutrientDetails(nutrients.get(position)));
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
