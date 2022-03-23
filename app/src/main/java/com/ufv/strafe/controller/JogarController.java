package com.ufv.strafe.controller;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.PartidaDAO;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.FragmentJogarBinding;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.model.Usuario;
import com.ufv.strafe.ui.Adapters.ItemRecompensasAdapter;
import com.ufv.strafe.ui.fragmentos.JogarFragment;
import com.ufv.strafe.ui.Adapters.ItemJogarAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JogarController {
    private UsuarioDAO usuarioDAO;
    private PartidaDAO partidaDAO;
    private ArrayList<Integer> iconsSeusJogos = new ArrayList<>();
    private ArrayList<Integer> iconsOutrosJogos = new ArrayList<>();
    private ArrayList<String> nomeSeusJogos = new ArrayList<>();
    private ArrayList<String> nomeOutrosJogos = new ArrayList<>();
    private JogarFragment jogarFragment;


    public JogarController(JogarFragment jogarFragment) {
        usuarioDAO = new UsuarioDAO();
        partidaDAO = new PartidaDAO();
        this.jogarFragment = jogarFragment;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void partidaObserve(LifecycleOwner lifecycleOwner,
                               ItemJogarAdapter seusJogosAdapter,
                               ItemJogarAdapter outrosJogosAdapter,
                               ItemRecompensasAdapter recompensasAdapter,
                               FragmentJogarBinding binding) {
        partidaDAO.partidas.observe(lifecycleOwner, partidas -> {
            Log.i(toString(), "atualizando Partidas");
            iconsSeusJogos.clear();
            getIcons(iconsSeusJogos, Boolean.TRUE, nomeSeusJogos);
            iconsOutrosJogos.clear();
            getIcons(iconsOutrosJogos, Boolean.FALSE, nomeOutrosJogos);


            seusJogosAdapter.updateAdapter(iconsSeusJogos, partidaDAO.partidas.getValue(), nomeSeusJogos);
            seusJogosAdapter.notifyDataSetChanged();
            outrosJogosAdapter.updateAdapter(iconsOutrosJogos, partidaDAO.partidas.getValue(), nomeOutrosJogos);
            outrosJogosAdapter.notifyDataSetChanged();

            recompensasAdapter.updateAdapter(getRecompensas());
            recompensasAdapter.notifyDataSetChanged();

            if (iconsSeusJogos.isEmpty()) {
                binding.labelSeusEEsports.setText("");
            }

            if (iconsOutrosJogos.isEmpty()) {
                binding.labelOutrosEEsports.setText("");
            }
            usuarioDAO.updateUser();
            Log.i(toString(), "Partidas Atualizadas");
        });


    }

    public void getIcons(ArrayList<Integer> icons, Boolean flag, ArrayList<String> nomesJogos) {
        String[] iconsName;
        int pos = 0;
        ArrayList<String> aux = new ArrayList<>();
        ArrayList<Boolean> values = new ArrayList<>();
        usuarioDAO.getJogos().forEach((k, v) -> nomesJogos.add(k));
        Collections.sort(nomesJogos);

        for (String jogo : nomesJogos) {
            values.add(usuarioDAO.getJogos().get(jogo));
        }

        iconsName = jogarFragment.getResources().getStringArray(R.array.icons_name);

        for (String icon : iconsName) {
            if (values.get(pos) == flag) {
                aux.add(nomesJogos.get(pos));
                icons.add(jogarFragment.getResources().getIdentifier(icon, "drawable", jogarFragment.getActivity().getPackageName()));
            }
            pos++;
        }
        nomesJogos.clear();
        aux.forEach(s -> nomesJogos.add(s));
    }


    public Map<String, Double> getRecompensas() {

        Map<String, Double> recompensas = new HashMap<>();
        Usuario usuario = usuarioDAO.getLiveData().getValue();
        if (usuario == null) return null;

        Map<String, ArrayList<String>> apostas = usuario.getApostas();
        ArrayList<Partida> partidas = partidaDAO.getPartidasRecompensas();

        ArrayList<String> keys = new ArrayList<>(apostas.keySet());


        for (String idPartida : keys) {

            ArrayList<String> bDApostas = apostas.get(idPartida);
            try {
                if (bDApostas != null) {
                    for (Partida p : partidas) {
                        if (p.getId().equals(idPartida) ) {
                            Double vAposta;
                            for (String aposta : bDApostas) {
                                vAposta = p.getSaldoAposta(aposta);
                                if (vAposta > 0) {
                                    vAposta = (Math.round(vAposta * 100.0) / 100.0);
                                    recompensas.put(aposta, vAposta);
                                } else {
                                    usuarioDAO.removeAposta(aposta);
                                    usuarioDAO.updateStatisticas(vAposta);
                                }
                            }
                        }

                    }
                }


            } catch (ConcurrentModificationException ignored) {
            }
        }


        return recompensas;
    }

    public void updateAposta(String aposta, Double valor) {
        usuarioDAO.removeAposta(aposta);
        usuarioDAO.updateStatisticas(valor);
        usuarioDAO.addSaldo(valor);
        usuarioDAO.updateUser();
    }

}
