package com.ufv.strafe.ui.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ufv.strafe.R;
import com.ufv.strafe.controller.ConfiguracoesController;
import com.ufv.strafe.databinding.ActivityConfiguracoesBinding;
import com.ufv.strafe.ui.utils.ItemJogoAdapter;


public class ConfiguracoesActivity extends AppCompatActivity {

    private ActivityConfiguracoesBinding binding;
    private ConfiguracoesController configuracoesController;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConfiguracoesBinding.inflate(getLayoutInflater());

        configuracoesController = new ConfiguracoesController(this);

        //Observa mudanças no  usuario
        configuracoesController.Observe(this, this);


        //Atualiza as mudanças nos jogos exibidos
        binding.buttonConfirmarMudancas.setOnClickListener(view -> {
            configuracoesController.updateJogos();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Bundle bundle = new Bundle(1);
            bundle.putInt("fragment", R.id.action_page_calendario_to_page_perfil);
            intent.putExtras(bundle);
            startActivity(intent);

        });

        setContentView(binding.getRoot());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void update(ItemJogoAdapter adapter) {
        binding.recycleJogos.setAdapter(adapter);
        binding.recycleJogos.setLayoutManager(new LinearLayoutManager(this));
    }


}