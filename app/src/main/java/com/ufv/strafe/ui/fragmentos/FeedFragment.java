package com.ufv.strafe.ui.fragmentos;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.controller.DialogApostaController;
import com.ufv.strafe.databinding.FragmentFeedBinding;
import com.ufv.strafe.model.Partida;

import java.text.ParseException;
import java.util.Calendar;


public class FeedFragment extends Fragment {
    FragmentFeedBinding binding;
    DialogApostaController controller;
    Calendar data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentFeedBinding.inflate(inflater);

      controller = new DialogApostaController("1645031813002");

      binding.buttonFeed.setOnClickListener(view -> {
          Partida partida = null;
          data =Calendar.getInstance();

          String ganhador;
          if (Math.random() * 10 +1 > 5){
              ganhador = "Cruzeiro";
          }else{
              ganhador = "atletico";
          }

          try {
              partida = new Partida("Cruzeiro",
                      "atletico",
                      "Overwatch",
                      "16/02/2022 12:00:00",
                      "20/02/2022 12:39:00",
                      String.valueOf(data.getTimeInMillis()),
                      ganhador
                   );
          } catch (ParseException e) {
              e.printStackTrace();
          }

        controller.addPartida(partida);

      });

      binding.buttonAposta.setOnClickListener(view -> {
          controller.addAposta("1645031813002", "cruzeiro", 20.0, 1.5);
      });


      return binding.getRoot();
    }




}