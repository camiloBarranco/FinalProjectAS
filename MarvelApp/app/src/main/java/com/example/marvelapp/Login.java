package com.example.marvelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    Button btnLogin;

    EditText edtUser,edtPassword;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String dataUser = "dataUser";
    static final int modo_private = Context.MODE_PRIVATE;
    String dato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin =findViewById(R.id.btnLogin);
        edtUser =findViewById(R.id.edtUser);
        edtPassword =findViewById(R.id.edtPassword);

        sharedPreferences=getSharedPreferences(dataUser,modo_private);

        editor= sharedPreferences.edit();
        dato= getApplicationContext().getSharedPreferences(dataUser,modo_private).getString("usuario","0");

        if(!dato.equalsIgnoreCase("0")){
            Intent intent = new Intent(Login.this,Inicio.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cajita = edtUser.getText().toString().trim();
                String cajita2 =edtPassword.getText().toString().trim();

                loginRequest(cajita,cajita2);

                if (cajita.equals("") || cajita2.equals("")){
                    Toast.makeText(Login.this,"Digite los campos vacios",Toast.LENGTH_LONG).show();
                }
                else {
                    loginRequest(cajita,cajita2);

                }

            }
        });
    }

    public void loginRequest(String user_name, String password) {

        HashMap<String, Object> json = new HashMap<>();
        json.put("user_name", user_name);
        json.put("password", password);



        JSONObject jsonObject = new JSONObject(json);

        Log.d("JSONData", jsonObject.toString());

        String url = "http://10.0.2.2:8000/login";

        JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                recibirRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    String body = "";
                    if (error.networkResponse.data != null) {
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("VolleyError", "Error: " + body);
                    switch (error.networkResponse.statusCode) {
                        case 401:
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            break;
                        case 422:
                            Toast.makeText(getApplicationContext(), "Usuario 422", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Error en el servidor " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Log.e("VolleyError", "Error: " + error.toString());
                    Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        int socketTimeout = 30000; // 30 segundos
        myRequest.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(myRequest);
    }

    public void recibirRespuesta(JSONObject respuesta) {

            // Aquí puedes procesar la respuesta del servidor si es necesaria
            // Por ejemplo, podrías guardar datos de usuario en SharedPreferences
            // y redirigir a la actividad principal.
            editor.putString("usuario", edtUser.getText().toString());
            editor.commit();
            Intent intent = new Intent(Login.this,Inicio.class);
            startActivity(intent);
            finish();

    }
}