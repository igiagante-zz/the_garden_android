package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.ui.TagView;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.AttributeHolder;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 6/2/16.
 */
public class AttributeAdapter extends RecyclerView.Adapter<AttributeViewHolder> {

    private Context mContext;
    private final LayoutInflater layoutInflater;

    /**
     * List of attribute holders. Each holder contains a reference to the model.
     * See {@link com.example.igiagante.thegarden.core.domain.entity.Attribute}
     */
    private ArrayList<AttributeHolder> attributeHolders = new ArrayList<>();

    public interface TagActionListener{
        void onTagClicked(AttributeHolder attributeHolder);
    }

    private TagActionListener tagActionListener;

    public AttributeAdapter(Context context, TagActionListener tagActionListener) {
        this.mContext = context;
        this.tagActionListener = tagActionListener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        return attributeHolders.get(position).isSelected() ? 1 : 0 ;
    }

    @Override
    public AttributeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if(viewType == 0) {
            view = layoutInflater.inflate(R.layout.attributes_button_tag, parent, false);
            return new AttributeAvailableViewHolder(view);
        } else {
            view = layoutInflater.inflate(R.layout.tag_view_item, parent, false);
            return new AttributeSelectedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(AttributeViewHolder holder, int position) {
        if(holder instanceof AttributeAvailableViewHolder) {
            ((AttributeAvailableViewHolder)holder).tag.setText(attributeHolders.get(position).getTagName());
        } else {
            ((AttributeSelectedViewHolder)holder).tagView.setTagName(attributeHolders.get(position).getTagName());
        }
    }

    @Override
    public int getItemCount() {
        return attributeHolders != null ? attributeHolders.size() : 0;
    }

    public void addTag(AttributeHolder attributeHolder){
        final int position = attributeHolders.size();
        attributeHolders.add(attributeHolder);
        notifyItemInserted(position);
    }

    /**
     * Inner class to hold a reference to each attribute of RecyclerView
     */
    public class AttributeAvailableViewHolder extends AttributeViewHolder {

        Button tag;

        final View.OnLongClickListener tagLongClickedLister = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final int position = getAdapterPosition();
                AttributeHolder pressedTag = attributeHolders.get(position);
                pressedTag.setSelected(!pressedTag.isSelected());
                attributeHolders.remove(position);
                notifyItemRemoved(position);
                tagActionListener.onTagClicked(pressedTag);
                return true;
            }
        };

        public AttributeAvailableViewHolder(View flavorView) {
            super(flavorView);
            tag = (Button) flavorView.findViewById(R.id.button_available_attribute);
            tag.setOnLongClickListener(tagLongClickedLister);
        }
    }

    /**
     * Inner class to hold a reference to each attribute of RecyclerView
     */
    public class AttributeSelectedViewHolder extends AttributeViewHolder {

        TagView tagView;

        final View.OnLongClickListener tagLongClickedLister = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final int position = getAdapterPosition();
                AttributeHolder pressedTag = attributeHolders.get(position);
                pressedTag.setSelected(!pressedTag.isSelected());
                attributeHolders.remove(position);
                notifyItemRemoved(position);
                tagActionListener.onTagClicked(pressedTag);
                return true;
            }
        };

        public AttributeSelectedViewHolder(View flavorView) {
            super(flavorView);
            tagView = (TagView) flavorView.findViewById(R.id.tag_item_id);
            tagView.setOnLongClickListener(tagLongClickedLister);
        }
    }

    public void setAttributeHolders(ArrayList<AttributeHolder> attributeHolders) {
        this.attributeHolders = attributeHolders;
        notifyDataSetChanged();
    }
}
