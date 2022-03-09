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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;
import com.ufv.strafe.ui.activitys.CadastrarActivity;
import com.ufv.strafe.ui.activitys.ConfiguracoesActivity;



import java.util.UUID;

public class CadastrarController {

    private final UsuarioDAO usuarioDAO;
    private Uri uriSelect;
    private final LifecycleObserver lifecycleObserver;
    private CadastrarActivity activity;
    private ActivityCadastrarBinding binding;
    private Context context;


    public CadastrarController(CadastrarActivity cadastrarActivity,
                               ActivityCadastrarBinding activityCadastrarBinding) {

        usuarioDAO = new UsuarioDAO();

        activity = cadastrarActivity;

        binding = activityCadastrarBinding;

        context = activity.getBaseContext();

        lifecycleObserver = new LifecycleObserver(
                activity.getActivityResultRegistry(),
                binding, activity.getContentResolver());
    }

    public void observe(Lifecycle lifecycle) {
        lifecycle.addObserver(lifecycleObserver);
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
        usuarioDAO.createDataUser(
                context,
                nome,
                email,
                senha,
                uriSelect,
                filename,
                this);
    }


    public void selectImg() {
        lifecycleObserver.selectImage();
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


    class LifecycleObserver implements DefaultLifecycleObserver {
        private final ActivityResultRegistry registry;
        private final ActivityCadastrarBinding binding;
        private final ContentResolver contentResolver;
        private ActivityResultLauncher<String> mGetContent;

        LifecycleObserver(ActivityResultRegistry registry, ActivityCadastrarBinding binding, ContentResolver contentResolver) {
            this.registry = registry;
            this.binding = binding;
            this.contentResolver = contentResolver;

        }

        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {

            mGetContent = registry.register("imgKey", owner, new ActivityResultContracts.GetContent(), uri -> {
                try {
                    uriSelect = uri;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                    binding.photoView.setImageDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
                    binding.buttonPhoto.setAlpha(0);
                } catch (Exception e) {
                    Log.e("erro", e.getMessage());
                }

            });
        }

        public void selectImage() {
            if (null != mGetContent) {
                mGetContent.launch("image/*");
            }
        }
    }



}
