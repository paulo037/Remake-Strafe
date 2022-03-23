package com.ufv.strafe.controller;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UsuarioDAO;

import com.ufv.strafe.utils.Resource;
import com.ufv.strafe.ui.activitys.ConfiguracoesActivity;
import com.ufv.strafe.ui.Adapters.ItemConfiguracoesAdapter;

import java.util.ArrayList;
import java.util.Set;

public class ConfiguracoesController {
    private UsuarioDAO usuarioDAO;
    private ConfiguracoesActivity configuracoesActivity;
    private String[] nomeJogos;
    private ArrayList<Integer> cores = new ArrayList<>();
    private ItemConfiguracoesAdapter adapter;


    public ConfiguracoesController(ConfiguracoesActivity configuracoesActivity) {
        usuarioDAO = new UsuarioDAO();
        this.configuracoesActivity = configuracoesActivity;
    }

    public void Observe(LifecycleOwner lifecycleOwner, Context context) {
        usuarioDAO.getLiveData().observe(lifecycleOwner, user -> {
            nomeJogos = configuracoesActivity.getResources().getStringArray(R.array.e_esports);
            cores = Resource.getColorsJogos(context);
            adapter = new ItemConfiguracoesAdapter(nomeJogos, cores, user.getJogos());
            configuracoesActivity.update(adapter);
        });


    }


    public void updateJogos() {
        if (adapter.jogos.getValue() == null) return;

        Boolean valor;
        Set<String> jogos = adapter.jogos.getValue().keySet();

        for (String key : jogos) {
            valor = adapter.jogos.getValue().get(key);
            usuarioDAO.putJogos(key, valor);
        }

        usuarioDAO.updateUser();

    }
}
