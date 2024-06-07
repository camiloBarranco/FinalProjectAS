package com.example.marvelapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Inicio extends AppCompatActivity {
    Home_fragment homeFragment = new Home_fragment();
    Comics_fragment comicsFragment = new Comics_fragment();
    Config_fragment callsFragment = new Config_fragment();

    //TextView txt1;
    FrameLayout frm_Inicio;
    BottomNavigationView nav_1;

    public void loadFragment(Fragment fr){
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.frm_inicio,fr);
        tr.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        frm_Inicio = findViewById(R.id.frm_inicio);
        nav_1 = findViewById(R.id.nav_1);



        nav_1.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home){
                    loadFragment(homeFragment);
                    return true;
                }else if (item.getItemId() == R.id.nav_inbox){
                    loadFragment(comicsFragment);
                    return true;
                }else if (item.getItemId() == R.id.nav_call){
                    loadFragment(callsFragment);
                    return true;
                }
                return false;
            }
        });

    }
}