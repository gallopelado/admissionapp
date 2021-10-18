package com.sistemascorporativos.miappnueva.admision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.adaptadores.ListaMedicosAdapter;
import com.sistemascorporativos.miappnueva.admision.dao.AdmisionDao;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;
import com.sistemascorporativos.miappnueva.databinding.ActivityAsignarMedicoBinding;

import java.util.ArrayList;

public class AsignarMedicoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityAsignarMedicoBinding binding;
    SearchView searchViewBuscarMedico;
    RecyclerView listaMedicos;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Profesional> listaArrayMedicos;
    ListaMedicosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAsignarMedicoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle(getString(R.string.titulo_asignar_medico));

        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout = binding.swipeRefreshLayout;

        // campo de búsqueda
        searchViewBuscarMedico = binding.searchViewBuscarMedico;

        // lista de médicos
        listaMedicos = binding.rvListaMedico;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        listaMedicos.setLayoutManager(linearLayoutManager);
        //listaMedicos.setLayoutManager(new LinearLayoutManager(this));

        AdmisionDao admisionDao = new AdmisionDao(this);
        listaArrayMedicos = new ArrayList<>();

        adapter = new ListaMedicosAdapter(admisionDao.getProfesionales());
        listaMedicos.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                reordenarElementos();
            }
        });

        // Asignar un listener al campo de búsqueda
        searchViewBuscarMedico.setOnQueryTextListener(this);
    }

    public void reordenarElementos() {
        AdmisionDao admisionDao = new AdmisionDao(this);
        adapter = new ListaMedicosAdapter(admisionDao.getProfesionales());
        listaMedicos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asignar_medico, menu);
        return super.onCreateOptionsMenu(menu);
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