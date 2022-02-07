package com.ufv.strafe.controller;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.dao.UserDAO;
import com.ufv.strafe.ui.activitys.LoginActivity;

import java.util.Objects;

public class LoginController {
    private UserDAO userDAO;
    LoginActivity loginActivity;


    public LoginController(LoginActivity loginActivity) {
        userDAO = new UserDAO();
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

                    loginActivity.erroMessage(e.getMessage());
                    loginActivity.progress(false);

                });
    }


}
