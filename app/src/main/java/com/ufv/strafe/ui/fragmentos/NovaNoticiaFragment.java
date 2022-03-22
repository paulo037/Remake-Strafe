package com.ufv.strafe.ui.fragmentos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.ufv.strafe.R;
import com.ufv.strafe.controller.CadastrarController;
import com.ufv.strafe.controller.NovaNoticiaController;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;
import com.ufv.strafe.databinding.FragmentNovaNoticiaBinding;
import com.ufv.strafe.model.utils.Resource;

import java.io.IOException;
import java.util.ArrayList;


public class NovaNoticiaFragment extends Fragment {
    private FragmentNovaNoticiaBinding binding;
    private NovaNoticiaController controller;


    public NovaNoticiaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNovaNoticiaBinding.inflate(inflater, container, false);

        controller = new NovaNoticiaController(getActivity(), binding, this);

        controller.observe(getLifecycle());



        binding.buttonPhoto.setOnClickListener(view -> {

          controller.selectImg();

        });

        String[] jogos = getActivity().getResources().getStringArray(R.array.e_esports);
        ArrayList<Chip> chips = new ArrayList<>();
        for(String j : jogos){
            Chip chip = new Chip(getContext());
            chip.setChecked(true);
            chip.setText(j);
            chips.add(chip);

        }

        for (Chip chip : chips){
            chip.setOnClickListener(view -> {
                chip.setChecked(!chip.isChecked());
                if (chip.isChecked()){
                    chip.setBackgroundColor(Resource.getColorByName("rocho", getContext()));
                    chip.setTextColor(Resource.getColorByName("rocho", getContext()));
                }
            });
            binding.containerChips.addView(chip);
        }


        binding.container.setOnClickListener(view -> {
          binding.entryText.requestFocus();
        });

        return binding.getRoot();
    }
}