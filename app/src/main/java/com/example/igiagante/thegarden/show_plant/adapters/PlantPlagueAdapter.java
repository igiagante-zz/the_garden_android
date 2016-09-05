package com.example.igiagante.thegarden.show_plant.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 14/6/16.
 */
public class PlantPlagueAdapter extends RecyclerView.Adapter<PlantPlagueAdapter.PlantPlagueViewHolder> {

    private List<Plague> mPlagues;
    private final LayoutInflater layoutInflater;

    public PlantPlagueAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PlantPlagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.image_plague, parent, false);
        return new PlantPlagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantPlagueViewHolder holder, int position) {
        holder.setImageUri(mPlagues.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return mPlagues != null ? mPlagues.size() : 0;
    }

    public void setPlagues(List<Plague> mPlagues) {
        this.mPlagues = mPlagues;
        notifyDataSetChanged();
    }

    class PlantPlagueViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mPlagueImage;

        public PlantPlagueViewHolder(View itemView) {
            super(itemView);
            mPlagueImage = (SimpleDraweeView) itemView.findViewById(R.id.image_plague_id);
        }

        public void setImageUri(String uri) {
            mPlagueImage.setImageURI(Uri.parse(uri));
        }
    }

}
