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

import com.ufv.strafe.R;
import com.ufv.strafe.databinding.FragmentCalendarioBinding;
import com.ufv.strafe.databinding.FragmentJogarBinding;
import com.ufv.strafe.ui.Adapters.ItemCalendarioDayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CalendarioFragment extends Fragment {

    FragmentCalendarioBinding binding;

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

        inicializarAdapters();
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private void inicializarAdapters() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL,
                false);


        ArrayList<Date> datas = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        for (int i = -15; i <= 15; i++ ){
           calendar.add(Calendar.DATE, i);
           datas.add(calendar.getTime());
           calendar = Calendar.getInstance();

        }

        datas.sort((date, t1) -> {
            if(date.after(t1))return 1;
            if(date.equals(t1))return 0;
            if(date.before(t1))return  -1;
            return 0;
        });

        ItemCalendarioDayAdapter dayAdapter = new ItemCalendarioDayAdapter(datas);

        binding.recyclerDias.setAdapter(dayAdapter);
        binding.recyclerDias.setLayoutManager(layoutManager);
        binding.recyclerDias.scrollToPosition(12);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}