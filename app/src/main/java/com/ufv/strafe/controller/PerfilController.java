package com.ufv.strafe.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.databinding.FragmentPerfilBinding;

import com.ufv.strafe.ui.activitys.LoginActivity;
import com.ufv.strafe.ui.fragmentos.PerfilFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;


public class PerfilController {
    private UserDAO userDAO;
    private ArrayList<Integer> icons = new ArrayList<>();
    private PerfilFragment perfilFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public PerfilController(PerfilFragment perfilFragment) {
        userDAO = new UserDAO();
        this.perfilFragment = perfilFragment;
    }


    public void verifyAuthentication() {
        boolean logado = userDAO.verifyAuthentication();
        if (!logado) {
            Intent intent = new Intent(perfilFragment.getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            perfilFragment.getContext().startActivity(intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void userObserve(LifecycleOwner lifecycleOwner, FragmentPerfilBinding binding, Context context) {
        userDAO.getLiveData().observe(lifecycleOwner, user -> {

            icons.clear();
            getIcons(icons);

            perfilFragment.updatePerfil(icons,
                    String.valueOf(user.getSaldo()),
                    user.getNome().toUpperCase(Locale.ROOT),
                    user.getFotoPerfil());

            userDAO.updateUser();

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getIcons(ArrayList<Integer> icons) {
        String[] iconsName;
        int pos = 0;

        ArrayList<Boolean> values = new ArrayList<>();
        ArrayList<String> nomesJogos = new ArrayList<>();

        userDAO.getJogos().forEach((k, v) -> nomesJogos.add(k));
        Collections.sort(nomesJogos);

        for (String jogo : nomesJogos) {
            values.add(userDAO.getJogos().get(jogo));
        }

        iconsName = perfilFragment.getResources().getStringArray(R.array.icons_name);

        for (String icon : iconsName) {
            if (values.get(pos)) {
                icons.add(perfilFragment.getResources().getIdentifier(icon, "drawable", perfilFragment.getActivity().getPackageName()));
            }
            pos++;
        }
    }

    public void signOut() {
        userDAO.signOut();
    }

    public Map<String, Boolean> getJogos() {
        return userDAO.getJogos();
    }

}
