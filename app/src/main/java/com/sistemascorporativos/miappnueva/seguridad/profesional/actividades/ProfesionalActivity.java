package com.sistemascorporativos.miappnueva.seguridad.profesional.actividades;

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
import com.sistemascorporativos.miappnueva.databinding.ActivityProfesionalBinding;
import com.sistemascorporativos.miappnueva.seguridad.profesional.adaptadores.ListaProfesionalesAdapter;
import com.sistemascorporativos.miappnueva.seguridad.profesional.entidades.ProfesionalDto;
import com.sistemascorporativos.miappnueva.seguridad.profesional.servicios.ProfesionalServices;

import java.util.ArrayList;

public class ProfesionalActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityProfesionalBinding binding;
    private RecyclerView rvListaProfesionales;
    private SearchView svBuscarProfesional;
    private ArrayList<ProfesionalDto> listaArrayReferencial;
    private ListaProfesionalesAdapter adapter;
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
        binding = ActivityProfesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences("profesional_referencial", Context.MODE_PRIVATE);
        rvListaProfesionales = binding.rvListaProfesionales;
        rvListaProfesionales.setLayoutManager(new LinearLayoutManager(this));
        svBuscarProfesional = binding.svBuscarProfesional;
        svBuscarProfesional.setOnQueryTextListener(this);
        extras = getIntent().getExtras();
        generarVista();
    }

    private void generarVista() {
        ProfesionalServices pfs = new ProfesionalServices(this);
        listaArrayReferencial = new ArrayList<>();
        listaArrayReferencial = pfs.getProfesionales();
        adapter = new ListaProfesionalesAdapter(listaArrayReferencial);
        rvListaProfesionales.setAdapter(adapter);
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
                sharedPref.edit().clear().commit();
                startActivity(new Intent(this, FormProfesionalActivity.class));
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