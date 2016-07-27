package com.example.igiagante.thegarden.home.irrigations.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.ui.CounterView;
import com.example.igiagante.thegarden.home.irrigations.presentation.holders.NutrientHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 21/7/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<NutrientHolder> mNutrients;
    private Context mContext;
    private LayoutInflater layoutInflater;

    private String[] nameOfGroup = {"Nutrients"};

    public ExpandableListAdapter(@NonNull Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setNutrients(List<NutrientHolder> nutrients) {
        this.mNutrients = new ArrayList<>(nutrients);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mNutrients != null ? mNutrients.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return nameOfGroup[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mNutrients.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.row_header_nutrients_expandable_list, null);
        }

        ImageView indicator = (ImageView) convertView.findViewById(R.id.arrow_indicator_id);

        if (isExpanded) {
            indicator.setImageResource(R.drawable.indicator_arrow_up);
        } else {
            indicator.setImageResource(R.drawable.indicator_arrow_bottom);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final NutrientHolder nutrient = (NutrientHolder) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.row_nutrients_expandable_list, null);
        }

        TextView nameOfNutrient = (TextView) convertView.findViewById(R.id.expandable_list_name_of_nutrient);
        nameOfNutrient.setText(nutrient.getName());

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.expandable_list_item_checkbox_id);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        nutrient.setSelected(true);
                    } else {
                        nutrient.setSelected(false);
                    }
                }
        );

        CounterView quantity = (CounterView) convertView.findViewById(R.id.expandable_list_quantity);
        quantity.setCountViewListener(value -> nutrient.setQuantity(value));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * Get selected nutrients in order to be used in the dose
     * @return nutrients
     */
    public ArrayList<Nutrient> getNutrientsSelected() {
        ArrayList<Nutrient> nutrients = new ArrayList<>();

        for (NutrientHolder nutrientHolder : mNutrients) {
            if(nutrientHolder.isSelected()) {
                Nutrient nutrient = nutrientHolder.getModel();
                nutrients.add(nutrient);
            }
        }
        return nutrients;
    }
}
