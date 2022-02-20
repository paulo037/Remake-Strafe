package com.ufv.strafe.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.PartidaDAO;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.databinding.FragmentJogarBinding;
import com.ufv.strafe.ui.fragmentos.JogarFragment;
import com.ufv.strafe.ui.Adapters.ItemJogarAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class JogarController {
    private UserDAO userDAO;
    private PartidaDAO partidaDAO;
    private ArrayList<Integer> iconsSeusJogos = new ArrayList<>();
    private ArrayList<Integer> iconsOutrosJogos = new ArrayList<>();
    private ArrayList<String> nomeSeusJogos = new ArrayList<>();
    private ArrayList<String> nomeOutrosJogos = new ArrayList<>();
    private JogarFragment jogarFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public JogarController(JogarFragment jogarFragment) {
        userDAO = new UserDAO();
        partidaDAO = new PartidaDAO();
        this.jogarFragment = jogarFragment;
    }



    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void partidaObserve(LifecycleOwner lifecycleOwner,
                               ItemJogarAdapter seusJogosAdapter,
                               ItemJogarAdapter  outrosJogosAdapter,
                               FragmentJogarBinding binding) {
        partidaDAO.partidas.observe(lifecycleOwner, partidas -> {

            iconsSeusJogos.clear();
            getIcons(iconsSeusJogos, Boolean.TRUE, nomeSeusJogos);
            iconsOutrosJogos.clear();
            getIcons(iconsOutrosJogos, Boolean.FALSE, nomeOutrosJogos);


            seusJogosAdapter.updateAdapter(iconsSeusJogos, partidaDAO.partidas.getValue(), nomeSeusJogos);
            seusJogosAdapter.notifyDataSetChanged();
            outrosJogosAdapter.updateAdapter(iconsOutrosJogos, partidaDAO.partidas.getValue(), nomeOutrosJogos);
            outrosJogosAdapter.notifyDataSetChanged();

            if (iconsSeusJogos.isEmpty()){
                binding.labelSeusEEsports.setText("");
            }

            if (iconsOutrosJogos.isEmpty()){
                binding.labelOutrosEEsports.setText("");
            }
            userDAO.updateUser();

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getIcons(ArrayList<Integer> icons, Boolean flag, ArrayList<String> nomesJogos) {
        String[] iconsName;
        int pos = 0;
        ArrayList<String> aux = new ArrayList<>();
        ArrayList<Boolean> values = new ArrayList<>();
        userDAO.getJogos().forEach((k, v) -> nomesJogos.add(k));
        Collections.sort(nomesJogos);

        for (String jogo : nomesJogos) {
            values.add(userDAO.getJogos().get(jogo));
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


}
