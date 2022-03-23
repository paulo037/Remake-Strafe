package com.ufv.strafe.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

import com.ufv.strafe.R;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.databinding.FragmentPerfilBinding;

import com.ufv.strafe.ui.activitys.LoginActivity;
import com.ufv.strafe.ui.fragmentos.PerfilFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;


public class PerfilController {
    private UsuarioDAO usuarioDAO;
    private ArrayList<Integer> icons = new ArrayList<>();
    private PerfilFragment perfilFragment;


    public PerfilController(PerfilFragment perfilFragment) {
        usuarioDAO = new UsuarioDAO();
        this.perfilFragment = perfilFragment;
    }


    public void verifyAuthentication() {
        boolean logado = usuarioDAO.verifyAuthentication();
        if (!logado) {
            Intent intent = new Intent(perfilFragment.getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            perfilFragment.getContext().startActivity(intent);
        }
    }


    public void userObserve(LifecycleOwner lifecycleOwner, FragmentPerfilBinding binding, Context context) {
        usuarioDAO.getLiveData().observe(lifecycleOwner, user -> {
            Log.i(toString(), "Atualizando perfil");
            icons.clear();
            getIcons(icons);

            BigDecimal bd= new BigDecimal(user.getSaldo()).setScale(2, RoundingMode.HALF_UP);
            double saldo  = bd.doubleValue();

            perfilFragment.updatePerfil(icons,
                    String.valueOf(saldo),
                    user.getNome().toUpperCase(Locale.ROOT),
                    user.getFotoPerfil(),
                    getNomePatente(user.getSaldo()),
                    user.getErros(),
                    user.getAcertos());

            usuarioDAO.updateUser();

            Log.i(toString(), "Perfil atualizado");

        });
    }


    public void getIcons(ArrayList<Integer> icons) {
        String[] iconsName;
        int pos = 0;

        ArrayList<Boolean> values = new ArrayList<>();

        ArrayList<String> nomesJogos = new ArrayList<>(usuarioDAO.getJogos().keySet());

        Collections.sort(nomesJogos);

        for (String jogo : nomesJogos) {
            values.add(usuarioDAO.getJogos().get(jogo));
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
        usuarioDAO.signOut();
    }

   public String getNomePatente(Double saldo){
        if (saldo<250)
            return  "N00b";
        else if (saldo<500)
            return("Bronze");
        else if(saldo<750)
            return "Prata";
        else if(saldo<1000)
            return "Ouro";
        else if(saldo<1250)
            return "Diamante";
        else if(saldo<1500)
            return "Desafiante";

       else return  "Heroi";

    }

}
