package com.ufv.strafe.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.R;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;

import com.ufv.strafe.model.utils.ImageGalery;
import com.ufv.strafe.ui.activitys.CadastrarActivity;
import com.ufv.strafe.ui.activitys.ConfiguracoesActivity;


import java.io.IOException;
import java.util.UUID;

public class CadastrarController {

    private final UsuarioDAO usuarioDAO;
    private Uri uriSelect;
    private ImageGalery imageGalery;
    private CadastrarActivity activity;
    private ActivityCadastrarBinding binding;
    private Context context;


    public CadastrarController(CadastrarActivity cadastrarActivity,
                               ActivityCadastrarBinding activityCadastrarBinding) {

        usuarioDAO = new UsuarioDAO();

        activity = cadastrarActivity;

        binding = activityCadastrarBinding;

        context = activity.getBaseContext();


        imageGalery = new ImageGalery(
                activity.getActivityResultRegistry(),
                activity.getContentResolver());
    }

    public void observe(Lifecycle lifecycle) {
        //imageGalery.
        lifecycle.addObserver(imageGalery);
    }

    public void createUser(
            String nome,
            String email,
            String senha) {


        //verifica se os dados estão todos preenchidos
        if ((nome.isEmpty()) || (email.isEmpty()) || (senha.isEmpty())) {
            Toast.makeText(context, "Preencha Todos os compos", Toast.LENGTH_LONG).show();
            return;
        }

        //verifica se o usuario selecionou uma foto
        if (uriSelect == null) {
            // Toast.makeText(context, "selecione uma foto", Toast.LENGTH_LONG).show();
            uriSelect = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.user_default);
            // return;
        }

        String filename = UUID.randomUUID().toString();
        progress(true);
        //Cria a autenticação para login do usuáiro


        this.createDataUser(
                context,
                nome,
                email,
                senha,
                uriSelect,
                filename);
    }


    public void selectImg() {
        imageGalery.selectImage(new UpdateFoto());
    }

    public void config() {
        Intent intent = new Intent(context, ConfiguracoesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

        progress(false);
        binding.cadastroVisibilit.setVisibility(View.INVISIBLE);

    }

    public void progress(Boolean visible) {
        activity.progress(visible);
    }


    public void createDataUser(Context context,
                               String nome,
                               String email,
                               String senha,
                               Uri uriSelect,
                               String fileName
    ) {

        FirebaseApp.initializeApp(context);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("sucesso", task.getResult().getUser().getUid());
                        usuarioDAO.saveUserInfireBase(
                                nome,
                                uriSelect,
                                fileName,
                                this);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("erro", e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    progress(false);
                });
    }

    private class UpdateFoto implements Runnable {

        @Override
        public void run() {
            uriSelect = imageGalery.getUriSelect();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uriSelect);
                binding.photoView.setImageDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
                binding.buttonPhoto.setAlpha(0);
            } catch (IOException ignored) {

            }
        }
    }

}
