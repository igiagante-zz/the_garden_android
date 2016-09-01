package com.example.igiagante.thegarden.home.plants.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.example.igiagante.thegarden.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 1/9/16.
 */
public class EmailProducer {

    private Context mContext;
    private ArrayList<String> urls;
    private String emailText;

    public EmailProducer(Context mContext, String emailText, ArrayList<String> urls) {
        this.mContext = mContext;
        this.urls = urls;
        this.emailText = emailText;
    }

    private void createShareIntent(List<String> filesPaths) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String subject = mContext.getResources().getString(R.string.subject_email);

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, this.emailText);

        ArrayList<Uri> uris = new ArrayList<>();

        for (String file : filesPaths) {
            File fileIn = new File(file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        mContext.startActivity(Intent.createChooser(shareIntent, mContext.getString(R.string.share_plant_info)));
    }

    public void createAttachmentAndSendEmail() {

        Observable<List<String>> downloadObservable = Observable.create(
                sub -> {

                    ArrayList<String> filesPaths = new ArrayList<>();
                    OutputStream output = null;

                    File folder = new File(Environment.getExternalStorageDirectory() + "/plants");
                    folder.mkdirs();

                    for (int i = 0; i < urls.size(); i++) {

                        try {

                            File tempFile = new File(folder.getAbsolutePath(), "image" + i + ".jpeg");
                            String filePath = tempFile.getAbsolutePath();

                            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(urls.get(i)).getContent());
                            output = new BufferedOutputStream(new FileOutputStream(filePath));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

                            output.flush();
                            output.close();

                            filesPaths.add(tempFile.getAbsolutePath());

                        } catch (Exception e) {
                            sub.onError(e);
                        }
                    }
                    sub.onNext(filesPaths);
                    sub.onCompleted();
                }
        );

        Subscriber<List<String>> mySubscriber = new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<String> filesPaths) {
                createShareIntent(filesPaths);
            }
        };

        downloadObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mySubscriber);
    }
}
