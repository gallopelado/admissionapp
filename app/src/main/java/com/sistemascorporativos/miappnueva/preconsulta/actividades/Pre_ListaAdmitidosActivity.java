package com.sistemascorporativos.miappnueva.preconsulta.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView rvListaPacientesAdmitidosPreconsulta;
    SearchView svBuscarPacientePreconsulta;
    private ArrayList<Pre_PacienteAdmitido> listaArrayPacientes;
    private Pre_ListaAdmitidosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        binding = ActivityPreListaAdmitidosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.titulo_pacientes_admitidos_preconsulta));
        rvListaPacientesAdmitidosPreconsulta = binding.rvListaPacientesAdmitidosPreconsulta;
        svBuscarPacientePreconsulta = binding.svBuscarPacientePreconsulta;
        listaArrayPacientes = new ArrayList<>();
        rvListaPacientesAdmitidosPreconsulta.setLayoutManager(new LinearLayoutManager(this));
        PreconsultaDao preconsultaDao = new PreconsultaDao(this);
        ArrayList<Pre_PacienteAdmitido> lista = preconsultaDao.getPacientesAdmitidos();
        adapter = new Pre_ListaAdmitidosAdapter(preconsultaDao.getPacientesAdmitidos());
        rvListaPacientesAdmitidosPreconsulta.setAdapter(adapter);
        svBuscarPacientePreconsulta.setOnQueryTextListener(this);
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
//        adapter.filtrado(newText);
        return false;
    }
}