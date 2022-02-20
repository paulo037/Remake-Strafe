package com.ufv.strafe.controller;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UserDAO;

import com.ufv.strafe.ui.activitys.ConfiguracoesActivity;

import com.ufv.strafe.ui.Adapters.ItemConfiguracoesAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ConfiguracoesController {
    private UserDAO userDAO;
    private ConfiguracoesActivity configuracoesActivity;
    private String[] nomeJogos;
    private ArrayList<Integer> cores = new ArrayList<>();
    ItemConfiguracoesAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ConfiguracoesController(ConfiguracoesActivity configuracoesActivity) {
        userDAO = new UserDAO();
        this.configuracoesActivity = configuracoesActivity;
    }

    public void Observe(LifecycleOwner lifecycleOwner, Context context) {
        userDAO.getLiveData().observe(lifecycleOwner, user -> {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateJogos() {
        Objects.requireNonNull(adapter.jogos.getValue()).forEach((key, valor) -> userDAO.putJogos(key, valor));

        userDAO.updateUser();

    }
}
