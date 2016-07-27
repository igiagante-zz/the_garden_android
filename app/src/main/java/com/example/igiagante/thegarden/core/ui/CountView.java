package com.example.igiagante.thegarden.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.igiagante.thegarden.R;

import java.text.DecimalFormat;

/**
 * @author igiagante on 6/5/16.
 */
public class CountView extends LinearLayout {

    protected Context mContext;

    protected int mDefaultValue;
    protected EditText mEditValue;

    public CountView(Context context) {
        super(context);
        inflate(getContext(), R.layout.count_view, this);
        init(context);
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.count_view, this);
        init(context);
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountView,
                0, 0);

        try {
            mDefaultValue = a.getInteger(R.styleable.CountView_setValue, 10);
        } finally {
            a.recycle();
        }

        init(context);
        setSaveEnabled(true);
    }

    protected void init(Context context) {

        mContext = context;

        inflate(mContext, R.layout.count_view, this);
        mEditValue = (EditText) findViewById(R.id.count_input);
        mEditValue.setText(String.valueOf(mDefaultValue));

        Button mButtonUp = (Button) findViewById(R.id.count_button_up);
        mButtonUp.setOnClickListener(view -> incrementValue());

        Button mButtonDown = (Button) findViewById(R.id.count_button_down);
        mButtonDown.setOnClickListener(view -> decrementValue());
    }

    /**
     * Set the edit value of the view with a int value
     * @param value value
     */
    public void setEditValue(int value) {
        if(mEditValue != null && value >= 0) {
            mEditValue.setText(String.valueOf(value));
        }
    }

    /**
     * Get the edit value from the view
     */
    public int getEditValue() {
        int count = 0;
        String value = mEditValue.getText().toString();
        if(!TextUtils.isEmpty(value)) {
            count = Integer.parseInt(value);
        }
        return count;
    }

    /**
     * Increment value from edit text
     */
    private void incrementValue() {
        if(mEditValue != null) {
            int value = getEditValue();
            value += 1;
            setEditValue(value);
        }
    }

    /**
     * Decrement value from edit text
     */
    private void decrementValue() {
        if(mEditValue != null) {
            int value = getEditValue();
            value -= 1;
            setEditValue(value);
        }
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
