package com.ufv.strafe.controller;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UsuarioDAO;

import com.ufv.strafe.ui.activitys.ConfiguracoesActivity;

import com.ufv.strafe.ui.Adapters.ItemConfiguracoesAdapter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class ConfiguracoesController {
    private UsuarioDAO usuarioDAO;
    private ConfiguracoesActivity configuracoesActivity;
    private String[] nomeJogos;
    private ArrayList<Integer> cores = new ArrayList<>();
    ItemConfiguracoesAdapter adapter;


    public ConfiguracoesController(ConfiguracoesActivity configuracoesActivity) {
        usuarioDAO = new UsuarioDAO();
        this.configuracoesActivity = configuracoesActivity;
    }

    public void Observe(LifecycleOwner lifecycleOwner, Context context) {
        usuarioDAO.getLiveData().observe(lifecycleOwner, user -> {
            nomeJogos = configuracoesActivity.getResources().getStringArray(R.array.e_esports);
            getColors(cores, context);
            adapter = new ItemConfiguracoesAdapter(nomeJogos, cores,user.getJogos());
            configuracoesActivity.update(adapter);
        });


    }

    public void getColors(ArrayList<Integer> cores, Context context) {
        String[] colorsName;
        colorsName = configuracoesActivity.getResources().getStringArray(R.array.colors_name);


        for (String cor : colorsName) {
            cores.add(configuracoesActivity.getResources().getIdentifier(cor, "color", context.getPackageName()));
        }
    }


    public void updateJogos() {
        if(adapter.jogos.getValue() == null) return;

        Boolean valor;
        Set<String> jogos = adapter.jogos.getValue().keySet();

        for(String key : jogos){
            valor = adapter.jogos.getValue().get(key);
            usuarioDAO.putJogos(key, valor);
        }

        usuarioDAO.updateUser();

    }
}
