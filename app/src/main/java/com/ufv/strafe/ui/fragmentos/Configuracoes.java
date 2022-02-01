package com.ufv.strafe.ui.fragmentos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ufv.strafe.R;
import com.ufv.strafe.databinding.FragmentConfiguracoesBinding;
import com.ufv.strafe.controller.ConfiguracoesController;
import com.ufv.strafe.ui.activitys.MainActivity;
import com.ufv.strafe.ui.utils.ItemJogoAdapter;

import java.util.ArrayList;
import java.util.Map;


public class Configuracoes extends Fragment {

    private FragmentConfiguracoesBinding binding;
    private ConfiguracoesController configuracoesController;

    public Configuracoes() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentConfiguracoesBinding.inflate(inflater);

        configuracoesController = new ConfiguracoesController(this);

        //Observa mudanças no  usuario
        configuracoesController.Observe(getViewLifecycleOwner(), binding, getActivity());


        //Atualiza as mudanças nos jogos exibidos
        binding.buttonConfirmarMudancas.setOnClickListener(view -> {
            configuracoesController.updateJogos();

            if (container.getId() == R.id.jogosFavoritosCadastrar) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_gamesFavoritosFragment2_to_page_perfil);
            }
        });


        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void update(ItemJogoAdapter adapter){
        binding.recycleJogos.setAdapter(adapter);
        binding.recycleJogos.setLayoutManager(new LinearLayoutManager(getActivity()));
    }



}