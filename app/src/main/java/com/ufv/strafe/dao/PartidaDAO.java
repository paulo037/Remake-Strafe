package com.ufv.strafe.dao;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.okhttp.internal.DiskLruCache;
import com.ufv.strafe.model.Partida;
import com.ufv.strafe.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PartidaDAO {
    public MutableLiveData<Partida> partida = new MutableLiveData<>();
    public MutableLiveData<List<Partida>> partidas = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PartidaDAO(String idPartida) {
        FirebaseFirestore.getInstance().collection("/partidas")
                .document(idPartida)
                .addSnapshotListener((value, error) -> {
                    if (error != null || !value.exists()) {
                        return;
                    }
                    partida.setValue(Objects.requireNonNull(value).toObject(Partida.class));
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PartidaDAO() {
        FirebaseFirestore.getInstance()
                .collectionGroup("partidas")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        return;
                    }
                    partidas.setValue(value.toObjects(Partida.class));
                    //updatePartidasVencidas();
                });

    }

    public void createPartida(Partida partida) {
        //Adicionando no Banco de dados
        FirebaseFirestore.getInstance().collection("partidas")
                .document(partida.getId())
                .set(partida);
    }


    public void addAposta(String idAposta,
                          String time,
                          Double valor,
                          Double multiplicador) {
        if (partida.getValue() == null) {
            return;
        }
        partida.getValue().addAposta(idAposta, time, valor, multiplicador);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updatePartida() {
        FirebaseFirestore.getInstance().collection("partidas")
                .document(partida.getValue().getId())
                .set(partida.getValue(), SetOptions.merge());

    }

}
