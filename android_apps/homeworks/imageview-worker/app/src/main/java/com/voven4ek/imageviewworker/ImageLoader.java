package com.voven4ek.imageviewworker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.URL;

public class ImageLoader extends Worker {
    public ImageLoader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imageUrl = getInputData().getString("imageUrl");
        try {
            URL url = new URL(imageUrl);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            MainActivity.cache.put(MainActivity.cacheKey, image);
        } catch(IOException e) {
            Log.e("ImageLoader", "Error loading image", e);
        }
        return ListenableWorker.Result.success();
    }


}
