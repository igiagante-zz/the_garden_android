package com.example.igiagante.thegarden.home.gardens.presentation.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.adapter.delegate.AdapterDelegate;
import com.example.igiagante.thegarden.home.gardens.presentation.viewTypes.ViewTypeGarden;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class AdapterDelegateGarden implements AdapterDelegate<AdapterDelegateGarden.TextHolder, ViewTypeGarden> {

    private OnClickGardenListener mOnClickGardenListener;

    public interface OnClickGardenListener {
        void showGardenDialog(int position);

        void getGarden(String gardenId);
    }

    public AdapterDelegateGarden(OnClickGardenListener OnClickGardenListener) {
        this.mOnClickGardenListener = OnClickGardenListener;
    }

    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_garden, parent, false);
        return new TextHolder(v);
    }

    @Override
    public void onBindViewHolder(TextHolder holder, ViewTypeGarden item) {
        holder.setGardenName(item.getName());
        holder.itemView.setOnLongClickListener(v -> {
            mOnClickGardenListener.showGardenDialog(holder.getAdapterPosition());
            return false;
        });
        holder.itemView.setOnClickListener(v -> mOnClickGardenListener.getGarden(item.getId()));
    }

    /**
     * Inner class to hold the reference to each recycle view item
     */
    public class TextHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView mGardenName;

        public TextHolder(View v) {
            super(v);
            mGardenName = (TextView) v.findViewById(R.id.garden_id);
            v.setOnCreateContextMenuListener(this);
        }

        public void setGardenName(String name) {
            mGardenName.setText(name);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.setHeaderTitle("Select The Action");
            menu.add(0, R.id.edit_plant, 0, "Edit Garden");//groupId, itemId, order, title
            menu.add(0, R.id.delete_plant, 0, "Delete Garden");
        }
    }
}
