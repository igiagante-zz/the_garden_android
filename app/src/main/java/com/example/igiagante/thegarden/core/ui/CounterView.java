package com.example.igiagante.thegarden.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;

/**
 * @author Ignacio Giagante, on 21/7/16.
 */
public class CounterView extends LinearLayout {

    private int mDefaultValue;
    private TextView mTextViewValue;

    private CountViewListener mCountViewListener;

    public CounterView(Context context) {
        super(context);
        inflate(getContext(), R.layout.counter_view, this);
        init(context);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.counter_view, this);
        init(context);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CounterView,
                0, 0);

        try {
            mDefaultValue = a.getInteger(R.styleable.CounterView_setCount, 10);
        } finally {
            a.recycle();
        }

        init(context);
        setSaveEnabled(true);
    }

    private void init(Context context) {

        inflate(context, R.layout.counter_view, this);
        mTextViewValue = (TextView) findViewById(R.id.counter_input_id);
        mTextViewValue.setText(String.valueOf(mDefaultValue));

        Button mButtonUp = (Button) findViewById(R.id.counter_button_up_id);
        mButtonUp.setOnClickListener(view -> incrementValue());

        Button mButtonDown = (Button) findViewById(R.id.counter_button_down_id);
        mButtonDown.setOnClickListener(view -> decrementValue());
    }

    /**
     * Set the edit value of the view with a int value
     *
     * @param value value
     */
    public void setEditValue(int value) {
        if (mTextViewValue != null && value >= 0) {
            mTextViewValue.setText(String.valueOf(value));
            mCountViewListener.onCountViewChanged(value);
        }
    }

    /**
     * Get the edit value from the view
     */
    public int getEditValue() {
        int count = 0;
        String value = mTextViewValue.getText().toString();
        if (!TextUtils.isEmpty(value)) {
            count = Integer.parseInt(value);
        }
        return count;
    }

    /**
     * Increment value from edit text
     */
    private void incrementValue() {
        if (mTextViewValue != null) {
            int value = getEditValue();
            value += 1;
            setEditValue(value);
        }
    }

    /**
     * Decrement value from edit text
     */
    private void decrementValue() {
        if (mTextViewValue != null) {
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

    public void setCountViewListener(CountViewListener mCountViewListener) {
        this.mCountViewListener = mCountViewListener;
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

