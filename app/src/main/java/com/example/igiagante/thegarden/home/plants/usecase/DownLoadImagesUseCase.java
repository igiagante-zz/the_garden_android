package com.example.igiagante.thegarden.home.plants.usecase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.restAPI.ImageServiceApi;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 9/2/16.
 */
@Singleton
public class DownLoadImagesUseCase extends UseCase <Void> {

    @Inject
    public DownLoadImagesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {

        ImageServiceApi imageServiceApi = new ImageServiceApi();

        Observable<List<String>> imagesPath = imageServiceApi.getImagesPath();

        // get Data From api
        List<String> listOne = new ArrayList<>();
        imagesPath.subscribeOn(Schedulers.io()).toBlocking().subscribe(strings -> listOne.addAll(strings));

        return downloadImage(listOne);
    }

    @NonNull
    private Observable<List<String>> downloadImage(List<String> urls) {

        return Observable.create(
                sub -> {

                    ArrayList<String> filesPaths = new ArrayList<>();
                    OutputStream output = null;

                    File flavorsFolder = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/thegarden/flavors");

                    flavorsFolder.mkdirs();

                    File plaguesFolder = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/thegarden/plagues");

                    plaguesFolder.mkdirs();

                    for (int i = 0; i < urls.size(); i++) {

                        try {

                            String [] parts = urls.get(i).split("/");
                            String folder = parts[parts.length - 2];
                            String name = parts[parts.length - 1];

                            File tempFile = null;

                            if(folder.equals("flavors")) {
                                tempFile = new File(flavorsFolder, name);
                            } else {
                                tempFile = new File(plaguesFolder, name);
                            }

                            String filePath = tempFile.getAbsolutePath();

                            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(urls.get(i)).getContent());
                            output = new BufferedOutputStream(new FileOutputStream(filePath));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

                            output.flush();
                            output.close();

                            Log.d("file image ", i  + "   " + name + "  saved   ");
                            filesPaths.add(tempFile.getAbsolutePath());

                        } catch (Exception e) {
                            sub.onError(e);
                        }
                    }
                    sub.onNext(filesPaths);
                    sub.onCompleted();
                }
        );
    }
}
