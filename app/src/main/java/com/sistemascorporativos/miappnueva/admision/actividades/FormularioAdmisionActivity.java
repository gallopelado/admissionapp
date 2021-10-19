package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.servicios.AdmisionServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormularioAdmisionBinding;
import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos.NacionalidadDto;

import java.util.ArrayList;

public class FormularioAdmisionActivity extends AppCompatActivity {

    private ActivityFormularioAdmisionBinding binding;
    private SharedPreferences sharedPreferences;
    static final Integer RC_EDIT = 21;
    private Spinner ciudadCombo, nacionalidadCombo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormularioAdmisionBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        // Iniciar el sharedpreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // combos
        ciudadCombo = binding.spCiudad;
        nacionalidadCombo = binding.spNacionalidad;
        AdmisionServices adms = new AdmisionServices(this);
        ArrayList<CiudadDto> listaCiudades = adms.getCiudades();
        ArrayList<NacionalidadDto> listaNacionalidades = adms.getNacionalidades();
        ArrayAdapter<CiudadDto> ciudadAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaCiudades);
        ArrayAdapter<NacionalidadDto> nacionalidadAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaNacionalidades);
        ciudadCombo.setAdapter(ciudadAdapter);
        nacionalidadCombo.setAdapter(nacionalidadAdapter);

        ciudadCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int ciuId = ((CiudadDto) parent.getSelectedItem()).getCiuId();
                String ciudad = ((CiudadDto) parent.getSelectedItem()).getCiuDescripcion();
                Toast.makeText(FormularioAdmisionActivity.this, ciuId+" - "+ciudad, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admision, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_opciones:
                Intent intent = new Intent(this, OtrosDatosActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}