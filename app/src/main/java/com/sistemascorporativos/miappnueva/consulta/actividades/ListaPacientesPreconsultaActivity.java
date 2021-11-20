package com.sistemascorporativos.miappnueva.consulta.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.consulta.adaptadores.ListaPacientesPreconsultaAdapter;
import com.sistemascorporativos.miappnueva.consulta.dao.ConsultaDao;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacientePreconsultaDetalle;
import com.sistemascorporativos.miappnueva.databinding.ActivityListaPacientesPreconsultaBinding;
import com.sistemascorporativos.miappnueva.seguridad.login.actividades.NavegacionActivity;

import java.util.ArrayList;

public class ListaPacientesPreconsultaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityListaPacientesPreconsultaBinding binding;
    private RecyclerView listaPacientes;
    SearchView searchViewBuscar;
    private ArrayList<PacientePreconsultaDetalle> listaArrayPacientes;
    private ListaPacientesPreconsultaAdapter adapter;
    private SharedPreferences sharedPref;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(binding.getRoot());
        setTitle(getString(R.string.titulo_paciente_preconsulta));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaPacientes.setLayoutManager(new LinearLayoutManager(this));
        ConsultaDao consultaDao = new ConsultaDao(this);
        ArrayList<PacientePreconsultaDetalle> lista = consultaDao.getPacientesPreconsulta();
        adapter = new ListaPacientesPreconsultaAdapter(consultaDao.getPacientesPreconsulta());
        listaPacientes.setAdapter(adapter);
        searchViewBuscar.setOnQueryTextListener(this);
    }

    public void init() {
        binding = ActivityListaPacientesPreconsultaBinding.inflate(getLayoutInflater());
        listaPacientes = binding.rvListaPacientesPreconsulta;
        searchViewBuscar = binding.searchViewBuscarPaciente;
        listaArrayPacientes = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pacientes_preconsulta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, NavegacionActivity.class);
                startActivity(intent);
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