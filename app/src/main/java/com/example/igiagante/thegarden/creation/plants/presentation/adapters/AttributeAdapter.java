package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.ui.RecyclerViewItemClickListener;
import com.example.igiagante.thegarden.core.ui.TagView;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.AttributeHolder;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 6/2/16.
 */
public class AttributeAdapter extends RecyclerView.Adapter<AttributeViewHolder> implements RecyclerViewItemClickListener.OnRecyclerViewItemClickListener {

    private static final String EFFECTS = "effects";
    private static final String MEDICINAL = "medicinal";
    private static final String SYMPTOMS = "symptoms";

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

        AttributeHolder attributeHolder = attributeHolders.get(position);

        if(holder instanceof AttributeAvailableViewHolder) {
            Button tag = ((AttributeAvailableViewHolder) holder).tag;
            tag.setText(attributeHolder.getTagName());
            setTypeOfAttributeBackground(attributeHolder, tag);
        } else {
            TagView tagView = ((AttributeSelectedViewHolder) holder).tagView;

            tagView.setTagName(attributeHolder.getTagName());
            setTypeOfAttributeBackground(attributeHolder, tagView.getContainerButton());
        }
    }

    /**
     * Depend on the type of the attribute, the button will have an specific color border
     * @param holder attribute holder that has a reference to the attribute model
     * @param tag button tag
     */
    private void setTypeOfAttributeBackground(AttributeHolder holder, View tag) {

        if (holder.getType().equals(EFFECTS)) {
            tag.setBackground(ContextCompat.getDrawable(mContext, R.drawable.attributes_rectangle_effects));
        } else if (holder.getType().equals(MEDICINAL)) {
            tag.setBackground(ContextCompat.getDrawable(mContext, R.drawable.attributes_rectangle_medicinal));
        } else if (holder.getType().equals(SYMPTOMS)) {
            tag.setBackground(ContextCompat.getDrawable(mContext, R.drawable.attributes_rectangle_symptoms));
        }
    }

    @Override
    public int getItemCount() {
        return attributeHolders != null ? attributeHolders.size() : 0;
    }

    /**
     * Add a tag to the list
     * @param attributeHolder Attribute Holder
     */
    public void addTag(AttributeHolder attributeHolder){
        final int position = attributeHolders.size();
        attributeHolders.add(attributeHolder);
        notifyItemInserted(position);
    }

    @Override
    public void onRecyclerViewItemClick(@NonNull RecyclerView parent, @NonNull View view, int adapterPosition, long id) {
        int level = ((TagView)view).getLevel();
        AttributeHolder attributeHolder = attributeHolders.get(adapterPosition);
        attributeHolder.setPercentage(level);
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

    public void setAttributeHolders(ArrayList<AttributeHolder> attributeHolders) {
        this.attributeHolders = attributeHolders;
        notifyDataSetChanged();
    }

    public ArrayList<AttributeHolder> getAttributeHolders() {
        return attributeHolders;
    }
}
