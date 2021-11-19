package com.sistemascorporativos.miappnueva.consulta.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.consulta.entidades.ConsultaComponent;
import com.sistemascorporativos.miappnueva.consulta.servicios.ConsultaServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormularioConsultaBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class FormularioConsultaActivity extends AppCompatActivity {
    private ActivityFormularioConsultaBinding binding;
    private SharedPreferences sharedPreferences, sharedPreferencesLogin;
    static final Integer RC_EDIT = 21;
    ConsultaServices consultaServices;
    private Bundle extras;
    // Campos del formulario
    private TextInputEditText txtNroIdentificacion;
    private TextInputEditText txtNombres;
    private TextInputEditText txtApellidos;
    private TextInputEditText txtTelefono;
    private TextInputEditText txtCodigoPreconsulta;
    private TextInputEditText txtCodigoAsignacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormularioConsultaBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        this.setTitle(getString(R.string.titulo_consulta));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Iniciar el sharedpreferences
        sharedPreferences = getSharedPreferences("lista_paciente", Context.MODE_PRIVATE);
        sharedPreferencesLogin = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        sharedPreferencesLogin.getString("codigo_usuario",null);
        // Campos de texto
        txtNroIdentificacion = binding.etNroIdentificacion;
        txtNombres = binding.etNombres;
        txtApellidos = binding.etApellidos;
        txtTelefono = binding.etTelefono;
        txtCodigoPreconsulta = binding.etIdPreconsulta;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean validarAntes() {
        Boolean isValid = true;
        String numeroIdentificacion = txtNroIdentificacion.getText().toString().trim();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        if(numeroIdentificacion.isEmpty() || numeroIdentificacion==null) {
            dialogo.setTitle("Cuidado")
                    .setMessage("Debe registrar primero este formulario")
                    .setPositiveButton("Salir", null).show();
            isValid=false;
            camposValidos();
        }
        return isValid;
    }

    private Boolean camposValidos() {
        Boolean isValid = true;

        if(txtNroIdentificacion.getText()==null || txtNroIdentificacion.getText().toString().isEmpty()) {
            binding.tilNroIdentificacion.setError(getString(R.string.common_mandatory_hint));
            binding.tilNroIdentificacion.requestFocus();
            isValid = false;
        } else {
            binding.tilNroIdentificacion.setError(null);
        }
        if(txtNombres.getText()==null || txtNombres.getText().toString().isEmpty()) {
            binding.tilNombres.setError(getString(R.string.common_mandatory_hint));
            binding.tilNombres.requestFocus();
            isValid = false;
        } else {
            binding.tilNombres.setError(null);
        }
        if(txtApellidos.getText()==null || txtApellidos.getText().toString().isEmpty()) {
            binding.tilApellidos.setError(getString(R.string.common_mandatory_hint));
            binding.tilApellidos.requestFocus();
            isValid = false;
        } else {
            binding.tilApellidos.setError(null);
        }
        return isValid;
    }
}