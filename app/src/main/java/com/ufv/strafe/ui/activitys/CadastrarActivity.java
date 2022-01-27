package com.ufv.strafe.ui.activitys;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.ufv.strafe.controller.CadastrarController;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;


public class CadastrarActivity extends AppCompatActivity {

    private CadastrarController cadastrarController;

    private ActivityCadastrarBinding binding;



//TODO Gif de carregar

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityCadastrarBinding.inflate(getLayoutInflater());

        cadastrarController = new CadastrarController(getActivityResultRegistry(), binding, getContentResolver());

        cadastrarController.observe(getLifecycle());

        binding.buttonCadastrar.setOnClickListener( view -> cadastrarController.selectImg());

        binding.buttonPhoto.setOnClickListener(view ->    cadastrarController.selectImg());

        setContentView(binding.getRoot());
    }



}


