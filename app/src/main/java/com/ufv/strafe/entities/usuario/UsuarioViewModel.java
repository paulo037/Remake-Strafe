package com.ufv.strafe.entities.usuario;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UsuarioViewModel extends ViewModel {

   public MutableLiveData<Usuario> usuario = new MutableLiveData<Usuario>();


    public UsuarioViewModel() {
        FirebaseFirestore.getInstance().collection("/usuarios")
                .document(FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        usuario.setValue(value.toObject(Usuario.class));
                        Log.i("usuario", usuario.getValue().getNome());
                    }
                });

    }
}