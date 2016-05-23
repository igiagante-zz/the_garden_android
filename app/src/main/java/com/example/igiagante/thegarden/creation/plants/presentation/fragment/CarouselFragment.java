package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;


/**
 * @author Ignacio Giagante, on 23/5/16.
 */
public class CarouselFragment extends BaseFragment {

    private static final String IMAGE_URL_KEY = "IMAGE_URL";

    private String mImageUrl;
    private SimpleDraweeView mImage;

    public static CarouselFragment newInstance(String imageUrl) {
        CarouselFragment myFragment = new CarouselFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_URL_KEY, imageUrl);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.carousel_fragment, container, false);

        mImage = (SimpleDraweeView) getActivity().findViewById(R.id.coursel_image_id);

        if(savedInstanceState != null) {
            mImageUrl = savedInstanceState.getString(IMAGE_URL_KEY);
            mImage.setImageURI(Uri.fromFile(new File(mImageUrl)));
        }

        return  containerView;
    }
}
