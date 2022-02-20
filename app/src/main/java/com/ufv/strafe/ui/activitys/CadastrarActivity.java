package com.ufv.strafe.ui.activitys;


import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ufv.strafe.controller.CadastrarController;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;


public class CadastrarActivity extends AppCompatActivity {

    private CadastrarController cadastrarController;

    private ActivityCadastrarBinding binding;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityCadastrarBinding.inflate(getLayoutInflater());

        cadastrarController = new CadastrarController(this, binding);

        cadastrarController.observe(getLifecycle());

        binding.buttonPhoto.setOnClickListener(view -> cadastrarController.selectImg());


        binding.buttonCadastrar.setOnClickListener(view -> {

            String nome = String.valueOf(binding.entryCadastroNome.getText());
            String email = String.valueOf(binding.entryCadastroEmail.getText());
            String senha = String.valueOf(binding.entryCadastroSenha.getText());
            cadastrarController.createUser(nome, email, senha);
        });

        setContentView(binding.getRoot());
    }

    public void progress(Boolean visible) {
        if (visible) {
            binding.progressCircularCadastro.setVisibility(View.VISIBLE);
            return;
        }
        binding.progressCircularCadastro.setVisibility(View.INVISIBLE);

    }

}


