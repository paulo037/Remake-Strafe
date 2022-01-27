package com.ufv.strafe.model;

import android.content.Context;
import android.os.Build;
import android.widget.Adapter;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ufv.strafe.R;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.databinding.FragmentConfiguracoesBinding;
import com.ufv.strafe.entities.usuario.Usuario;
import com.ufv.strafe.ui.fragmentos.Configuracoes;
import com.ufv.strafe.utils.ItemJogoAdapter;

import java.util.ArrayList;

public class ConfiguracoesModel {
    private UserDAO userDAO;
    private Configuracoes configuraoesFragment;
    private  String[] jogos;
    private ArrayList<Integer> cores = new ArrayList<Integer>();
    ItemJogoAdapter adapter;

    public ConfiguracoesModel(Configuracoes configuraoesFragment){
        userDAO = new UserDAO();
        this.configuraoesFragment = configuraoesFragment;
        adapter = new ItemJogoAdapter(jogos, cores, userDAO);
    }

    public void userObserve(LifecycleOwner lifecycleOwner, FragmentConfiguracoesBinding binding, Context context) {
        adapter.userDAO.getLiveData().observe(lifecycleOwner,  new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                jogos = configuraoesFragment.getResources().getStringArray(R.array.e_esports);
                getColors(cores, context);

                adapter = new ItemJogoAdapter(jogos, cores, userDAO);
                binding.recycleJogos.setAdapter(adapter);
                binding.recycleJogos.setLayoutManager(new LinearLayoutManager(context));
            }
        });
    }

    public void getColors( ArrayList<Integer> cores, Context context){
        String colorsName[];
        colorsName = configuraoesFragment.getResources().getStringArray(R.array.colors_name);


        for (String cor : colorsName) {
            cores.add(configuraoesFragment.getResources().getIdentifier(cor, "color", context.getPackageName()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateJogos() {
        adapter.jogos.forEach((key, valor) -> {
            adapter.userDAO.putJogos(key, valor);
        });

        adapter.userDAO.updateUser();
    }
}
