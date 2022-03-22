package com.ufv.strafe.dao;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.ufv.strafe.model.Aposta;
import com.ufv.strafe.model.Partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PartidaDAO {
    public MutableLiveData<Partida> partida = new MutableLiveData<>();
    public MutableLiveData<List<Partida>> partidas = new MutableLiveData<>();


    public PartidaDAO(String idPartida) {
        FirebaseFirestore.getInstance().collection("/partidas")
                .document(idPartida)
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        return;
                    }
                    partida.setValue(Objects.requireNonNull(value).toObject(Partida.class));
                });

    }

    public PartidaDAO() {
        FirebaseFirestore.getInstance()
                .collectionGroup("partidas")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        return;
                    }
                    partidas.setValue(value.toObjects(Partida.class));
                });

    }

    public void createPartida(Partida partida) {
        //Adicionando no Banco de dados
        FirebaseFirestore.getInstance().collection("partidas")
                .document(partida.getId())
                .set(partida);
    }


    public void addAposta(Aposta aposta) {

        if (partida.getValue() == null) {
            return;
        }

        partida.getValue().addAposta(aposta);
    }


    public void updatePartida() {
        if (partida.getValue() == null) {
            return;
        }
        FirebaseFirestore.getInstance().collection("partidas")
                .document(partida.getValue().getId())
                .set(partida.getValue(), SetOptions.merge());

    }

    public List<Partida> getPartidasByDate(String data, Map<String, Boolean> jogos) {
        List<Partida> partidasByDate = new ArrayList<>();

        if (partidas.getValue() == null || jogos.size() < 1) return partidasByDate;

        for (Partida partida : partidas.getValue()) {
            String dataWithoutHours = partida.getDataIncio().substring(0, 10);
            if (data.equals(dataWithoutHours) && jogos.get(partida.getJogo())) {
                partidasByDate.add(partida);
            }
        }

        return partidasByDate;
    }


    public ArrayList<Partida> getPartidasRecompensas() {

        ArrayList<Partida> array = new ArrayList<>();

        if (this.partidas.getValue() == null) return array;


        for (Partida p : this.partidas.getValue()) {
            if (p.verificaFim()) {
                array.add(p);
            }
        }

        return array;
    }


}
