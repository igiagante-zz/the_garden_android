package com.example.igiagante.thegarden.home.gardens.presentation.delegates;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.adapter.delegate.AdapterDelegate;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeButton;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class AdapterDelegateButtonAddGarden implements AdapterDelegate<AdapterDelegateButtonAddGarden.ButtonAddGardenHolder, ViewTypeButton> {

    private Context mContext;
    private Session session;
    private OnGardenDialog onGardenDialog;
    private final LayoutInflater layoutInflater;

    public interface OnGardenDialog {
        void createGarden(Garden garden);
    }

    public AdapterDelegateButtonAddGarden(Context context, Session session, OnGardenDialog onGardenDialog) {
        this.mContext = context;
        this.session = session;
        this.onGardenDialog = onGardenDialog;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ButtonAddGardenHolder onCreateViewHolder(ViewGroup parent) {
        return new ButtonAddGardenHolder(parent, mContext);
    }

    @Override
    public void onBindViewHolder(ButtonAddGardenHolder holder, ViewTypeButton item) {

    }

    /**
     * Inner class to hold a reference to button `Add New Garden` of RecyclerView
     */
    class ButtonAddGardenHolder extends RecyclerView.ViewHolder {

        Button mButtonAddGarden;
        Context mContext;

        public ButtonAddGardenHolder(ViewGroup parent, Context context) {

            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_garden_button, parent, false));
            this.mContext = context;

            mButtonAddGarden = (Button) itemView.findViewById(R.id.add_garden_button_id);
            mButtonAddGarden.setOnClickListener(v -> showDialogAddGarden());
        }

        private void showDialogAddGarden(){
            View promptView = layoutInflater.inflate(R.layout.add_garden_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setView(promptView);

            final EditText editText = (EditText) promptView.findViewById(R.id.add_garden_dialog_enter_name);
            // setup a dialog window
            alertDialogBuilder
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Garden garden = new Garden();
                        garden.setUserId(session.getUser().getId());
                        garden.setName(editText.getText().toString());
                        onGardenDialog.createGarden(garden);})
                    .setNegativeButton("No", null);

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            alert.show();
        }
    }
}
