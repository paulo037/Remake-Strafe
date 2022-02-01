package com.ufv.strafe.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.ufv.strafe.R;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;


import java.util.UUID;

public class CadastrarController {

    private final UserDAO userDAO;
    private Uri uriSelect;
    private final LifecycleObserver lifecycleObserver;

    public CadastrarController(ActivityResultRegistry registry, ActivityCadastrarBinding binding, ContentResolver contentResolver) {
        userDAO = new UserDAO();
        lifecycleObserver = new LifecycleObserver(registry, binding, contentResolver);
    }

    public void observe(Lifecycle lifecycle) {
        lifecycle.addObserver(lifecycleObserver);
    }

    public void createUser(ActivityCadastrarBinding binding,
                           Context context,
                           FragmentManager supportFragmentManager,
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

        //Cria a autenticação para login do usuáiro
        userDAO.createDataUser(context, nome, email, senha, uriSelect, filename, supportFragmentManager, binding);


    }


    public void selectImg() {
        lifecycleObserver.selectImage();
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
