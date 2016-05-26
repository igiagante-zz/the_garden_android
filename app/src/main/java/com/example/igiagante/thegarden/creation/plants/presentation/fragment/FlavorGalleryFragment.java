package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.FlavorAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class FlavorGalleryFragment extends CreationBaseFragment {

    @Bind(R.id.recycler_view_flavors)
    RecyclerView mFlavors;

    private String [] fileNames = {"wood_flavor", "lemon_flavor", "skunk_flavor"};

    private  int[] resourcesIds = new int[] { R.drawable.skunk_flavor, R.drawable.lemon_flavor,
            R.drawable.wood_flavor };

    private FlavorAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.flavors_fragment, container, false);
        ButterKnife.bind(this, containerView);

        mFlavors.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        mFlavors.setLayoutManager(manager);

        mAdapter = new FlavorAdapter(getContext());
        mFlavors.setAdapter(mAdapter);

       // loadFlavorImages();

        mAdapter.setResourcesIds(resourcesIds);

        return containerView;
    }

    private void loadFlavorImages() {
        Uri uri0 = Uri.parse("android.resource://com.example.igiagante.thegarden/drawable/" + fileNames[0]);
        Uri uri1 = Uri.parse("android.resource://com.example.igiagante.thegarden/drawable/" + fileNames[1]);
        Uri uri2 = Uri.parse("android.resource://com.example.igiagante.thegarden/drawable/" + fileNames[2]);

        ArrayList<Uri> urls = new ArrayList<>();
        urls.add(uri0);
        urls.add(uri1);
        urls.add(uri2);

        mAdapter.setUrls(urls);
    }
}
