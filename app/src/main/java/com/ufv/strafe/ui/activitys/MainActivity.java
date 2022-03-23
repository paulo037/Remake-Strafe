package com.ufv.strafe.ui.activitys;





import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.R;
import com.ufv.strafe.conexao.ConexaoController;
import com.ufv.strafe.conexao.NetworkChangeListener;


public class MainActivity extends AppCompatActivity  {

    private BottomNavigationView btnNav;
    private NavController navCtr;
    private NetworkChangeListener listener= new NetworkChangeListener();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(toString(), "inicializando MainActivity");
        //conectando o navController com o bottom navigation
        navCtr = Navigation.findNavController(this, R.id.nav_host_fragment);
        btnNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(btnNav, navCtr);
        verifyAuthentication();
        Log.i(toString(), "MainActivity inicializada");
    }



    public void verifyAuthentication(){
        if (FirebaseAuth.getInstance().getUid() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener, filter);
        super.onStart();

    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }
}