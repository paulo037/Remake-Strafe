package com.ufv.strafe.ui.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufv.strafe.R;
import com.ufv.strafe.controller.FeedController;
import com.ufv.strafe.databinding.FragmentReadNoticiaBinding;


public class ReadNoticiaFragment extends Fragment {

  FragmentReadNoticiaBinding binding;
  FeedController controller;

    public ReadNoticiaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReadNoticiaBinding.inflate(inflater, container, false);
        String idNoticia = getArguments().getString("id");
        controller = new FeedController(idNoticia);
        controller.ObserverNoticia(binding, getViewLifecycleOwner(), getContext());
        binding.topAppBarNovaNoticia.setNavigationOnClickListener(view -> {
            getActivity().onBackPressed();
        });
        return  binding.getRoot();
    }
}