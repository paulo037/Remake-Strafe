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
                        Log.i(toString(), "Login efetuado com sucesso");
                        loginActivity.progress(false);
                        loginActivity.login();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(toString(), "Erro ao tenytar realizar o login");
                    loginActivity.emailOuSenhaIncorretos();
                    loginActivity.erroMessage(e.getMessage());
                    loginActivity.progress(false);

                });
    }


}
