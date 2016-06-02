package com.example.igiagante.thegarden.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;

/**
 * @author Ignacio Giagante, on 2/6/16.
 */
public class TagView extends LinearLayout {
    private final int MAX_VALUE = 100;
    private final int MIN_VALUE = 0;
    private final int SHOW_TIME = 750;

    private final int DELTA = 10;

    private int level;
    private String tagName;
    private Button plusButton, minusButton;
    private TextView tagNameText, tagProgressText;
    private ProgressBar tagPgBar;

    final OnClickListener tagButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            tagProgressText.setVisibility(VISIBLE);
            switch (v.getId()){
                case R.id.tagButtonMinus:
                    if((level)>=0) {
                        level -= DELTA;
                    }
                    break;
                case R.id.tagButtonPlus:
                    if((level)<=MAX_VALUE) {
                        level += DELTA;
                    }
                    break;

            }
            tagPgBar.setProgress(level);
            tagProgressText.setText(level+" %");
            tagProgressText.setVisibility(VISIBLE);
            removeCallbacks(hideProgressTextIndicator); // para cuando apreto muchas veces seguidas
            postDelayed(hideProgressTextIndicator,SHOW_TIME);
        }
    };

    final Runnable hideProgressTextIndicator = new Runnable() {
        @Override
        public void run() {
            tagProgressText.setVisibility(INVISIBLE);
        }
    };

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(),R.layout.attributes_custom_button_tag,this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TagView,
                0, 0);

        try {
            level = a.getInteger(R.styleable.TagView_level,0);
            tagName = a.getString(R.styleable.TagView_tagName);
        } finally {
            a.recycle();
        }
        init();
    }

    public TagView(Context context) {
        super(context);
        inflate(getContext(),R.layout.attributes_custom_button_tag,this);
        init();
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(),R.layout.attributes_custom_button_tag,this);
        init();


    }

    public void init(){
        this.setOrientation(VERTICAL);
        plusButton = (Button) findViewById(R.id.tagButtonPlus);
        minusButton = (Button) findViewById(R.id.tagButtonMinus);
        tagNameText = (TextView)  findViewById(R.id.tagNameText);
        tagProgressText = (TextView) findViewById(R.id.tagProgressText);
        tagPgBar = (ProgressBar) findViewById(R.id.tagPgb);
        if(tagName!=null){
            tagNameText.setText(tagName);
        }
        tagPgBar.setMax(MAX_VALUE);
        tagPgBar.setProgress(level);
        plusButton.setOnClickListener(tagButtonListener);
        minusButton.setOnClickListener(tagButtonListener);
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
        tagNameText.setText(tagName);
    }

    public int getLevel() {
        return level;
    }

    public String getTagName() {
        return tagName;
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.level = savedState.level;
    }


    // se llama automaticamente siemrpe y cuando la visa tenga un id
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState =  super.onSaveInstanceState();
        SavedState newSavedState = new SavedState(superState);
        newSavedState.level = this.level;
        return newSavedState;
    }

    //////////////PARCEL RELATED FEATURES///////////////////////////////

    private static class SavedState extends BaseSavedState{
        int level;

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            readFromParcel(source);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(level);
        }

        private void readFromParcel(Parcel in){
            this.level = in.readInt();
        }

    }
}
