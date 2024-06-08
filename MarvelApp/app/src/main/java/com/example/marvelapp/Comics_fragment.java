package com.example.marvelapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelapp.classes.Comic;
import com.example.marvelapp.classes.Hero;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Comics_fragment extends Fragment {
    Spinner spn_comics;
    Button btn_adquirir;
    ImageView imgV1;
    List<Comic> listComics = new ArrayList<>();
    private static final String TAG = "Home";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_comics, container, false);

        spn_comics = view.findViewById(R.id.spn_comics);
        btn_adquirir = view.findViewById(R.id.btn_adquirir);
        imgV1= view.findViewById(R.id.imgV1);


        cargarInformacion();


        spn_comics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();

                if (!selectedItem.equals("Seleccione")) {
                    Toast.makeText(parent.getContext(), "Seleccionado: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

                    if (position >= 0 && position < listComics.size()) {
                        Comic selectedComic = listComics.get(position);
                        Toast.makeText(parent.getContext(), "Seleccionado: " + selectedComic.getTitle(), Toast.LENGTH_LONG).show();

                        // Aquí puedes utilizar el objeto Comic seleccionado para hacer más cosas.
                        // Por ejemplo, cargar una imagen específica o mostrar más detalles del cómic.
                        String comicImage = selectedComic.getImageUrl() + "." + selectedComic.getExtension();
                        Picasso.get()
                                .load(comicImage)
                                .into(imgV1);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_adquirir.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Comic adquirido", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void  cargarInformacion(){

        String url = "https://gateway.marvel.com:443/v1/public/comics?apikey=5bea1895c686d4d56c13df8cf0421d1e&ts=1&hash=4bc2c77e57fac3cd2e8e6b668decc3d6&limit=10";

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
            listComics.clear();
            //recorremos el json (los personajes)
            //Log.i(TAG, respuesta);
            JSONArray resultsArray = respuesta.getJSONObject("data").getJSONArray("results");
            for (int i=0;i< resultsArray.length();i++){
                String nombre = resultsArray.getJSONObject(i).getString("title");
                String descripcion = resultsArray.getJSONObject(i).getString("description");
                //obtenemos la imagen
                String image = resultsArray.getJSONObject(i).getJSONObject("thumbnail").getString("path");
                String extension = resultsArray.getJSONObject(i).getJSONObject("thumbnail").getString("extension");
                Log.i(TAG, image);
                String heroes = "";

                for (int j=0;j<resultsArray.getJSONObject(i).getJSONObject("characters").getJSONArray("items").length();j++){
                    heroes += resultsArray.getJSONObject(i).getJSONObject("characters").getJSONArray("items").getJSONObject(j).getString("name");
                    heroes +=" - ";
                };


                //creamos un objeto de la clase Personaje para cada personaje
                Comic p = new Comic(nombre,descripcion,image,extension,heroes);

                listComics.add(p);
            }
            List<String> comicTitles = new ArrayList<>();
            for (Comic comic : listComics) {
                comicTitles.add(comic.getTitle());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, comicTitles);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_comics.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();//imprime el error en consola
            Toast.makeText(getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
        }
    }
}