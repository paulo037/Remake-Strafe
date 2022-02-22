package com.ufv.strafe.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ufv.strafe.R;
import com.ufv.strafe.controller.CadastrarController;
import com.ufv.strafe.databinding.ActivityCadastrarBinding;
import com.ufv.strafe.model.Aposta;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.model.Usuario;


import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class UserDAO {

    public MutableLiveData<Usuario> usuario = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public UserDAO() {

        if (!verifyAuthentication()) {
            return;
        }
        FirebaseFirestore.getInstance().collection("/usuarios")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addSnapshotListener((value, error) -> {
                    if (error == null && value != null) {
                        usuario.setValue(Objects.requireNonNull(value).toObject(Usuario.class));
                        updateApostas();
                    }

                });
    }


    public void updateUser() {
        FirebaseFirestore.getInstance().collection("usuarios")
                .document(usuario.getValue().getId())
                .set(usuario.getValue(), SetOptions.merge());
    }


    public Boolean verifyAuthentication() {
        return FirebaseAuth.getInstance().getUid() != null;
    }

    public MutableLiveData<Usuario> getLiveData() {
        return usuario;
    }


    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public Map<String, Boolean> getJogos() {
        if (usuario.getValue() != null) {
            return usuario.getValue().getJogos();
        }
        return new HashMap<>();
    }


    public void putJogos(String key, Boolean valor) {
        usuario.getValue().putJogos(key, valor);
    }


    public void createDataUser(Context context,
                               String nome,
                               String email,
                               String senha,
                               Uri uriSelect,
                               String fileName,
                               CadastrarController controller) {

        FirebaseApp.initializeApp(context);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("sucesso", task.getResult().getUser().getUid());
                        saveUserInfireBase(
                                nome,
                                uriSelect,
                                fileName,
                                controller);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("erro", e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                });
    }

    public void saveUserInfireBase(
            String nome,
            Uri uriSelect,
            String filename,
            CadastrarController controller) {


        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images" + filename);

        ref.putFile(uriSelect)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {

                    //Criando novo usuario
                    String uId = FirebaseAuth.getInstance().getUid();
                    String fotoPerfil = uri.toString();
                    Usuario usuario = new Usuario(nome, uId, fotoPerfil);
                    usuario.inicializaJogos();

                    //Adicionando no Banco de dados
                    FirebaseFirestore.getInstance().collection("usuarios")
                            .document(usuario.getId())
                            .set(usuario)
                            .addOnSuccessListener(unused -> controller.config())
                            .addOnFailureListener(e -> Log.e("erro", e.getMessage(), e));

                }))
                .addOnFailureListener(e -> Log.e("erro", e.getMessage()));


    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateApostas() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        Map<String, ArrayList<String>> apostas = Objects.requireNonNull(usuario.getValue()).getApostas();
        if (apostas == null) return;
        apostas.forEach((s, bDApostas) -> {
            fb.collection("/partidas")
                    .document(s)
                    .addSnapshotListener((value, error) -> {
                        try {
                            if (value == null) return;
                            Partida partida = value.toObject(Partida.class);
                            if (partida == null) return;

                            Double vAposta = partida.getSaldoNApostas(bDApostas);
                            addSaldo(vAposta);

                            if (partida.verificaFim()) {
                                removeAposta(s);
                                updateUser();
                            }
                        } catch (Exception e) {
                            Log.i("err", "erro ao atualizar aposta");
                        }
                    });


        });

    }

    private void removeAposta(String aposta) {
        Objects.requireNonNull(usuario.getValue()).removeApostas(aposta);
    }

    public void addSaldo(Double valor) {
        usuario.getValue().addSaldo(valor);
    }


    public void addAposta(String idPartida, String idAposta, Double valor) {
        usuario.getValue().addAposta(idPartida, idAposta, valor);
    }


}

