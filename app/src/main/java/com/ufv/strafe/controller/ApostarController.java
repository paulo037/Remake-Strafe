package com.ufv.strafe.controller;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.dao.PartidaDAO;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.FragmentApostarBinding;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.ui.fragmentos.ApostarFragment;
import com.ufv.strafe.ui.Adapters.ItemApostaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ApostarController {
    private UsuarioDAO usuarioDAO;
    private PartidaDAO partidaDAO;
    private ApostarFragment apostarFragment;


    public ApostarController(ApostarFragment apostarFragment) {
        usuarioDAO = new UsuarioDAO();
        partidaDAO = new PartidaDAO();
        this.apostarFragment = apostarFragment;
    }


    public void observe(String jogo, FragmentApostarBinding binding, LifecycleOwner lifecycleOwner, Context context) {
        partidaDAO.partidas.observe(lifecycleOwner, partidas -> {
            List<Partida> partidasbyJogo = new ArrayList<>();

            for (Partida partida : partidas) {
                if (jogo.equals(partida.getJogo())) {
                    partidasbyJogo.add(partida);
                }
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            ItemApostaAdapter adapter = new ItemApostaAdapter(partidasbyJogo, apostarFragment);
            binding.recycleApostas.setLayoutManager(layoutManager);
            binding.recycleApostas.setAdapter(adapter);

        });

    }
}
