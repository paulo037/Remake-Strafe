package com.ufv.strafe.activitys;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ufv.strafe.R;
import com.ufv.strafe.entities.usuario.Usuario;
import com.ufv.strafe.ui.configuracoes.GamesFavoritosFragment;

import java.util.UUID;

public class CadastrarActivity extends AppCompatActivity {

    private EditText entryNome;
    private EditText entryEmail;
    private EditText entrySenha;
    private Button btnCadastrar;
    private Button btnPhoto;
    private ImageView mImgPhoto;
    private Uri uriSelect;
    private NavController navController;
    private MyLifecycleObserver mObserver;


//TODO Gif de carregar
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        entryNome = findViewById(R.id.entryCadastroNome);
        entryEmail = findViewById(R.id.entryCadastroEmail);
        entrySenha = findViewById(R.id.entryCadastroSenha);
        btnCadastrar = findViewById(R.id.button_cadastrar);
        btnPhoto = findViewById(R.id.button_photo);
        mImgPhoto = findViewById(R.id.photo_view);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObserver.selectImage();
            }

        });

        mObserver = new MyLifecycleObserver(getActivityResultRegistry());
        getLifecycle().addObserver(mObserver);


    }


    private void createUser() {
        //pega os dados da tela
        String nome = entryNome.getText().toString();
        String email = entryEmail.getText().toString();
        String senha = entrySenha.getText().toString();

        //verifica se os dados estão todos preenchidos
        if ((nome.isEmpty()) || (email.isEmpty()) || (senha.isEmpty())) {
            Toast.makeText(this, "Preencha Todos os compos", Toast.LENGTH_LONG).show();
            return;
        }

        //verifica se o usuario selecionou uma foto
        if (uriSelect == null) {
            Toast.makeText(this, "selecione uma foto", Toast.LENGTH_LONG).show();
            return;
        }

        //Cria a autenticação para login do usuáiro
        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("sucesso", task.getResult().getUser().getUid());
                            saveUserInfireBase();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("erro", e.getMessage());
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });



    }

    //salvar usuario criado no fire base
    private void saveUserInfireBase() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images" + filename);
        if (uriSelect == null) {
            Toast.makeText(getBaseContext(), "selecione uma foto", Toast.LENGTH_LONG).show();
            return;
        } else {
            ref.putFile(uriSelect)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //Criando novo usuario
                                    String uId = FirebaseAuth.getInstance().getUid();
                                    String nome = entryNome.getText().toString();
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
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getBaseContext(), "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                    GamesFavoritosFragment fragment = new GamesFavoritosFragment();
                                                    fragmentTransaction.add(R.id.jogosFavoritosCadastrar, fragment);
                                                    fragmentTransaction.commit();
                                                    View cadastro = findViewById(R.id.cadastroVisibilit);
                                                    cadastro.setVisibility(View.INVISIBLE);

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("erro", e.getMessage(), e);
                                                }
                                            });

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("erro", e.getMessage());
                        }
                    });
        }

    }


    class MyLifecycleObserver implements DefaultLifecycleObserver {
        private final ActivityResultRegistry mRegistry;
        private ActivityResultLauncher<String> mGetContent;

        MyLifecycleObserver(@NonNull ActivityResultRegistry registry) {
            mRegistry = registry;
        }

        public void onCreate(@NonNull LifecycleOwner owner) {
            mGetContent = mRegistry.register("0", owner, new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri uri) {
                            Bitmap bitmap = null;
                            try {
                                uriSelect = uri;
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                mImgPhoto.setImageDrawable(new BitmapDrawable(bitmap));
                                btnPhoto.setAlpha(0);
                            } catch (Exception e) {
                                Log.e("erro", e.getMessage());
                            }
                        }
                    });
        }

        //seleciona imagem
        public void selectImage() {
            mGetContent.launch("image/*");
        }
    }


}


