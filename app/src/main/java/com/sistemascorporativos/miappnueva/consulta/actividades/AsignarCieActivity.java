package com.sistemascorporativos.miappnueva.consulta.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.consulta.adaptadores.ListaCieAdapter;
import com.sistemascorporativos.miappnueva.consulta.dao.ConsultaDao;
import com.sistemascorporativos.miappnueva.consulta.entidades.Cie;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacientePreconsultaDetalle;
import com.sistemascorporativos.miappnueva.databinding.ActivityAsignarCieBinding;

import java.util.ArrayList;

public class AsignarCieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityAsignarCieBinding binding;
    SearchView searchViewBuscarCie;
    RecyclerView listaCie;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Cie> listaArrayCie;
    ListaCieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAsignarCieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle("Asignar Cie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout = binding.swipeRefreshLayout;

        // campo de búsqueda
        searchViewBuscarCie = binding.searchViewBuscarCie;

        // lista de médicos
        listaCie = binding.rvListaCie;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        listaCie.setLayoutManager(linearLayoutManager);
        //listaMedicos.setLayoutManager(new LinearLayoutManager(this));

        ConsultaDao consultaDao = new ConsultaDao(this);
        ArrayList<Cie> lista = consultaDao.getCie();

        adapter = new ListaCieAdapter(consultaDao.getCie());
        listaCie.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                reordenarElementos();
            }
        });

        // Asignar un listener al campo de búsqueda
        searchViewBuscarCie.setOnQueryTextListener(this);
    }

    public void reordenarElementos() {
        ConsultaDao consultaDao = new ConsultaDao(this);
        adapter = new ListaCieAdapter(consultaDao.getCie());
        listaCie.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asignar_cie, menu);
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
        return false;
    }

}