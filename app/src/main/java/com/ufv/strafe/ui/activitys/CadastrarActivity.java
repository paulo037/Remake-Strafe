package com.ufv.strafe.ui.activitys;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ufv.strafe.databinding.ActivityCadastrarBinding;
import com.ufv.strafe.controller.CadastrarController;


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

        binding.buttonPhoto.setOnClickListener(view -> cadastrarController.selectImg());


        binding.buttonCadastrar.setOnClickListener(view -> {
            String nome = String.valueOf(binding.entryCadastroNome.getText());
            String email = String.valueOf(binding.entryCadastroEmail.getText());
            String senha = String.valueOf(binding.entryCadastroSenha.getText());
            cadastrarController.createUser(
                    binding,
                    getBaseContext(),
                    getSupportFragmentManager(),
                    nome,
                    email,
                    senha
            );
        });

        setContentView(binding.getRoot());
    }


}


