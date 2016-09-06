package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.CarouselActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;


/**
 * Fragment used to show the images through a carousel
 *
 * @author Ignacio Giagante, on 23/5/16.
 */
public class CarouselFragment extends BaseFragment {

    private static final String IMAGE_URL_KEY = "IMAGE_URL";
    private static final String IMAGE_POSITION_KEY = "IMAGE_POSITION";

    private String mImageUrl;
    private int mPosition;
    private OnDeleteImageInCarousel mOnDeleteImageInCarousel;

    public interface OnDeleteImageInCarousel {
        void deleteImageInCarousel(int position);
    }

    public static CarouselFragment newInstance(String imageUrl, int position) {
        CarouselFragment myFragment = new CarouselFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_URL_KEY, imageUrl);
        args.putInt(IMAGE_POSITION_KEY, position);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mImageUrl = args.getString(IMAGE_URL_KEY);
            mPosition = args.getInt(IMAGE_POSITION_KEY);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.carousel_fragment, container, false);

        SimpleDraweeView mImage = (SimpleDraweeView) containerView.findViewById(R.id.carousel_image_id);
        if (mImageUrl.contains("http")) {
            mImage.setImageURI(Uri.parse(mImageUrl));
        } else {
            mImage.setImageURI(Uri.fromFile(new File(mImageUrl)));
        }

        if (getActivity() instanceof CarouselActivity) {
            FrameLayout frameLayout = (FrameLayout) containerView.findViewById(R.id.carousel_container_id);
            frameLayout.setBackground(ContextCompat.getDrawable(getContext(), R.color.background));

            Button button = (Button) containerView.findViewById(R.id.carousel_delete_button_id);
            button.setOnClickListener(view -> mOnDeleteImageInCarousel.deleteImageInCarousel(mPosition));
        }

        return containerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnDeleteImageInCarousel = (OnDeleteImageInCarousel) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.getClass().getName() + " should be implement OnDeleteImageInCarousel");
        }
    }
}
