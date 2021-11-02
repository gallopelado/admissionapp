package com.sistemascorporativos.miappnueva.seguridad.login.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityInicioMenuPrincipalBinding;

public class InicioMenuPrincipalActivity extends AppCompatActivity {

    private ActivityInicioMenuPrincipalBinding binding;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Button btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        verificaSesion();
        Listeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificaSesion();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        verificaSesion();
    }

    private void init(){
        binding = ActivityInicioMenuPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btLogout = binding.btLogout;
        sharedPref = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
    }
    private void Listeners() {
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
    }
    private void verificaSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("codigo_usuario", null);
        if(user != null) {
            //tranqui
            Toast.makeText(this, "Sesion activa", Toast.LENGTH_LONG).show();
        } else {
            cerrarSesion();
        }
    }
    private void cerrarSesion() {
        sharedPref.edit().clear().commit();
        irAlogin();
    }
    private void irAlogin() {
        Intent login = new Intent(this, LoginActivity.class);
        finish();
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
    }
}