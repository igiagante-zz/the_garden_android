package com.example.igiagante.thegarden.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;

import com.example.igiagante.thegarden.R;

/**
 * @author Ignacio Giagante, on 2/6/16.
 */
public class FangButton extends LinearLayout {

    public interface FangButtonListener {
        void onClick(FangButton v);
    }

    private int fangPower;
    private boolean checked;

    private FangButtonListener fangButtonListener;

    final OnClickListener innerOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            checked = !checked;
            if (checked) {
                v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.fang_button_pressed));
            } else {
                v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.fang_button));
            }
            if (outerClickListener != null) {
                outerClickListener.onClick(v);
            }
            if (fangButtonListener != null) {
                fangButtonListener.onClick((FangButton) v);
            }
        }
    };

    private OnClickListener outerClickListener;

    private SeekBar fangSeekBar;
    private RatingBar fangRatingBar;

    public FangButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public FangButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.fang_button_layout, this);
        this.setOrientation(VERTICAL);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FangButton,
                0, 0);

        try {
            fangPower = a.getInteger(R.styleable.FangButton_fangPower, 0);
            checked = a.getBoolean(R.styleable.FangButton_checked, false);
        } finally {
            a.recycle();
        }


        fangSeekBar = (SeekBar) findViewById(R.id.fangButtonSeekBar);
        fangRatingBar = (RatingBar) findViewById(R.id.fangButtonRateBar);

        if (checked) {
            ContextCompat.getDrawable(FangButton.this.getContext(), R.drawable.fang_button_pressed);
        } else {
            this.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.fang_button));
        }
        fangRatingBar.setProgress(fangPower);
        fangSeekBar.setProgress(fangPower);

        fangSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fangPower = progress;
                fangRatingBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fangRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                fangPower = (int) rating;
                fangSeekBar.setProgress((int) rating);
            }
        });

        this.setOnClickListener(innerOnClickListener);

    }

    public int getFangPower() {
        return fangPower;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setFangButtonListener(FangButtonListener fangButtonListener) {
        this.fangButtonListener = fangButtonListener;
    }
}
