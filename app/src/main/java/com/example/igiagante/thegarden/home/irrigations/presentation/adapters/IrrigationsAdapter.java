package com.example.igiagante.thegarden.home.irrigations.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public IrrigationsAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.irrigations = new ArrayList<>();
    }

    @Override
    public IrrigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.card_view_irrigation, parent, false);
        return new IrrigationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IrrigationViewHolder holder, int position) {
        Irrigation irrigation = irrigations.get(position);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        if(irrigation != null) {
            holder.water.setText(mContext.getString(R.string.dose_water, irrigation.getDose().getWater()));
            holder.ph.setText(mContext.getString(R.string.dose_ph, irrigation.getDose().getPh()));
            holder.ec.setText(mContext.getString(R.string.dose_ec, irrigation.getDose().getEc()));

            Date date = irrigation.getIrrigationDate();

            if(date != null) {
                holder.date.setText(dateFormatter.format(date));
            }

            holder.quantity.setText(String.valueOf(irrigation.getQuantity()));

            StringBuilder builder = new StringBuilder();

            for(Nutrient nutrient : irrigation.getDose().getNutrients()) {
                String name = nutrient.getName();
                float quantityUsed = nutrient.getQuantityUsed();
                builder.append(name + " " + String.valueOf(quantityUsed));
            }
            holder.nutrients.setText(builder.toString());
        }
    }

    @Override
    public int getItemCount() {
        return irrigations != null ? irrigations.size() : 0;
    }

    /**
     * Set irrigations
     * @param irrigations List of irrigations
     */
    public void setIrrigations(ArrayList<Irrigation> irrigations) {
        this.irrigations = new ArrayList<>(irrigations);
        this.notifyItemRangeInserted(0, irrigations.size() - 1);
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

        public IrrigationViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
