package com.example.igiagante.thegarden.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.igiagante.thegarden.R;

import java.text.DecimalFormat;

/**
 * @author igiagante on 6/5/16.
 */
public class CountView extends LinearLayout {

    private Context mContext;

    private float defaultValue;
    private EditText mEditValue;

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountView,
                0, 0);

        try {
            defaultValue = a.getFloat(R.styleable.CountView_setValue, 6);
        } finally {
            a.recycle();
        }

        init(context);
    }

    private void init(Context context) {

        mContext = context;

        inflate(mContext, R.layout.count_view, this);
        mEditValue = (EditText) findViewById(R.id.count_input);
        mEditValue.setText(formatFloat(defaultValue));

        Button mButtonUp = (Button) findViewById(R.id.count_button_up);
        mButtonUp.setOnClickListener(view -> incrementValue());

        Button mButtonDown = (Button) findViewById(R.id.count_button_down);
        mButtonDown.setOnClickListener(view -> decrementValue());
    }

    /**
     * Set the edit value of the view with a float value
     * @param value value
     */
    public void setEditValue(float value) {
        if(mEditValue != null) {
            mEditValue.setText(formatFloat(value));
        }
    }

    /**
     * Get the edit value from the view
     */
    public String getEditValue() {
        float count = 0;
        String value = mEditValue.getText().toString();
        if(!TextUtils.isEmpty(value)) {
            count = Float.parseFloat(value);
        }
        return formatFloat(count);
    }

    /**
     * Increment value from edit text
     */
    private void incrementValue() {
        if(mEditValue != null) {
            float value = Float.parseFloat(getEditValue());
            value += 0.1;
            setEditValue(value);
        }
    }

    /**
     * Decrement value from edit text
     */
    private void decrementValue() {
        if(mEditValue != null) {
            float value = Float.parseFloat(getEditValue());
            value -= 0.1;
            setEditValue(value);
        }
    }

    private String formatFloat(float value) {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }
}
