package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.adaptadores.ListaPacientesAdmitidosAdapter;
import com.sistemascorporativos.miappnueva.admision.dao.AdmisionDao;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAdmitidoDetalle;
import com.sistemascorporativos.miappnueva.databinding.ActivityListaPacientesAdmitidosBinding;

import java.util.ArrayList;

public class ListaPacientesAdmitidosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityListaPacientesAdmitidosBinding binding;
    private RecyclerView listaPacientes;
    SearchView searchViewBuscar;
    private ArrayList<PacienteAdmitidoDetalle> listaArrayPacientes;
    private ListaPacientesAdmitidosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(binding.getRoot());
        setTitle(getString(R.string.titulo_pacientes_admitidos));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaPacientes.setLayoutManager(new LinearLayoutManager(this));
        AdmisionDao admisionDao = new AdmisionDao(this);
        ArrayList<PacienteAdmitidoDetalle> lista = admisionDao.getPacientesAdmitidos();
        adapter = new ListaPacientesAdmitidosAdapter(admisionDao.getPacientesAdmitidos());
        listaPacientes.setAdapter(adapter);
        searchViewBuscar.setOnQueryTextListener(this);
    }

    public void init() {
        binding = ActivityListaPacientesAdmitidosBinding.inflate(getLayoutInflater());
        listaPacientes = binding.rvListaPacientesAdmitidos;
        searchViewBuscar = binding.searchViewBuscarPaciente;
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