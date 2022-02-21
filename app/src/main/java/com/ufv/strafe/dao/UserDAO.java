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
                        Log.i("usuario", Objects.requireNonNull(usuario.getValue()).getNome());
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
                    Double saldo = 300.0;
                    Integer acertos = 0;
                    Integer erros = 0;
                    Usuario usuario = new Usuario(nome, uId, fotoPerfil, saldo, acertos, erros);
                    usuario.inicializaJogos();

                    //Adicionando no Banco de dados
                    FirebaseFirestore.getInstance().collection("usuarios")
                            .document(usuario.getId().toString())
                            .set(usuario)
                            .addOnSuccessListener(unused -> {
                                controller.config();
                            })
                            .addOnFailureListener(e -> Log.e("erro", e.getMessage(), e));

                }))
                .addOnFailureListener(e -> Log.e("erro", e.getMessage()));


    }

    public void addAposta(String idPartida, String idAposta, Double valor) {
        usuario.getValue().addAposta(idPartida, idAposta);
        Double saldo = usuario.getValue().getSaldo();
        usuario.getValue().setSaldo(saldo - valor);

    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateApostas() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        Map<String, ArrayList<String>> apostas = usuario.getValue().getApostas();

        usuario.getValue().getApostas().forEach((s, bDApostas) -> {
            fb.collection("/partidas")
                    .document(s)
                    .addSnapshotListener((value, error) -> {
                        try {
                            Double vAposta = 0.0;
                            Partida partida = value.toObject(Partida.class);
                            for (String a : bDApostas) {
                                vAposta += partida.getSaldoAposta(a);
                            }
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date dataFim = dateFormat.parse(partida.getDataFim());
                            Date dataAtual = new Date();

                            if (dataAtual.after(dataFim) || dataAtual.equals(dataFim)) {
                                usuario.getValue().removeApostas(s);
                                if (vAposta > 0.0) {
                                    usuario.getValue().setSaldo(usuario.getValue().getSaldo() + vAposta);
                                }
                                updateUser();
                            }
                        } catch (Exception e) {

                        }
                    });


        });

    }

}

