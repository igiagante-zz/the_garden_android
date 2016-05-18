package com.example.igiagante.thegarden.creation.plants.presentation.delegates;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.creation.plants.presentation.GalleryAdapter;

/**
 * @author Ignacio Giagante, on 18/5/16.
 */
public class AdapterDelegateButton implements AdapterDelegate<AdapterDelegateButton.ButtonAddImageHolder, ViewTypeButton> {

    private Context mContext;
    private final GalleryAdapter.OnExecutePickerImage mPicker;

    public AdapterDelegateButton(Context context, GalleryAdapter.OnExecutePickerImage picker) {
        this.mContext = context;
        this.mPicker = picker;
    }

    @Override
    public ButtonAddImageHolder onCreateViewHolder(ViewGroup parent) {
        return new ButtonAddImageHolder(parent, mContext);
    }

    @Override
    public void onBindViewHolder(ButtonAddImageHolder holder, ViewTypeButton item) {

    }

    class ButtonAddImageHolder extends RecyclerView.ViewHolder {

        Button mButtonAddImage;
        Context mContext;

        public ButtonAddImageHolder(ViewGroup parent, Context context) {

            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_first_time_image_button_view, parent, false));
            this.mContext = context;

            mButtonAddImage = (Button) itemView.findViewById(R.id.add_image_button_id);
            mButtonAddImage.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.button_add_image, null));

            mButtonAddImage.setOnClickListener(view -> mPicker.pickImages());
        }
    }
}
