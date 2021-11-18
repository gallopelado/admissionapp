package com.sistemascorporativos.miappnueva.preconsulta.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityPreListaAdmitidosBinding;
import com.sistemascorporativos.miappnueva.preconsulta.adaptadores.Pre_ListaAdmitidosAdapter;
import com.sistemascorporativos.miappnueva.preconsulta.dao.PreconsultaDao;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.Pre_PacienteAdmitido;

import java.util.ArrayList;

public class Pre_ListaAdmitidosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityPreListaAdmitidosBinding binding;

    private RecyclerView listaPacientes;
    SearchView searchViewBuscar;
    private ArrayList<Pre_PacienteAdmitido> listaArrayPacientes;
    private Pre_ListaAdmitidosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(binding.getRoot());
        setTitle(getString(R.string.titulo_pacientes_admitidos_preconsulta));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaPacientes.setLayoutManager(new LinearLayoutManager(this));
        PreconsultaDao preconsultaDao = new PreconsultaDao(this);
        ArrayList<Pre_PacienteAdmitido> lista = preconsultaDao.getPacientesAdmitidos();
        adapter = new Pre_ListaAdmitidosAdapter(preconsultaDao.getPacientesAdmitidos());
        listaPacientes.setAdapter(adapter);
        searchViewBuscar.setOnQueryTextListener(this);
    }

    public void init() {
        binding = ActivityPreListaAdmitidosBinding.inflate(getLayoutInflater());
        listaPacientes = binding.rvListaPacientesAdmitidos2;
        searchViewBuscar = binding.searchViewBuscarPaciente2;
        listaArrayPacientes = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pacientes_admitidos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Pre_FormPreconsultaActivity.class);
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
//        adapter.filtrado(newText);
        return false;
    }
}