package com.ufv.strafe.ui.configuracoes;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.ufv.strafe.R;

import com.ufv.strafe.activitys.MainActivity;
import com.ufv.strafe.databinding.FragmentGamesFavoritosBinding;
import com.ufv.strafe.entities.usuario.Usuario;
import com.ufv.strafe.entities.usuario.UsuarioViewModel;
import com.ufv.strafe.utils.ItemJogoAdapter;

import java.util.ArrayList;

import io.grpc.Context;


public class GamesFavoritosFragment extends Fragment {

    private FragmentGamesFavoritosBinding binding;
    private UsuarioViewModel viewModel;
    private  String jogos[];
    private  ArrayList<Integer> cores = new ArrayList<Integer>();

    public GamesFavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGamesFavoritosBinding.inflate(inflater);
        viewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);

        jogos = getResources().getStringArray(R.array.e_esports);
        getColors(cores);

        ItemJogoAdapter adapter = new ItemJogoAdapter(Context.ROOT, jogos, cores, getActivity());


        //Observa mudanças no  usuario
        adapter.viewModel.usuario.observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.recycleJogos.setAdapter(adapter);
                binding.recycleJogos.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        //Atualiza as mudanças nos jogos exibidos
        binding.buttonConfirmarMudancas.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                adapter.jogos.forEach((key, valor) -> {
                    adapter.viewModel.usuario.getValue().jogos.put(key, valor);
                });

                FirebaseFirestore.getInstance().collection("usuarios")
                        .document(adapter.viewModel.usuario.getValue().getId())
                        .set(adapter.viewModel.usuario.getValue(), SetOptions.merge());


                if (container.getId() == R.id.jogosFavoritosCadastrar) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_gamesFavoritosFragment2_to_page_perfil);
                }


            }
        });


        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void getColors( ArrayList<Integer> cores){
        String colorsName[];
        colorsName = getResources().getStringArray(R.array.colors_name);


        for (String cor : colorsName) {
            cores.add(getResources().getIdentifier(cor, "color", getActivity().getPackageName()));
        }
    }


}