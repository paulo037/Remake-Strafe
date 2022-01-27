package com.ufv.strafe.controller;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import com.ufv.strafe.databinding.FragmentConfiguracoesBinding;
import com.ufv.strafe.model.ConfiguracoesModel;
import com.ufv.strafe.ui.fragmentos.Configuracoes;



public class ConfiguracoesController {

    private ConfiguracoesModel model;

    public ConfiguracoesController(Configuracoes configuracoesFragment){
        model = new ConfiguracoesModel(configuracoesFragment);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void configuracoesObserve(LifecycleOwner lifecycleOwner, FragmentConfiguracoesBinding binding, Context context){
        model.userObserve(lifecycleOwner, binding, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateJogos() {
        model.updateJogos();
    }


}
