package com.ufv.strafe.ui.perfil;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import com.ufv.strafe.R;
import com.ufv.strafe.activitys.LoginActivity;

import com.ufv.strafe.databinding.FragmentPerfilBinding;
import com.ufv.strafe.entities.usuario.UsuarioViewModel;
import com.ufv.strafe.entities.usuario.Usuario;
import com.ufv.strafe.utils.ItemPerfilAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class PerfilFragment extends Fragment {


    private UsuarioViewModel usuarioViewModel;
    private FragmentPerfilBinding binding;
    private NavController navCtr;
    private ArrayList<Integer> icons = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //infla o fragmento e passa os dados para o binding
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //instancia o viewModel
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);


        ItemPerfilAdapter adapter = new ItemPerfilAdapter(getContext(), icons, getActivity());
        binding.recycleJogosPerfil.setAdapter(adapter);


        //observa mudanças no usuario
        usuarioViewModel.usuario.observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<Usuario>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(Usuario usuario) {
                icons.clear();
                getIcons(icons);
                binding.recycleJogosPerfil.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                TextView txtNome = getActivity().findViewById(R.id.txt_nome);
                TextView txtSaldo = getActivity().findViewById(R.id.txt_saldo);
                ImageView image = getActivity().findViewById(R.id.image_perfil);

                binding.txtSaldo.setText(usuario.getSaldo().toString());
                binding.txtNome.setText(usuario.getNome().toUpperCase(Locale.ROOT));
                Picasso.get()
                        .load(usuario.getFotoPerfil())
                        .into(binding.imagePerfil);

                FirebaseFirestore.getInstance().collection("usuarios")
                        .document(usuario.getId())
                        .set(usuario, SetOptions.merge());

            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //logout
        binding.topAppBarPerfil.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.configurations:
                        Navigation.findNavController(view).navigate(R.id.action_page_perfil_to_gamesFavoritosFragment2);
                        break;
                    case R.id.button_logout:
                        FirebaseAuth.getInstance().signOut();
                        verifyAuthentication();
                        break;
                }
                return false;
            }
        });

        navCtr = Navigation.findNavController(view);


    }


    //verifica se o usuario está logado
    public void verifyAuthentication() {
        if (FirebaseAuth.getInstance().getUid() == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getIcons(ArrayList<Integer> icons) {
        String[] iconsName;
        int pos = 0;
        ArrayList<Boolean> values = new ArrayList<Boolean>();
        ArrayList<String> nomesJogos = new ArrayList<String>();
        //icons.clear();
        //usuarioViewModel.usuario.getValue().getJogos().
        usuarioViewModel.usuario.getValue().getJogos().forEach((k, v)->{
            nomesJogos.add(k);
        });
        Collections.sort(nomesJogos);

        for (String jogo : nomesJogos){
            values.add(usuarioViewModel.usuario.getValue().jogos.get(jogo));
        }

        //values = usuarioViewModel.usuario.getValue().getJogos().values().toArray(new Boolean[0]);
        iconsName = getResources().getStringArray(R.array.icons_name);

        for (String icon : iconsName) {
            if(values.get(pos)){
                icons.add(getResources().getIdentifier(icon, "drawable", getActivity().getPackageName()));
            }
            pos++;
        }



    }
}