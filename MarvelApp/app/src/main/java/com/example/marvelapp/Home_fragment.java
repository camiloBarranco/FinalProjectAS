package com.example.marvelapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelapp.adapters.HeroAdapter;
import com.example.marvelapp.classes.Hero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Home_fragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView rcv_heroes;
    SearchView search_hero;
    HeroAdapter adapter;
    private static final String TAG = "Home";
    List<Hero> listHeroes = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rcv_heroes = view.findViewById(R.id.rcv_heroes);
        search_hero = view.findViewById(R.id.search_hero);

        cargarInformacion(null);

        rcv_heroes.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new HeroAdapter(listHeroes, new HeroAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Hero dato) {
                moveToDescription(dato);
            }
        });
        rcv_heroes.setAdapter(adapter);

        search_hero.setOnQueryTextListener(this);



        return view;
    }

    public void  cargarInformacion(String query){

        String baseUrl = "https://gateway.marvel.com:443/v1/public/characters?ts=1&apikey=5bea1895c686d4d56c13df8cf0421d1e&hash=4bc2c77e57fac3cd2e8e6b668decc3d6&limit=10";
        String url = query != null && !query.isEmpty() ? baseUrl + "&nameStartsWith=" + query : baseUrl;

        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recibirRespuesta(new JSONObject(response));
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(myRequest);
    }

    public void recibirRespuesta( JSONObject respuesta){
        try {
            listHeroes.clear();
            //recorremos el json (los personajes)
            //Log.i(TAG, respuesta);
            JSONArray resultsArray = respuesta.getJSONObject("data").getJSONArray("results");
            for (int i=0;i< resultsArray.length();i++){
                int id = resultsArray.getJSONObject(i).getInt("id");
                String nombre = resultsArray.getJSONObject(i).getString("name");
                String descripcion = resultsArray.getJSONObject(i).getString("description");
                //obtenemos la imagen
                String image = resultsArray.getJSONObject(i).getJSONObject("thumbnail").getString("path");
                String extension = resultsArray.getJSONObject(i).getJSONObject("thumbnail").getString("extension");
                Log.i(TAG, image);
                String modified = resultsArray.getJSONObject(i).getString("modified");

                //creamos un objeto de la clase Personaje para cada personaje
                Hero p = new Hero(id,nombre,descripcion,image,extension,modified);
                Log.d(TAG, p.getImage());

                listHeroes.add(p);
            }

            adapter.notifyDataSetChanged();

        }catch (JSONException e){
            e.printStackTrace();//imprime el error en consola
            Toast.makeText(getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
        }
    }
    public void moveToDescription(Hero dato) {
        Intent intent = new Intent(this.getContext(),HeroDescription.class);
        intent.putExtra("Hero",dato);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cargarInformacion(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        cargarInformacion(newText);
        //adapter.filter(newText);
        return false;
    }
}