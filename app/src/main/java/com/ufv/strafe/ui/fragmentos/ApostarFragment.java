package com.ufv.strafe.ui.fragmentos;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufv.strafe.R;
import com.ufv.strafe.controller.ApostarController;
import com.ufv.strafe.controller.JogarController;
import com.ufv.strafe.databinding.FragmentApostarBinding;
import com.ufv.strafe.model.Aposta;
import com.ufv.strafe.ui.activitys.MainActivity;

public class ApostarFragment extends Fragment {
    private FragmentApostarBinding binding;
    private ApostarController controller;
    private String jogo;

    public ApostarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentApostarBinding.inflate(inflater, container, false);

        controller = new ApostarController(this);
        jogo = getArguments().getString("Jogo");
        controller.observe(jogo, binding, getViewLifecycleOwner(), getActivity());

        binding.topAppBarAposta.setNavigationOnClickListener(view -> {
            getActivity().onBackPressed();
        });
        return binding.getRoot();
    }

}