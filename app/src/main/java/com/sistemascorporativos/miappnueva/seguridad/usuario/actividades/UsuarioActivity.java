package com.sistemascorporativos.miappnueva.seguridad.usuario.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityUsuarioBinding;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.usuario.adaptadores.ListaUsuariosAdapter;
import com.sistemascorporativos.miappnueva.seguridad.usuario.servicios.UsuarioServices;

import java.util.ArrayList;

public class UsuarioActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityUsuarioBinding binding;
    private RecyclerView rvListaUsuarios;
    private SearchView svBuscarUsuario;
    private ArrayList<LoginDto> listaArrayUsuarios;
    private ListaUsuariosAdapter adapter;
    private Bundle extras;
    private SharedPreferences sharedPref, sharedPref_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        generarVista();
    }

    private void init() {
        binding = ActivityUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Usuarios");
        rvListaUsuarios = binding.rvListaUsuarios;
        svBuscarUsuario = binding.svBuscarUsuario;
        svBuscarUsuario.setOnQueryTextListener(this);
        rvListaUsuarios.setLayoutManager(new LinearLayoutManager(this));
        extras = getIntent().getExtras();
        sharedPref = getSharedPreferences("usuario_referencial", Context.MODE_PRIVATE);
        sharedPref_menu = getSharedPreferences("menu", Context.MODE_PRIVATE);
        generarVista();
    }

    private void generarVista() {
        UsuarioServices us = new UsuarioServices(this);
        listaArrayUsuarios = new ArrayList<>();
        listaArrayUsuarios.addAll(us.getUsuarios());
        adapter = new ListaUsuariosAdapter(listaArrayUsuarios);
        rvListaUsuarios.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_referencial, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_opt_referencial:
                // Limpiar el share preferences
                sharedPref.edit().clear().commit();
                startActivity(new Intent(UsuarioActivity.this, FormUsuarioActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}