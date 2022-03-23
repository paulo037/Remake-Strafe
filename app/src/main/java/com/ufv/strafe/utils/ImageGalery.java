package com.ufv.strafe.utils;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class ImageGalery implements DefaultLifecycleObserver {
    private final ActivityResultRegistry registry;
    private final ContentResolver contentResolver;
    private ActivityResultLauncher<String> mGetContent;
    private static  Uri uriSelect;
    private  Runnable runnable;

    public ImageGalery(ActivityResultRegistry registry,
                ContentResolver contentResolver) {
        this.registry = registry;
        this.contentResolver = contentResolver;

    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

        mGetContent = registry.register("imgKey", owner, new ActivityResultContracts.GetContent(), uri -> {
            try {
                uriSelect = uri;
                runnable.run();
            } catch (Exception e) {
                Log.e("erro", e.getMessage());
            }

        });
    }

    public void selectImage(Runnable runnable) {
        if (null != mGetContent) {
            mGetContent.launch("image/*");
        }
        this.runnable = runnable;

    }




    public Uri getUriSelect()  {

       return uriSelect;
    }




}