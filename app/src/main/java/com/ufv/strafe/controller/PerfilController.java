package com.ufv.strafe.controller;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.databinding.FragmentPerfilBinding;
import com.ufv.strafe.model.PerfilModel;
import com.ufv.strafe.ui.fragmentos.Perfil;

public class PerfilController {

    private PerfilModel model;


    public PerfilController(Perfil perfilFragment){
        model = new PerfilModel(perfilFragment);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void userObserve(LifecycleOwner lifecycleOwner, FragmentPerfilBinding binding, Context context){
       model.userObserve(lifecycleOwner, binding, context);
    }


    public void signOut() {
        model.signOut();
    }

    public void verifyAuthentication() {
        model.verifyAuthentication();
    }
}
