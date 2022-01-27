package com.ufv.strafe.ui.activitys;


import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

//TODO Gif de carregar
public class LoginActivity extends AppCompatActivity  {

    private Button btnEntrar;
    private Button btnCriarConta;
    private TextInputEditText entryEmail;
    private TextInputEditText entrySenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnCriarConta = findViewById(R.id.btnCriarConta);
        btnEntrar = findViewById(R.id.btnEntrarLogin);
        entryEmail = findViewById(R.id.entryLoginEmail);
        entrySenha = findViewById(R.id.entryLoginSenha);

        btnEntrar.setOnClickListener( view -> {
            String email = entryEmail.getText().toString();
            String senha = entrySenha.getText().toString();

            //verifica se algum entry está nulo
            if ( (email.isEmpty()) || (senha.isEmpty())){
                Toast.makeText(LoginActivity.this, "Preencha Todos os compos", LENGTH_LONG).show();
                return;
            }

            //verifica se o usuario está logado
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task ->{
                        Log.i("Sucesso", task.getResult().getUser().getUid());
                        login();
                    } )
                    .addOnFailureListener(e -> Log.e("erro", e.getMessage()));

        });

        btnCriarConta.setOnClickListener(view ->  criarConta());



    }


    private void criarConta() {
        Intent intent = new Intent(this, CadastrarActivity.class);
        startActivity(intent);
    }

    public void login(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}