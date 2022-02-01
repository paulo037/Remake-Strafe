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
                    switch (Objects.requireNonNull(e.getMessage())) {
                        case "An internal error has occurred. [ Read error:ssl=0xa6799b40: I/O error during system call, Connection reset by peer ]":
                            loginActivity.erroMessage("Falha na conexão");
                            break;
                        case "The password is invalid or the user does not have a password.":
                            loginActivity.erroMessage("Senha incorreta.");
                            break;
                        case "There is no user record corresponding to this identifier. The user may have been deleted.":
                            loginActivity.erroMessage("Nenhuma conta com esse email encontrada.");
                            break;
                        default:
                            loginActivity.erroMessage("Erro. Tente mais tarde.");
                            loginActivity.progress(false);
                    }
                    loginActivity.progress(false);
                                    });
    }


}
