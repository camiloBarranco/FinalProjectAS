package com.example.marvelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelapp.classes.Hero;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HeroDescription extends AppCompatActivity {
    TextView txt_title, txt_description,txt_comics,txt_stories;
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
        txt_comics = findViewById(R.id.txt_comics);
        txt_stories = findViewById(R.id.txt_stories);

        assert hero != null;
        Log.d("Hero", hero.getName());
        txt_title.setText(hero.getName());
        txt_description.setText(hero.getDescription());
        Picasso.get().load(hero.getImage()).into(img_hero);
        sharedPreferences = getSharedPreferences(Login.dataUser, Login.modo_private);
        editor= sharedPreferences.edit();

        cargarInformacion(hero.getId());

        btn_cerrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                editor.remove("usuario");
                editor.apply();
                Intent intent = new Intent(HeroDescription.this,Inicio.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void  cargarInformacion(int query){

        String baseUrl = "https://gateway.marvel.com:443/v1/public/characters/";
        String url = baseUrl+query + "?apikey=5bea1895c686d4d56c13df8cf0421d1e&ts=1&hash=4bc2c77e57fac3cd2e8e6b668decc3d6";

        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recibirRespuesta(new JSONObject(response));
                } catch (JSONException e) {
                    Toast.makeText(HeroDescription.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HeroDescription.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(myRequest);
    }

    public void recibirRespuesta( JSONObject respuesta){
        String comics="";
        String stories="";
        try {
            //recorremos el json (los personajes)
            //Log.i(TAG, respuesta);
            JSONObject resultsArray = respuesta.getJSONObject("data").getJSONArray("results").getJSONObject(0);
            for (int i=0;i< resultsArray.length();i++){
                for (int y = 0; y <resultsArray.getJSONObject("comics").getJSONArray("items").length();y++){
                    String comicName = resultsArray.getJSONObject("comics").getJSONArray("items").getJSONObject(y).getString("name");
                    comics +=" - "+ comicName+"\n";
                }
            }
            txt_comics.setText(comics);

            //Creacion de las historias
            for (int y = 0; y < resultsArray.getJSONObject("stories").getJSONArray("items").length();y++){
                String storyName = resultsArray.getJSONObject("stories").getJSONArray("items").getJSONObject(y).getString("name");
                stories +=" - "+ storyName+"\n";
            }
            txt_stories.setText(stories);

        }catch (JSONException e){
            e.printStackTrace();//imprime el error en consola
            Toast.makeText(this, "Error en el servidor", Toast.LENGTH_SHORT).show();
        }
    }
}