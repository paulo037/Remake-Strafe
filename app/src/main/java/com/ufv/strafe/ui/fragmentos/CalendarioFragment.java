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
        //createPartidas();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void createPartidas() {


        ArrayList<String> jogos = new ArrayList<>();
        jogos.add("Call of Duty");
        jogos.add("Counter-Strike");
        jogos.add("Dota II");
        jogos.add("Hearthstone");
        jogos.add("League of Legends");
        jogos.add("Overwatch");
        jogos.add("Rainbow 6 Siege");
        jogos.add("Rocket League");
        jogos.add("StarCraft");
        jogos.add("Valorant");

        ArrayList<ArrayList<String>> times = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();
        t = new ArrayList<>();
        t.add("MINNESOTA ROKKR");
        t.add("MUTANTES DA FLÓRIDA");
        times.add(t);
        t = new ArrayList<>();
        t.add("Cloud9");
        t.add("Virtus Pro");
        times.add(t);
        t = new ArrayList<>();
        t.add("Vici Gaming VG");
        t.add("Evil Geniuses EG");
        times.add(t);
        t = new ArrayList<>();
        t.add("Vivo Keyd");
        t.add("paiN");
        times.add(t);
        t = new ArrayList<>();
        t.add("INTZ e-Sports");
        t.add("KaBuM!");
        times.add(t);
        t = new ArrayList<>();
        t.add("London Spitfire");
        t.add("Philadelphia Fusion");
        times.add(t);
        t = new ArrayList<>();
        t.add("Ninjas in Pyjamas");
        t.add("Team One");
        times.add(t);
        t = new ArrayList<>();
        t.add("The General NRG");
        t.add("Team BDS");
        times.add(t);
        t = new ArrayList<>();
        t.add("Serral");
        t.add("Dark");
        times.add(t);

        t = new ArrayList<>();
        t.add("Acend");
        t.add("Gambit Esports");
        times.add(t);


        Random random = new Random();
        for (int j = 24; j <= 31; j++){

                for (int i = 0; i < 10; i++) {
                    int h1 = random.nextInt(10) + 6;

                    int pt1 = random.nextInt(3);
                    int pt2 = random.nextInt(3);
                    pt2 = pt2 == pt1 ? pt1 + 1 : pt2;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Partida partida = null;
                    try {
                        partida = new Partida(
                                times.get(i).get(0),
                                times.get(i).get(1),
                                jogos.get(i),
                                sdf.parse(String.valueOf(j)+ "/03/2022 " + String.valueOf(h1) + ":00"),
                                sdf.parse(String.valueOf(j)+ "/03/2022 " +String.valueOf(h1+1 + i % 2) + ":00"),
                                String.valueOf(Calendar.getInstance().getTimeInMillis()),
                                pt1,
                                pt2

                        );
                    } catch (ParseException e) {

                    }
                    controller.createPartida(partida);
                }



        }

    }


}