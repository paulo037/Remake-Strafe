package com.ufv.strafe.ui.fragmentos;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufv.strafe.controller.JogarController;
import com.ufv.strafe.databinding.FragmentJogarBinding;
import com.ufv.strafe.ui.utils.ItemJogarAdapter;

import java.util.ArrayList;

public class JogarFragment extends Fragment  {


    private NavController navController;
    private FragmentJogarBinding binding;
    private JogarController jogarController;
    public JogarFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentJogarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        jogarController = new JogarController(this);
        jogarController.userObserve(getViewLifecycleOwner(), binding, getActivity());

        return view;
    }


    public void updatePerfil(ArrayList<Integer> iconsSeusJogos,ArrayList<Integer> iconsOutrosJogos, String valueOf, String toUpperCase, String fotoPerfil) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        ItemJogarAdapter adapter = new ItemJogarAdapter(iconsSeusJogos);
        binding.recycleSeusEEsports.setAdapter(adapter);
        binding.recycleSeusEEsports.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        ItemJogarAdapter adapter2 = new ItemJogarAdapter(iconsOutrosJogos);
        binding.recycleOutrosEEsports.setAdapter(adapter2);
        binding.recycleOutrosEEsports.setLayoutManager(layoutManager2);
        if (iconsSeusJogos.isEmpty()){
            binding.labelSeusEEsports.setText("");
        }

        if (iconsOutrosJogos.isEmpty()){
            binding.labelOutrosEEsports.setText("");
        }


    }
}