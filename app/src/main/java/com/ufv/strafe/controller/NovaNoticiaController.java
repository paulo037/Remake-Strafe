package com.ufv.strafe.controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ufv.strafe.databinding.FragmentNovaNoticiaBinding;
import com.ufv.strafe.model.Noticia;
import com.ufv.strafe.model.utils.ImageGalery;
import com.ufv.strafe.ui.fragmentos.NovaNoticiaFragment;

import java.io.IOException;

public class NovaNoticiaController {
    private Uri uriSelect;
    private ImageGalery imageGalery;
    private FragmentActivity activity;
    private FragmentNovaNoticiaBinding binding;
    private Context context;
    NovaNoticiaFragment fragment;

    public NovaNoticiaController(FragmentActivity activity,
                                 FragmentNovaNoticiaBinding binding,
                                 NovaNoticiaFragment fragment) {


        this.activity = activity;

        this.binding = binding;

        context = activity.getBaseContext();



        imageGalery = new ImageGalery(
                fragment.requireActivity().getActivityResultRegistry(),
                activity.getContentResolver());
    }

    public void observe(Lifecycle lifecycle) {
        //imageGalery.
        lifecycle.addObserver(imageGalery);
    }

    public void selectImg() {
        imageGalery.selectImage(new UpdateFoto());

    }


    private class UpdateFoto implements  Runnable{

        @Override
        public void run() {
            uriSelect = imageGalery.getUriSelect();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uriSelect);
                binding.thumbunail.setImageDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
                binding.buttonPhoto.setAlpha(0);
            } catch (IOException e) {

            }
        }
    }

    public void  createNoticia(Noticia noticia){
        FirebaseFirestore.getInstance().collection("noticias")
                .document(noticia.getId())
                .set(noticia);
    }

}
