package com.ufv.strafe.controller;

import android.content.ContentResolver;
import android.content.Context;
import androidx.activity.result.ActivityResultRegistry;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;
import com.ufv.strafe.model.CadastrarModel;


public class CadastrarController {
    private CadastrarModel model;

    public  CadastrarController(ActivityResultRegistry registry, ActivityCadastrarBinding binding, ContentResolver contentResolver){
        model = new CadastrarModel(registry, binding, contentResolver);
    }


    public void createUser(ActivityCadastrarBinding binding, Context context, FragmentManager supportFragmentManager) {
        model.createUser(binding, context ,supportFragmentManager);
    }


    public void selectImg() {
        model.selectImg();
    }

    public void observe(Lifecycle lifecycle){
        model.observe(lifecycle);
    }
}
