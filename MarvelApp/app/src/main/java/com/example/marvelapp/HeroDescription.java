package com.example.marvelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.marvelapp.classes.Hero;
import com.squareup.picasso.Picasso;

public class HeroDescription extends AppCompatActivity {
    TextView txt_title, txt_description;
    ImageView img_hero;
    Button btn_cerrar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_description);

        Hero hero = (Hero) getIntent().getSerializableExtra("Hero");
        txt_title = findViewById(R.id.txt_title);
        txt_description = findViewById(R.id.txt_description);
        img_hero = findViewById(R.id.img_usuario);
        btn_cerrar = findViewById(R.id.btn_cerrar);

        assert hero != null;
        Log.d("Hero", hero.getName());
        txt_title.setText(hero.getName());
        txt_description.setText(hero.getDescription());
        Picasso.get().load(hero.getImage()).into(img_hero);
        sharedPreferences = getSharedPreferences(Login.dataUser, Login.modo_private);
        editor= sharedPreferences.edit();

        btn_cerrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                editor.remove("usuario");
                editor.apply();
                Intent intent = new Intent(HeroDescription.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}