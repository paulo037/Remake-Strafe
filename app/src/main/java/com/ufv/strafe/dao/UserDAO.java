package com.ufv.strafe.dao;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.ufv.strafe.databinding.ActivityCadastrarBinding;
import com.ufv.strafe.model.Usuario;
import com.ufv.strafe.ui.fragmentos.Configuracoes;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class UserDAO {

    public MutableLiveData<Usuario> usuario = new MutableLiveData<>();

    public UserDAO() {

        if (!verifyAuthentication()) {
            return;
        }
        FirebaseFirestore.getInstance().collection("/usuarios")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addSnapshotListener((value, error) -> {
                    usuario.setValue(Objects.requireNonNull(value).toObject(Usuario.class));
                    Log.i("usuario", Objects.requireNonNull(usuario.getValue()).getNome());

                });
    }


    public void updateUser() {
        FirebaseFirestore.getInstance().collection("usuarios")
                .document(usuario.getValue().getId())
                .set(usuario.getValue(), SetOptions.merge());
    }

    public Boolean verifyAuthentication() {
        if (FirebaseAuth.getInstance().getUid() == null) {
            return false;
        }
        return true;
    }

    public MutableLiveData<Usuario> getLiveData() {
        return usuario;
    }


    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public Map<String, Boolean> getJogos() {
        return usuario.getValue().getJogos();
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
                               FragmentManager supportFragmentManager,
                               ActivityCadastrarBinding binding) {

        FirebaseApp.initializeApp(context);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("sucesso", task.getResult().getUser().getUid());
                        saveUserInfireBase(context, nome, uriSelect, fileName, supportFragmentManager, binding);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("erro", e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                });
    }

    public void saveUserInfireBase(Context context,
                                   String nome,
                                   Uri uriSelect,
                                   String filename,
                                   FragmentManager fragmentManager,
                                   ActivityCadastrarBinding binding) {


        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images" + filename);

        ref.putFile(uriSelect)
                .addOnSuccessListener(taskSnapshot -> {

                    ref.getDownloadUrl().addOnSuccessListener(uri -> {

                        //Criando novo usuario
                        String uId = FirebaseAuth.getInstance().getUid();
                        String fotoPerfil = uri.toString();
                        Integer saldo = 300;
                        Integer acertos = 0;
                        Integer erros = 0;
                        Usuario usuario = new Usuario(nome, uId, fotoPerfil, saldo, acertos, erros);
                        usuario.inicializaJogos();

                        //Adicionando no Banco de dados
                        FirebaseFirestore.getInstance().collection("usuarios")
                                .document(usuario.getId().toString())
                                .set(usuario)
                                .addOnSuccessListener(unused -> {

                                    Toast.makeText(context, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    Configuracoes fragment = new Configuracoes();
                                    fragmentTransaction.add(R.id.jogosFavoritosCadastrar, fragment);
                                    fragmentTransaction.commit();
                                    binding.cadastroVisibilit.setVisibility(View.INVISIBLE);

                                })
                                .addOnFailureListener(e -> Log.e("erro", e.getMessage(), e));

                    });

                })
                .addOnFailureListener(e -> Log.e("erro", e.getMessage()));


    }


}



