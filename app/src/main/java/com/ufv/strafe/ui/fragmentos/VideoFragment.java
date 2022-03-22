package com.ufv.strafe.ui.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufv.strafe.R;
import com.ufv.strafe.controller.VideoController;
import com.ufv.strafe.databinding.FragmentVideoBinding;
import com.ufv.strafe.model.Video;

import java.util.Calendar;


public class VideoFragment extends Fragment {


    private FragmentVideoBinding binding;
    private VideoController controller;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentVideoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        controller = new VideoController();


        controller.Observer(getViewLifecycleOwner(), binding, getContext());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}