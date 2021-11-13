package com.sistemascorporativos.miappnueva.seguridad.usuario.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

    private void init() {
        binding = ActivityUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Usuarios");
        rvListaUsuarios = binding.rvListaUsuarios;
        svBuscarUsuario = binding.svBuscarUsuario;
        svBuscarUsuario.setOnQueryTextListener(this);
        listaArrayUsuarios = new ArrayList<>();
        rvListaUsuarios.setLayoutManager(new LinearLayoutManager(this));
        extras = getIntent().getExtras();
        sharedPref = getSharedPreferences("referenciales", Context.MODE_PRIVATE);
        sharedPref_menu = getSharedPreferences("menu", Context.MODE_PRIVATE);

        UsuarioServices us = new UsuarioServices(this);
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