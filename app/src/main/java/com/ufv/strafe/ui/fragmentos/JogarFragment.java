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
import com.ufv.strafe.ui.Adapters.ItemJogarAdapter;
import com.ufv.strafe.ui.Adapters.SeusDesafiosAdapter;

import java.util.ArrayList;

public class JogarFragment extends Fragment  {



    private FragmentJogarBinding binding;
    private JogarController jogarController;
    private ItemJogarAdapter seusJogosAdapter;
    private ItemJogarAdapter outrosJogosAdapter;
    private SeusDesafiosAdapter seusDesafiosAdapter;

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

        inicializarAdapters();
        jogarController.partidaObserve(getViewLifecycleOwner(), seusJogosAdapter, outrosJogosAdapter, binding);



        return view;
    }

    private void inicializarAdapters(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                                                            RecyclerView.VERTICAL,
                                                            false);

        seusJogosAdapter  =  new ItemJogarAdapter(new ArrayList<>(),
                                                    new ArrayList<>(),
                                                    new ArrayList<>(),
                                                    getActivity());

        binding.recycleSeusEEsports.setAdapter(seusJogosAdapter);
        binding.recycleSeusEEsports.setLayoutManager(layoutManager);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),
                                                                RecyclerView.VERTICAL,
                                                                false);

        outrosJogosAdapter = new ItemJogarAdapter(new ArrayList<>(),
                                                    new ArrayList<>(),
                                                    new ArrayList<>(),
                                                        getActivity());

        binding.recycleOutrosEEsports.setAdapter(outrosJogosAdapter);
        binding.recycleOutrosEEsports.setLayoutManager(layoutManager2);

        SeusDesafiosAdapter seusDesafiosAdapter = new SeusDesafiosAdapter();
        binding.recycleSeusDesafios.setAdapter(seusDesafiosAdapter);

    }




}