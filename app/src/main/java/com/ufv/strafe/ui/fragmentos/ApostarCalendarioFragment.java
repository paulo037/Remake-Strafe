package com.ufv.strafe.ui.fragmentos;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufv.strafe.controller.ApostarController;
import com.ufv.strafe.controller.CalendarioController;
import com.ufv.strafe.databinding.FragmentApostarCalendarioBinding;
import com.ufv.strafe.ui.Adapters.ItemApostaAdapter;


public class ApostarCalendarioFragment extends Fragment {
    private FragmentApostarCalendarioBinding binding;
    private CalendarioController controller;
    private String idPartida;

    public ApostarCalendarioFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentApostarCalendarioBinding.inflate(inflater, container, false);


        idPartida= getArguments().getString("idPartida");
        controller = new CalendarioController(idPartida);


        binding.topAppBarAposta.setNavigationOnClickListener(view -> {
            getActivity().onBackPressed();
        });

        controller.ObserverPartida(binding, getViewLifecycleOwner(), this );
        return binding.getRoot();
    }

}