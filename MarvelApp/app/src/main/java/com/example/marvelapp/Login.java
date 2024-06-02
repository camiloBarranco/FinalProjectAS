package com.example.marvelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cajita = edtUser.getText().toString();
                String cajita2 =edtPassword.getText().toString();

                if (cajita.equals("") || cajita2.equals("")){
                    Toast.makeText(Login.this,"Digite los campos vacios",Toast.LENGTH_LONG).show();
                }
                else {
                    editor.putString("usuario",cajita);
                    editor.commit();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}