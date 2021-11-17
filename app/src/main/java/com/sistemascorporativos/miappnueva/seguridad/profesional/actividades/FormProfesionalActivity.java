package com.sistemascorporativos.miappnueva.seguridad.profesional.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormProfesionalBinding;
import com.sistemascorporativos.miappnueva.referenciales.especialidad.dao.EspecialidadDao;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.usuario.servicios.UsuarioServices;

import java.util.ArrayList;

public class FormProfesionalActivity extends AppCompatActivity {

    private ActivityFormProfesionalBinding binding;
    private Spinner spUsuarioProfesional, spEspecialidadProfesional;
    private CheckBox chkEstadoProfesional;
    private TextInputLayout tilNroRegistroProfesional;
    private TextInputEditText etNroRegistroProfesional;
    private Bundle extras;
    private SharedPreferences sharedPref, sharedPref_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        binding = ActivityFormProfesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        extras = getIntent().getExtras();
        sharedPref = getSharedPreferences("profesional_referencial", Context.MODE_PRIVATE);
        spUsuarioProfesional = binding.spUsuarioProfesional;
        spEspecialidadProfesional = binding.spEspecialidadProfesional;
        chkEstadoProfesional = binding.chkEstadoProfesional;
        tilNroRegistroProfesional = binding.tilNroRegistroProfesional;
        etNroRegistroProfesional = binding.etNroRegistroProfesional;

        // Cargar valores para spinners
        // Traer usuarios
        UsuarioServices uss = new UsuarioServices(this);
        ArrayList<LoginDto> listaUsuarios = uss.getUsuarios();
        ArrayAdapter<LoginDto> usuariosAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaUsuarios);
        spUsuarioProfesional.setAdapter(usuariosAdapter);

        //Traer especialidades
        EspecialidadDao espdao = new EspecialidadDao(this);
        ArrayList<Especialidad> listaEspecialidades = espdao.getEspecialidades();
        ArrayAdapter<Especialidad> especialidadesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaEspecialidades);
        spEspecialidadProfesional.setAdapter(especialidadesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_referencial, menu);
        // Cambia icono del menu topbar
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_save));
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}