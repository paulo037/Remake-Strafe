package com.ufv.strafe.ui.fragmentos;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mooveit.library.Fakeit;
import com.ufv.strafe.R;
import com.ufv.strafe.controller.DialogApostaController;
import com.ufv.strafe.controller.FeedController;
import com.ufv.strafe.databinding.FragmentFeedBinding;
import com.ufv.strafe.model.Noticia;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.ui.Adapters.ItemNoticiaAdapter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;


public class FeedFragment extends Fragment {
    FragmentFeedBinding binding;
    FeedController controller;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fakeit.init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater);


        binding.floatingActionButton.setOnClickListener(view -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_page_feed_to_novaNoticiaFragment);


        });

        controller = new FeedController();
        controller.Observer(binding, getViewLifecycleOwner(),getContext());


        return binding.getRoot();
    }


}