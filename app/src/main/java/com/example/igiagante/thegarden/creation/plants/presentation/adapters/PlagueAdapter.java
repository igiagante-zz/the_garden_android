package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.PlagueHolder;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class PlagueAdapter extends RecyclerView.Adapter<PlagueAdapter.PlagueViewHolder> {

    private Context mContext;
    private final LayoutInflater layoutInflater;

    /**
     * A list with holders which contain the model and extra data
     */
    private ArrayList<PlagueHolder> plagues;

    public PlagueAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PlagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.plague_button, parent, false);
        return new PlagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlagueViewHolder holder, int position) {
        holder.tag.setText(plagues.get(position).getPlagueName());
    }

    @Override
    public int getItemCount() {
        return plagues != null ? plagues.size() : 0;
    }

    /**
     * Inner class to hold a reference to each plague of RecyclerView
     */
    public class PlagueViewHolder extends RecyclerView.ViewHolder {

        Button tag;
        int width, height;

        public PlagueViewHolder(View view) {
            super(view);
            tag = (Button) view.findViewById(R.id.button_plague);
            tag.setBackground(ContextCompat.getDrawable(mContext, R.drawable.plague_available_button));

            setWidthAndHeightFromButton(tag);

            tag.setOnClickListener(button -> {

                final int position = getAdapterPosition();
                PlagueHolder plagueHolder = plagues.get(position);
                plagueHolder.setSelected(!plagueHolder.isSelected());

                if(plagues.get(position).isSelected()) {
                    tag.setBackground(ContextCompat.getDrawable(mContext, R.drawable.plague_selected_button));
                    tag.setWidth(width);
                    tag.setHeight(height);
                } else {
                    tag.setBackground(ContextCompat.getDrawable(mContext, R.drawable.plague_available_button));
                }
            });
        }

        private void setWidthAndHeightFromButton(Button button) {
            width = button.getWidth();
            height = button.getHeight();
        }
    }

    public void setPlagues(ArrayList<PlagueHolder> plagues) {
        this.plagues = plagues;
        notifyDataSetChanged();
    }

    public ArrayList<PlagueHolder> getPlagues() {
        return plagues;
    }
}
