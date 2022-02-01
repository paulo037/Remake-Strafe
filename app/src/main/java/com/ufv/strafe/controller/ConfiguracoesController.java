package com.ufv.strafe.controller;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.databinding.FragmentConfiguracoesBinding;
import com.ufv.strafe.model.Usuario;
import com.ufv.strafe.ui.fragmentos.Configuracoes;
import com.ufv.strafe.ui.utils.ItemJogoAdapter;

import java.util.ArrayList;

public class ConfiguracoesController {
    private UserDAO userDAO;
    private Configuracoes configuraoesFragment;
    private String[] nomeJogos;
    private ArrayList<Integer> cores = new ArrayList<>();
    ItemJogoAdapter adapter;

    public ConfiguracoesController(Configuracoes configuraoesFragment) {
        userDAO = new UserDAO();
        this.configuraoesFragment = configuraoesFragment;
    }

    public void Observe(LifecycleOwner lifecycleOwner, FragmentConfiguracoesBinding binding, Context context) {
        userDAO.getLiveData().observe(lifecycleOwner, user -> {
            nomeJogos = configuraoesFragment.getResources().getStringArray(R.array.e_esports);
            getColors(cores, context);
            adapter = new ItemJogoAdapter(nomeJogos, cores,user.getJogos());
            configuraoesFragment.update(adapter);
        });


    }

    public void getColors(ArrayList<Integer> cores, Context context) {
        String[] colorsName;
        colorsName = configuraoesFragment.getResources().getStringArray(R.array.colors_name);


        for (String cor : colorsName) {
            cores.add(configuraoesFragment.getResources().getIdentifier(cor, "color", context.getPackageName()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateJogos() {
        adapter.jogos.getValue().forEach((key, valor) -> {
            userDAO.putJogos(key, valor);
        });

        userDAO.updateUser();

    }
}
