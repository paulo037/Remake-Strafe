package com.ufv.strafe.ui.activitys;


import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.R;
import com.ufv.strafe.controller.LoginController;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;
import com.ufv.strafe.databinding.ActivityLoginBinding;

import java.util.Objects;

//TODO Gif de carregar
public class LoginActivity extends AppCompatActivity {


    private LoginController loginController;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        loginController = new LoginController(this);

        binding.btnEntrarLogin.setOnClickListener(view -> {
            String email = String.valueOf(binding.entryLoginEmail.getText());
            String senha = String.valueOf(binding.entryLoginSenha.getText());
            //verifica se algum entry está nulo
            if ((email.isEmpty()) || (senha.isEmpty())) {
                Toast.makeText(LoginActivity.this, "Preencha Todos os compos", LENGTH_LONG).show();
                return;
            }

            loginController.Login(email, senha);

        });

        binding.btnCriarConta.setOnClickListener(view -> criarConta());

        setContentView(binding.getRoot());
    }


    private void criarConta() {
        Intent intent = new Intent(this, CadastrarActivity.class);
        startActivity(intent);
    }

    public void login() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle(1);
        bundle.getInt("fragment", R.layout.fragment_calendario);
        startActivity(intent, bundle);
    }

    public void emailOuSenhaIncorretos() {
        binding.erroLogin.setVisibility(View.VISIBLE);
    }

    public void progress(Boolean visible) {
        if (visible){
            binding.progressCircularLogin.setVisibility(View.VISIBLE);
            return;
        }
        binding.progressCircularLogin.setVisibility(View.INVISIBLE);

    }

    public void erroMessage(String erro) {
        Toast.makeText(LoginActivity.this, erro, LENGTH_LONG).show();
    }
}