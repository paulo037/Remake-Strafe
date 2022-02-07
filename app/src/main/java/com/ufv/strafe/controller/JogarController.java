package com.ufv.strafe.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.databinding.FragmentJogarBinding;
import com.ufv.strafe.databinding.FragmentPerfilBinding;
import com.ufv.strafe.ui.activitys.LoginActivity;
import com.ufv.strafe.ui.fragmentos.JogarFragment;
import com.ufv.strafe.ui.fragmentos.PerfilFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class JogarController {
    private UserDAO userDAO;
    private ArrayList<Integer> iconsSeusJogos = new ArrayList<>();
    private ArrayList<Integer> iconsOutrosJogos = new ArrayList<>();
    private JogarFragment jogarFragment;


    public JogarController(JogarFragment jogarFragment) {
        userDAO = new UserDAO();
        this.jogarFragment = jogarFragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void userObserve(LifecycleOwner lifecycleOwner, FragmentJogarBinding binding, Context context) {
        userDAO.getLiveData().observe(lifecycleOwner, user -> {

            iconsSeusJogos.clear();
            getIcons(iconsSeusJogos, Boolean.TRUE);
            iconsOutrosJogos.clear();
            getIcons(iconsOutrosJogos, Boolean.FALSE);

            jogarFragment.updatePerfil(iconsSeusJogos, iconsOutrosJogos,
                    String.valueOf(user.getSaldo()),
                    user.getNome().toUpperCase(Locale.ROOT),
                    user.getFotoPerfil());

            userDAO.updateUser();

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getIcons(ArrayList<Integer> icons, Boolean flag) {
        String[] iconsName;
        int pos = 0;

        ArrayList<Boolean> values = new ArrayList<>();
        ArrayList<String> nomesJogos = new ArrayList<>();

        userDAO.getJogos().forEach((k, v) -> nomesJogos.add(k));
        Collections.sort(nomesJogos);

        for (String jogo : nomesJogos) {
            values.add(userDAO.getJogos().get(jogo));
        }

        iconsName = jogarFragment.getResources().getStringArray(R.array.icons_name);

        for (String icon : iconsName) {
            if (values.get(pos) == flag) {
                icons.add(jogarFragment.getResources().getIdentifier(icon, "drawable", jogarFragment.getActivity().getPackageName()));
            }
            pos++;
        }
    }


    public Map<String, Boolean> getJogos() {
        return userDAO.getJogos();
    }

}
