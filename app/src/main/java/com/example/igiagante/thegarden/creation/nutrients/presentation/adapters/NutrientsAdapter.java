package com.example.igiagante.thegarden.creation.nutrients.presentation.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

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

    public interface OnNutrientSelected {
        void showNutrientDetails(Nutrient nutrient);
    }

    public NutrientsAdapter(Context context, OnNutrientSelected onNutrientSelected) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.nutrients = new ArrayList<>();
        this.mOnNutrientSelected = onNutrientSelected;
    }

    @Override
    public NutrientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.card_view_nutrient, parent, false);
        return new NutrientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NutrientViewHolder holder, int position) {
        final Nutrient nutrient = this.nutrients.get(position);

        Image mainImage = nutrient.getImages().get(0);

        if (mainImage != null) {
            String thumbnailUrl = mainImage.getThumbnailUrl();
            if(mainImage.getId() == null) {
                holder.mNutrientImage.setImageURI(Uri.fromFile(new File(thumbnailUrl)));
            } else {
                holder.mNutrientImage.setImageURI(Uri.parse(thumbnailUrl));
            }
        }

        holder.setPosition(position);
        holder.mNutrientName.setText(nutrient.getName());
        holder.npk.setText(nutrient.getNpk());
        holder.ph.setText(String.valueOf(nutrient.getPh()));
    }

    @Override
    public int getItemCount() {
        return (this.nutrients != null) ? this.nutrients.size() : 0;
    }

    public void setNutrients(ArrayList<Nutrient> nutrients) {
        this.nutrients = nutrients;
        this.notifyDataSetChanged();
    }

    /**
     * Add nutrient to the nutrients list
     * @param nutrient Nutrient Object
     */
    public void addNutrient(Nutrient nutrient){
        this.nutrients.add(nutrient);
        notifyDataSetChanged();
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
