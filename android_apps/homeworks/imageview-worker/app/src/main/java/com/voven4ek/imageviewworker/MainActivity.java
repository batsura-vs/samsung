package com.voven4ek.imageviewworker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static final String cacheKey = "bitmap";
    public static final LruCache<String, Bitmap> cache = new LruCache<>(1024 * 1024 * 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data data = new Data.Builder().putString("imageUrl", "https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg").build();
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(ImageLoader.class).setInputData(data).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(myWorkRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo.getState().isFinished()) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(cache.get(cacheKey));
                        }
                    }
                });
    }
}