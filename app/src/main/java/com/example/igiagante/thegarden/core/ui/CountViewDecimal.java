package com.example.igiagante.thegarden.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.igiagante.thegarden.R;

import java.text.DecimalFormat;

/**
 * @author Ignacio Giagante, on 6/11/16.
 */
public class CountViewDecimal extends LinearLayout {

    private float mDefaultValue;
    private EditText mEditValue;
    private String hint;

    public CountViewDecimal(Context context) {
        super(context);
        inflate(getContext(), R.layout.count_view_decimal, this);
        init(context);
    }

    public CountViewDecimal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.count_view_decimal, this);
        init(context);
    }

    public CountViewDecimal(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountViewDecimal,
                0, 0);

        try {
            mDefaultValue = a.getFloat(R.styleable.CountViewDecimal_setValueDecimal, 6);
            hint = a.getString(R.styleable.CountViewDecimal_setDecimalHint);
        } finally {
            a.recycle();
        }

        init(context);
        setSaveEnabled(true);
    }

    private void init(Context context) {

        inflate(context, R.layout.count_view_decimal, this);
        mEditValue = (EditText) findViewById(R.id.count_input);
        mEditValue.setText(formatFloat(mDefaultValue));

        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.input_wrap_decimal);
        textInputLayout.setHint(hint);

        Button mButtonUp = (Button) findViewById(R.id.count_button_up);
        mButtonUp.setOnClickListener(view -> incrementValue());

        Button mButtonDown = (Button) findViewById(R.id.count_button_down);
        mButtonDown.setOnClickListener(view -> decrementValue());
    }

    /**
     * Set the edit value of the view with a float value
     *
     * @param value value
     */
    public void setEditValue(float value) {
        if (mEditValue != null && value >= 0) {
            mEditValue.setText(formatFloat(value));
        }
    }

    /**
     * Get the edit value from the view
     */
    public float getEditValue() {
        float count = 0;
        String value = mEditValue.getText().toString();
        if (!TextUtils.isEmpty(value)) {
            count = Float.parseFloat(value);
        }
        return count;
    }

    /**
     * Increment value from edit text
     */
    private void incrementValue() {
        if (mEditValue != null) {
            float value = getEditValue();
            value += 0.1;
            setEditValue(value);
        }
    }

    /**
     * Decrement value from edit text
     */
    private void decrementValue() {
        if (mEditValue != null) {
            float value = getEditValue();
            value -= 0.1;
            setEditValue(value);
        }
    }

    private String formatFloat(float value) {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setMaximumFractionDigits(2);
        return df.format(value);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.childrenStates = new SparseArray();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).saveHierarchyState(ss.childrenStates);
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    static class SavedState extends BaseSavedState {
        SparseArray childrenStates;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in, ClassLoader classLoader) {
            super(in);
            childrenStates = in.readSparseArray(classLoader);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeSparseArray(childrenStates);
        }

        public static final ClassLoaderCreator<SavedState> CREATOR
                = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
