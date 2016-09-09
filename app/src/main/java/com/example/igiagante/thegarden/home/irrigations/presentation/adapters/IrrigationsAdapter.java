package com.example.igiagante.thegarden.home.irrigations.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 26/7/16.
 */
public class IrrigationsAdapter extends RecyclerView.Adapter<IrrigationsAdapter.IrrigationViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<Irrigation> irrigations;

    private OnIrrigationSelected mOnIrrigationSelected;
    private OnDeleteIrrigation mOnDeleteIrrigation;

    private int irrigationDeletedPosition;

    public interface OnIrrigationSelected {
        void showIrrigationDetails(Irrigation irrigation);
    }

    public interface OnDeleteIrrigation {
        void showDeleteIrrigationDialog(int position);

        void deleteIrrigation(String irrigationId);
    }

    public IrrigationsAdapter(Context context, OnIrrigationSelected OnIrrigationSelected, OnDeleteIrrigation OnDeleteIrrigation) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.irrigations = new ArrayList<>();
        this.mOnIrrigationSelected = OnIrrigationSelected;
        this.mOnDeleteIrrigation = OnDeleteIrrigation;
    }

    @Override
    public IrrigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.card_view_irrigation, parent, false);
        return new IrrigationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IrrigationViewHolder holder, int position) {
        Irrigation irrigation = irrigations.get(position);
        holder.setPosition(position);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("d MMM", Locale.US);

        if (irrigation != null) {
            holder.water.setText(mContext.getString(R.string.dose_water, irrigation.getDose().getWater()));
            holder.ph.setText(mContext.getString(R.string.dose_ph, irrigation.getDose().getPh()));
            holder.ec.setText(mContext.getString(R.string.dose_ec, irrigation.getDose().getEc()));

            Date date = irrigation.getIrrigationDate();

            if (date != null) {
                holder.date.setText(dateFormatter.format(date));
            }

            String quantity = mContext.getString(R.string.irrigation_water, getQuantity(irrigation.getQuantity()));
            holder.quantity.setText(quantity);

            StringBuilder builder = new StringBuilder();

            for (Nutrient nutrient : irrigation.getDose().getNutrients()) {
                String name = nutrient.getName();
                float quantityUsed = nutrient.getQuantityUsed();
                builder.append(name + " " + getQuantity(quantityUsed));
                builder.append("   ");
            }
            String nutrients = mContext.getString(R.string.dose_nutrients, builder.toString());
            holder.nutrients.setText(nutrients);
        }

        holder.mDeleteButton.setOnClickListener(v -> mOnDeleteIrrigation.showDeleteIrrigationDialog(holder.getAdapterPosition()));

    }

    /**
     * Retrieve integer part in case there are not any decimal
     *
     * @param quantity how much water is going to receive each plant
     * @return quantity
     */
    private String getQuantity(float quantity) {
        String tempQuantity = String.valueOf(quantity);
        String[] parts = tempQuantity.split("\\.");
        if (parts[1] != null && Integer.parseInt(parts[1]) == 0) {
            tempQuantity = parts[0];
        }
        return tempQuantity;
    }

    @Override
    public int getItemCount() {
        return irrigations != null ? irrigations.size() : 0;
    }

    /**
     * Set irrigations
     *
     * @param irrigations List of irrigations
     */
    public void setIrrigations(ArrayList<Irrigation> irrigations) {
        if (!irrigations.isEmpty()) {
            this.irrigations = new ArrayList<>(irrigations);
        } else {
            this.irrigations = new ArrayList<>();
        }
        this.notifyDataSetChanged();
    }

    /**
     * Add irrigation to the irrigations list
     *
     * @param irrigation Irrigation Object
     */
    public void addIrrigation(Irrigation irrigation) {
        this.irrigations.add(irrigation);
        notifyDataSetChanged();
    }

    /**
     * Inform that an irrigation should be deleted
     *
     * @param position Position of irrigation
     */
    public void deleteIrrigation(int position) {
        this.irrigationDeletedPosition = position;
        this.mOnDeleteIrrigation.deleteIrrigation(this.irrigations.get(position).getId());
    }

    /**
     * An irrigation was deleted, so it should be removed from the list
     */
    public void removeIrrigation() {
        if (!irrigations.isEmpty()) {
            this.irrigations.remove(irrigationDeletedPosition);
            this.notifyDataSetChanged();
        }
    }

    // inner class to hold a reference to each card_view of RecyclerView
    public class IrrigationViewHolder extends RecyclerView.ViewHolder {

        int position;

        @Bind(R.id.dose_water_id)
        TextView water;

        @Bind(R.id.dose_ph_id)
        TextView ph;

        @Bind(R.id.dose_ec_id)
        TextView ec;

        @Bind(R.id.card_view_irrigation_date_id)
        TextView date;

        @Bind(R.id.card_view_irrigation_quantity_id)
        TextView quantity;

        @Bind(R.id.dose_list_of_nutrients)
        TextView nutrients;

        @Bind(R.id.irrigation_delete_button_id)
        Button mDeleteButton;

        public IrrigationViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);

            v.setOnClickListener(v1 -> mOnIrrigationSelected.showIrrigationDetails(irrigations.get(position)));
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
