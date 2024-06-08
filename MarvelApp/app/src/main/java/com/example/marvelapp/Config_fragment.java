package com.example.marvelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelapp.classes.Hero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Config_fragment extends Fragment {
    TextView txt_nombre, txt_correo, txt_telefono,txt_edad,txt_birth;
    Button btn_cerrar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        txt_nombre = view.findViewById(R.id.txt_nombre);
        txt_correo = view.findViewById(R.id.txt_correo);
        txt_telefono = view.findViewById(R.id.txt_telefono);
        txt_edad = view.findViewById(R.id.txt_edad);
        txt_birth = view.findViewById(R.id.txt_birth);



        btn_cerrar = view.findViewById(R.id.btn_cerrar);

        sharedPreferences = getContext().getSharedPreferences(Login.dataUser, Login.modo_private);
        usuario = sharedPreferences.getString("usuario","No existe").toString();
        Log.d("usuario",usuario);
        editor= sharedPreferences.edit();

        txt_nombre.setText("Camilo Barranco");
        txt_correo.setText("admin@admin.com");
        txt_edad.setText("21");
        txt_birth.setText("14/08/2002");
        txt_telefono.setText("3136279267");
        btn_cerrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                editor.remove("usuario");
                editor.apply();
                Intent intent = new Intent(getActivity(),Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }


}