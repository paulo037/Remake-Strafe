package com.ufv.strafe.ui.activitys;





import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ufv.strafe.R;
import com.ufv.strafe.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity  {

    private BottomNavigationView btnNav;
    private NavController navCtr;
    private ActivityMainBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //conectando o navController com o bottom navigation
        navCtr = Navigation.findNavController(this, R.id.nav_host_fragment);
        btnNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(btnNav, navCtr);
        if (getIntent().getExtras() != null){
            navCtr.navigate(getIntent().getExtras().getInt("fragment"));
        }
        verifyAuthentication();

    }



    public void verifyAuthentication(){
        if (FirebaseAuth.getInstance().getUid() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


}