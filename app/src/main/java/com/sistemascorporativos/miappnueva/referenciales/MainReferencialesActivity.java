package com.sistemascorporativos.miappnueva.referenciales;

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
import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.databinding.ActivityMainReferencialesBinding;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.dao.CiudadDao;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;
import com.sistemascorporativos.miappnueva.referenciales.especialidad.dao.EspecialidadDao;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.dao.NacionalidadDao;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos.NacionalidadDto;
import com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.dao.NivelEducativoDao;
import com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.modelos.NivelEducativoDto;
import com.sistemascorporativos.miappnueva.referenciales.seguro_medico.dao.SeguroMedicoDao;
import com.sistemascorporativos.miappnueva.referenciales.seguro_medico.modelos.SeguroMedicoDto;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.dao.SituacionLaboralDao;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.modelos.SituacionLaboralDto;

import java.util.ArrayList;

public class MainReferencialesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityMainReferencialesBinding binding;
    private RecyclerView listaItems;
    private SearchView searchViewBuscarReferencial;
    private ArrayList<ReferencialDto> listaArrayReferencial;
    private ListaReferencialesAdapter adapter;
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
        binding = ActivityMainReferencialesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaItems = binding.rvListaItemsReferenciales;
        searchViewBuscarReferencial = binding.searchViewBuscarReferencial;
        listaArrayReferencial = new ArrayList<>();
        listaItems.setLayoutManager(new LinearLayoutManager(this));
        extras = getIntent().getExtras();
        searchViewBuscarReferencial.setOnQueryTextListener(this);
        sharedPref = getSharedPreferences("referenciales", Context.MODE_PRIVATE);
        sharedPref_menu = getSharedPreferences("menu", Context.MODE_PRIVATE);
        generarVista();
    }

    private void generarVista() {
        SharedPreferences.Editor editor = sharedPref_menu.edit();
        editor.putString("menu", extras.getString("menu")).commit();
        listaArrayReferencial = new ArrayList<>();
        switch (extras.getString("menu")) {
            case "ciudad":
                setTitle("Ciudad");
                CiudadDao ciudadDao = new CiudadDao(this);
                ArrayList<CiudadDto> listaCiudad = ciudadDao.getCiudades();
                for(CiudadDto item: listaCiudad) {
                    ReferencialDto obj = new ReferencialDto();
                    obj.setId(item.getCiuId().toString());
                    obj.setDescripcion(item.getCiuDescripcion());
                    listaArrayReferencial.add(obj);
                }
                adapter = new ListaReferencialesAdapter(listaArrayReferencial);
                listaItems.setAdapter(adapter);
                break;
            case "nacionalidad":
                setTitle("Nacionalidad");
                NacionalidadDao nacionalidadDao = new NacionalidadDao(this);
                ArrayList<NacionalidadDto> listaNac = nacionalidadDao.getNacionalidades();
                for(NacionalidadDto item: listaNac) {
                    ReferencialDto obj = new ReferencialDto();
                    obj.setId(item.getNacId().toString());
                    obj.setDescripcion(item.getNacDescripcion());
                    listaArrayReferencial.add(obj);
                }
                adapter = new ListaReferencialesAdapter(listaArrayReferencial);
                listaItems.setAdapter(adapter);
                break;
            case "seguro_medico":
                setTitle("Seguro Médico");
                SeguroMedicoDao seguroMedicoDao = new SeguroMedicoDao(this);
                ArrayList<SeguroMedicoDto> listaSeg = seguroMedicoDao.getSeguroMedico();
                for(SeguroMedicoDto item: listaSeg) {
                    ReferencialDto obj = new ReferencialDto();
                    obj.setId(item.getSegId().toString());
                    obj.setDescripcion(item.getSegDescripcion());
                    listaArrayReferencial.add(obj);
                }
                adapter = new ListaReferencialesAdapter(listaArrayReferencial);
                listaItems.setAdapter(adapter);
                break;
            case "nivel_educativo":
                setTitle("Nivel Educativo");
                NivelEducativoDao nivelEducativoDao = new NivelEducativoDao(this);
                ArrayList<NivelEducativoDto> listaNiv = nivelEducativoDao.getNivelEducativo();
                for(NivelEducativoDto item: listaNiv) {
                    ReferencialDto obj = new ReferencialDto();
                    obj.setId(item.getEduId().toString());
                    obj.setDescripcion(item.getEduDescripcion());
                    listaArrayReferencial.add(obj);
                }
                adapter = new ListaReferencialesAdapter(listaArrayReferencial);
                listaItems.setAdapter(adapter);
                break;
            case "situacion_laboral":
                setTitle("Situación Laboral");
                SituacionLaboralDto situacionLaboralDto = new SituacionLaboralDto();
                SituacionLaboralDao situacionLaboralDao = new SituacionLaboralDao(this);
                ArrayList<SituacionLaboralDto> listaSi = situacionLaboralDao.getSituacionLaboral();
                for(SituacionLaboralDto item: listaSi) {
                    ReferencialDto obj = new ReferencialDto();
                    obj.setId(item.getSitlabId().toString());
                    obj.setDescripcion(item.getSitlabDescripcion());
                    listaArrayReferencial.add(obj);
                }
                adapter = new ListaReferencialesAdapter(listaArrayReferencial);
                listaItems.setAdapter(adapter);
                break;
            case "especialidad":
                setTitle("Especialidad");
                Especialidad especialidad = new Especialidad();
                EspecialidadDao especialidadDao = new EspecialidadDao(this);
                ArrayList<Especialidad> listaEs = especialidadDao.getEspecialidades();
                for(Especialidad item: listaEs) {
                    ReferencialDto obj = new ReferencialDto();
                    obj.setId(item.getEspecialidadId().toString());
                    obj.setDescripcion(item.getEspecialidadDescripcion());
                    listaArrayReferencial.add(obj);
                }
                adapter = new ListaReferencialesAdapter(listaArrayReferencial);
                listaItems.setAdapter(adapter);
                break;
        }
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
                startActivity(new Intent(this, FormMainReferencialesActivity.class));
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