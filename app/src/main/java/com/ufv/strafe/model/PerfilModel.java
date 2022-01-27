package com.ufv.strafe.model;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ufv.strafe.R;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.databinding.FragmentPerfilBinding;
import com.ufv.strafe.entities.usuario.Usuario;
import com.ufv.strafe.ui.activitys.LoginActivity;
import com.ufv.strafe.ui.fragmentos.Perfil;
import com.ufv.strafe.utils.ItemPerfilAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class PerfilModel {
    private UserDAO userDAO;
    private ArrayList<Integer> icons = new ArrayList<Integer>();;
    private Perfil perfilFragment;



    public PerfilModel(Perfil perfilFragment){
        userDAO = new UserDAO();
        this.perfilFragment = perfilFragment;
    }


    public void verifyAuthentication() {
        boolean logado = userDAO.verifyAuthentication();
        if(!logado) {
            Intent intent = new Intent(perfilFragment.getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            perfilFragment.getContext().startActivity(intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void userObserve(LifecycleOwner lifecycleOwner, FragmentPerfilBinding binding, Context context){
        userDAO.getLiveData().observe(lifecycleOwner, new Observer<Usuario>() {
            public void onChanged(Usuario user) {

                icons.clear();
                getIcons(icons);

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                ItemPerfilAdapter adapter = new ItemPerfilAdapter(icons, userDAO);

                binding.recycleJogosPerfil.setAdapter(adapter);
                binding.recycleJogosPerfil.setLayoutManager(layoutManager);

                binding.txtSaldo.setText(user.getSaldo().toString());
                binding.txtNome.setText(user.getNome().toUpperCase(Locale.ROOT));


                Picasso.get()
                        .load(user.getFotoPerfil())
                        .into(binding.imagePerfil);

                userDAO.updateUser();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getIcons(ArrayList<Integer> icons) {
        String[] iconsName;
        int pos = 0;
        ArrayList<Boolean> values = new ArrayList<Boolean>();
        ArrayList<String> nomesJogos = new ArrayList<String>();

        userDAO.getJogos().forEach((k, v) -> {
            nomesJogos.add(k);
        });
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
