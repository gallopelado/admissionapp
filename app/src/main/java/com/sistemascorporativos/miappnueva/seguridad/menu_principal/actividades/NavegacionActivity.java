package com.sistemascorporativos.miappnueva.seguridad.menu_principal.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sistemascorporativos.miappnueva.admision.actividades.BuscarPacienteActivity;
import com.sistemascorporativos.miappnueva.databinding.ActivityNavegacionBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.referenciales.MainReferencialesActivity;
import com.sistemascorporativos.miappnueva.seguridad.login.actividades.LoginActivity;

public class NavegacionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityNavegacionBinding binding;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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

    private void init() {
        binding = ActivityNavegacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Inicio");

        setSupportActionBar(binding.appBarNavegacion.toolbar);
        sharedPref = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        verificaSesion();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Aquí se setea el titulo de header del menú pancholo :)
        String user_name = sharedPref.getString("nombres_usuario", null);
        String user_apellido = sharedPref.getString("apellidos_usuario", null);
        String user_rol = sharedPref.getString("rol_usuario", null);
        View headerView = navigationView.getHeaderView(0);
        TextView profileName = (TextView) headerView.findViewById(R.id.tvTituloGrandeMenu);
        TextView profileRol = (TextView) headerView.findViewById(R.id.tvSubtituloMenu);
        profileName.setText(user_name+' '+user_apellido);
        profileRol.setText(user_rol);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, binding.appBarNavegacion.toolbar, R.string.abriendo_menu, R.string.cerrando_menu);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacion, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(NavegacionActivity.this, MainReferencialesActivity.class);
        switch (item.getItemId()) {
            case R.id.nav_admision:
                startActivity(new Intent(NavegacionActivity.this, BuscarPacienteActivity.class));
                break;
            case R.id.nav_ciudad:
                intent.putExtra("menu", "ciudad");
                startActivity(intent);
                break;
            case R.id.nav_nacionalidad:
                intent.putExtra("menu", "nacionalidad");
                startActivity(intent);
                break;
            case R.id.nav_seguro_medico:
                intent.putExtra("menu", "seguro_medico");
                startActivity(intent);
                break;
            case R.id.nav_nivel_educativo:
                intent.putExtra("menu", "nivel_educativo");
                startActivity(intent);
                break;
            case R.id.nav_situacion_laboral:
                intent.putExtra("menu", "situacion_laboral");
                startActivity(intent);
                break;
            case R.id.nav_especialidad:
                intent.putExtra("menu", "especialidad");
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_salir:
                cerrarSesion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void verificaSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("codigo_usuario", null);
        if(user != null) {
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