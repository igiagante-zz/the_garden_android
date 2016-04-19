package com.example.igiagante.thegarden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.example.igiagante.thegarden.core.activity.BaseActivity;
import com.example.igiagante.thegarden.core.network.NetworkRequest;
import com.example.igiagante.thegarden.core.network.ServiceFactory;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.service.PlantRestAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;

/**
 * Created by igiagante on 18/4/16.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mBody;

    private Subscription getSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBody = (TextView) findViewById(R.id.text_id);

        PlantRestAPI api = ServiceFactory.createRetrofitService(PlantRestAPI.class);/*
        getSubscription =  NetworkRequest.performAsyncRequest(api.getPlant("57164f1e654be6e328000003"), (plant) -> {
            // Update UI on the main thread
            displayPost(plant);
        });*/

        Plant plant = new Plant();
        plant.setName("test");

        HashMap<String, RequestBody> files = attachFile();

        /*
        getSubscription =  NetworkRequest.performAsyncRequest(api.createPlant(plant, files), (data) -> {
            // Update UI on the main thread
            displayPost(data);
        });*/
    }


    private HashMap<String, RequestBody> attachFile() {

        ArrayList<String> listOfNames = new ArrayList<>();
        listOfNames.add("images");
        listOfNames.add("mango-lg2");

        HashMap<String, RequestBody> map = new HashMap<>(listOfNames.size());
        RequestBody file = null;
        File f = null;

        for(int i = 0, size = listOfNames.size(); i<size;i++){

            try {

                File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                if(folder.isDirectory()) {
                    for (File file1: folder.listFiles()) {
                        Log.i(TAG, file1.getAbsolutePath());
                    }
                }

                f = new File(folder, "images.jpg");

                Bitmap bitmap = convertFileToBitmap(f);

                if(bitmap != null){
                    FileOutputStream fos = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, fos);
                    fos.flush();
                    fos.close();
                }else{
                    Log.i("INFO", "imageNotFound");
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            file = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            map.put("file\"; filename=\"" + listOfNames.get(i) + ".jpg",file);
            file = null;
            f = null;
        }
        return map;
    }

    private Bitmap convertFileToBitmap(File file) {
        if(file.exists()){
            Log.i(TAG, "the file exits");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        }
        Log.i(TAG, "the file does not exit. " + file.getAbsolutePath());
        return null;
    }

    private void displayPost(Plant plant) {
        mBody.setText("Body: " + plant.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getSubscription != null) {
            getSubscription.unsubscribe();
        }
    }
}
