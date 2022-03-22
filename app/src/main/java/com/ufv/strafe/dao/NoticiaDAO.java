package com.ufv.strafe.dao;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ufv.strafe.model.Noticia;
import com.ufv.strafe.model.Partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NoticiaDAO {

    public MutableLiveData<List<Noticia>> noticias = new MutableLiveData<>();
    public MutableLiveData<Noticia> noticia = new MutableLiveData<>();

    public NoticiaDAO() {
        FirebaseFirestore.getInstance()
                .collectionGroup("noticias")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        return;
                    }
                    noticias.setValue(value.toObjects(Noticia.class));
                });
    }

    public NoticiaDAO(String id) {
        FirebaseFirestore.getInstance().collection("/noticias")
                .document(id)
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        return;
                    }
                    noticia.setValue(Objects.requireNonNull(value).toObject(Noticia.class));
                });
    }


    public void createNoticia(Noticia noticia) {
        //Adicionando no Banco de dados
        FirebaseFirestore.getInstance().collection("noticias")
                .document(noticia.getId())
                .set(noticia);
    }
}
