package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.FlavorAdapter;
import com.example.igiagante.thegarden.creation.plants.respository.sqlite.FlavorContract;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class FlavorGalleryFragment extends CreationBaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @Bind(R.id.recycler_view_flavors)
    RecyclerView mFlavors;

    private String [] fileNames = {"wood_flavor", "lemon_flavor", "skunk_flavor"};

    private  int[] resourcesIds = new int[] { R.drawable.skunk_flavor, R.drawable.lemon_flavor,
            R.drawable.wood_flavor };

    private static final int LOADER_FLAVORS = 1;

    private FlavorAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create adapter
        this.mAdapter = new FlavorAdapter(getContext());

        // start loader
        this.getLoaderManager().restartLoader(LOADER_FLAVORS, null, this);
    }

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

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle args)
    {
        switch (id)
        {
            case LOADER_FLAVORS:
                return new CursorLoader(getContext(), FlavorContract.FlavorEntry.CONTENT_URI, null, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data)
    {
        switch (loader.getId())
        {
            case LOADER_FLAVORS:

                this.mAdapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader)
    {
        switch (loader.getId())
        {
            case LOADER_FLAVORS:

                this.mAdapter.swapCursor(null);
                break;
        }
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
