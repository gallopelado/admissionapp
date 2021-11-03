package com.sistemascorporativos.miappnueva.seguridad.login.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.sistemascorporativos.miappnueva.admision.actividades.BuscarPacienteActivity;
import com.sistemascorporativos.miappnueva.databinding.ActivityNavegacionBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.sistemascorporativos.miappnueva.R;
//import com.sistemascorporativos.miappnueva.seguridad.login.actividades.databinding.ActivityNavegacionBinding;

public class NavegacionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavegacionBinding binding;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavegacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Inicio");

        //setSupportActionBar(binding.appBarNavegacion.toolbar);
        setSupportActionBar(binding.topAppBar);
        sharedPref = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        verificaSesion();
        /*binding.appBarNavegacion.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacion);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, binding.topAppBar, R.string.abriendo_menu, R.string.cerrando_menu);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacion, menu);
        return true;
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacion);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                //startActivity(new Intent(NavegacionActivity.this, InicioMenuPrincipalActivity.class));
                startActivity(new Intent(NavegacionActivity.this, BuscarPacienteActivity.class));
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