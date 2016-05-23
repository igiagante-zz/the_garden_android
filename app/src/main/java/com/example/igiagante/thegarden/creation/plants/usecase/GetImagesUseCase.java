package com.example.igiagante.thegarden.creation.plants.usecase;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/5/16.
 */
public class GetImagesUseCase extends UseCase<Collection<String>> {

    private Context mContext;

    @Inject
    public GetImagesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                            Context context) {
        super(threadExecutor, postExecutionThread);
        this.mContext = context;
    }

    @Override
    protected Observable buildUseCaseObservable(Collection<String> filesPaths) {
        return getImagesCollection(filesPaths);
    }

    private Observable<Collection<Image>> getImagesCollection(Collection<String> filesPaths) {

        File file;
        ArrayList<Image> viewTypeImages = new ArrayList<>();

        for(String path : filesPaths) {

            file = new File(path);
            Image image = new Image();

            //set file
            image.setFile(file);

            //set size of file
            image.setSize((int)file.length());

            //set type of file
            ContentResolver cR = mContext.getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(Uri.parse(path)));
            image.setType(type);

            //set name of image
            image.setName(file.getName());

            //set uri
            image.setUrl(path);

            // add image to collection
            viewTypeImages.add(image);
        }

        return Observable.just(viewTypeImages);
    }

}
