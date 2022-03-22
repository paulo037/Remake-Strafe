package com.ufv.strafe.dao;


import android.net.Uri;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ufv.strafe.controller.CadastrarController;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.model.Usuario;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class UsuarioDAO {

    public MutableLiveData<Usuario> usuario = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Partida>> partidasArray = new MutableLiveData<>();
    public MutableLiveData<Map<String, Double>> recompensas = new MutableLiveData<>();

    public UsuarioDAO() {

        if (!verifyAuthentication()) {
            return;
        }
        partidasArray.setValue(new ArrayList<>());
        recompensas.setValue(new HashMap<>());
        FirebaseFirestore.getInstance().collection("/usuarios")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addSnapshotListener((value, error) -> {
                    if (error == null && value != null) {
                        usuario.setValue(Objects.requireNonNull(value).toObject(Usuario.class));
                    }

                });
    }


    public void updateUser() {
        FirebaseFirestore.getInstance().collection("usuarios")
                .document(usuario.getValue().getId())
                .set(usuario.getValue());
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


    public void removeAposta(String aposta) {
        Objects.requireNonNull(usuario.getValue()).removeAposta(aposta);
        updateUser();
    }

    public void addSaldo(Double valor) {
        Objects.requireNonNull(usuario.getValue()).addSaldo(valor);
    }


    public void addAposta(String idPartida, String idAposta, Double valor) {
        Objects.requireNonNull(usuario.getValue()).addAposta(idPartida, idAposta, valor);
    }

    public void updateStatisticas(Double v) {
        if (v > 0) {
            Objects.requireNonNull(usuario.getValue()).addAcertos();
        } else {
            Objects.requireNonNull(usuario.getValue()).addErros();
        }
    }


}

