package com.ufv.strafe.ui.fragmentos;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.R;
import com.ufv.strafe.controller.CalendarioController;
import com.ufv.strafe.databinding.FragmentCalendarioBinding;
import com.ufv.strafe.databinding.FragmentJogarBinding;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.ui.Adapters.ItemCalendarioDayAdapter;
import com.ufv.strafe.ui.Adapters.ItemPartidaAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class CalendarioFragment extends Fragment {

    FragmentCalendarioBinding binding;
    CalendarioController controller;

    public CalendarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCalendarioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        controller = new CalendarioController();
        controller.Observer(binding, getViewLifecycleOwner(), getContext());
        inicializarAdapters();

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private void inicializarAdapters() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL,
                false);


        ArrayList<Date> datas = controller.getDatasSort();
        ItemCalendarioDayAdapter dayAdapter = new ItemCalendarioDayAdapter(datas, controller);

        binding.recyclerDias.setAdapter(dayAdapter);
        binding.recyclerDias.setLayoutManager(layoutManager);
        binding.recyclerDias.scrollToPosition(12);


        List<Partida> partidas = new ArrayList<>();
        ItemPartidaAdapter partidaAdapter = new ItemPartidaAdapter(partidas, getContext());
        binding.recycleJogosCalendario.setAdapter(partidaAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,
                false);
        binding.recycleJogosCalendario.setLayoutManager(layoutManager2);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }





}