package com.ufv.strafe.controller;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.dao.UsuarioDAO;
import com.ufv.strafe.ui.activitys.LoginActivity;

public class LoginController {
    private UsuarioDAO usuarioDAO;
    private LoginActivity loginActivity;


    public LoginController(LoginActivity loginActivity) {
        usuarioDAO = new UsuarioDAO();
        this.loginActivity = loginActivity;
    }


    public void Login(String email, String senha) {
        loginActivity.progress(true);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("sucesso", "Login efetuado com sucesso");
                        loginActivity.progress(false);
                        loginActivity.login();
                    }
                })
                .addOnFailureListener(e -> {
                    loginActivity.emailOuSenhaIncorretos();
                    loginActivity.erroMessage(e.getMessage());
                    loginActivity.progress(false);

                });
    }


}
