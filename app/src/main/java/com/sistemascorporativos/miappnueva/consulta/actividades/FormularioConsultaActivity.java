package com.sistemascorporativos.miappnueva.consulta.actividades;

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
import com.sistemascorporativos.miappnueva.consulta.servicios.ConsultaServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormularioConsultaBinding;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos.NacionalidadDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class FormularioConsultaActivity extends AppCompatActivity {
    private ActivityFormularioConsultaBinding binding;
    private SharedPreferences sharedPreferences;
    static final Integer RC_EDIT = 21;
    ConsultaServices consultaServices;
    private Bundle extras;
    private Spinner ciudadCombo, nacionalidadCombo;
    // Campos del formulario
    private TextInputEditText txtNroIdentificacion;
    private TextInputEditText txtNombres;
    private TextInputEditText txtApellidos;
    private RadioGroup rgSexo;
    private RadioButton txtSexo;
    private TextInputEditText txtFechaNacimiento;
    private TextInputEditText txtLugarNacimiento;
    private TextInputEditText txtCorreo;
    private TextInputEditText txtTelefono;
    private TextInputEditText txtDomicilio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormularioConsultaBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        this.setTitle(getString(R.string.titulo_consulta));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Iniciar el sharedpreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // SETEAR TODOS LOS ELEMENTOS PARA SU CORRECTA MANIPULACIÓN
        // Campos de texto
        txtNroIdentificacion = binding.etNroIdentificacion;
        txtNombres = binding.etNombres;
        txtApellidos = binding.etApellidos;
        txtFechaNacimiento = binding.etFechaNac;
        txtLugarNacimiento = binding.etLugarnac;
        txtCorreo = binding.etCorreo;
        txtTelefono = binding.etTelefono;
        txtDomicilio = binding.etDomicilio;

        // Radio button y sus yerbas
        rgSexo = binding.rgSexo;
        //trae el radio seleccionado del radio group
        int selectedSexo = rgSexo.getCheckedRadioButtonId();
        txtSexo = findViewById(selectedSexo);

        // combos
        ciudadCombo = binding.spCiudad;
        nacionalidadCombo = binding.spNacionalidad;
        // Iniciar una instancia del service
        consultaServices = new ConsultaServices(this);
        ArrayList<CiudadDto> listaCiudades = consultaServices.getCiudades();
        ArrayList<NacionalidadDto> listaNacionalidades = consultaServices.getNacionalidades();
        ArrayAdapter<CiudadDto> ciudadAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaCiudades);
        ArrayAdapter<NacionalidadDto> nacionalidadAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaNacionalidades);
        ciudadCombo.setAdapter(ciudadAdapter);
        nacionalidadCombo.setAdapter(nacionalidadAdapter);

        // Asignar evento click al datepicker de fecha de nacimiento
        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker picker = builder.setTitleText("Seleccionar fecha").build();

            @Override
            public void onClick(View v) {
                picker.addOnPositiveButtonClickListener(timeInMilliseconds -> {
                    // obtener el tiempo en milisegundos y formatear
                    SimpleDateFormat dateStr = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
                    dateStr.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String fecha = dateStr.format(timeInMilliseconds);
                    txtFechaNacimiento.setText(fecha);//setear campo
                });
                picker.show(getSupportFragmentManager(), picker.toString());
            }
        });

        //Traer extras
        if(savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras.getString("operacion").isEmpty()) {
                txtNroIdentificacion.setEnabled(true);
                txtNroIdentificacion.setText(extras.getString("codigo_paciente"));
            } else {
                txtNroIdentificacion.setEnabled(false);
                txtNroIdentificacion.setText(extras.getString("codigo_paciente"));
                txtNombres.setText(extras.getString("nombres"));
                txtApellidos.setText(extras.getString("apellidos"));
                switch (extras.getString("sexo")) {
                    case "Masculino":
                        rgSexo.check(binding.rbMasculino.getId());
                        break;
                    case "Femenino":
                        rgSexo.check(binding.rbFemenino.getId());
                        break;
                    case "Indistinto":
                        rgSexo.check(binding.rbIndistinto.getId());
                        break;
                }
                txtFechaNacimiento.setText(extras.getString("fechanac"));
                for (int i = 0; i < ciudadCombo.getCount(); i++) {
                    if (((CiudadDto) ciudadCombo.getItemAtPosition(i)).getCiuId() == extras.getInt("ciudad")) {
                        ciudadCombo.setSelection(i);
                        break;
                    }
                }
                for (int i = 0; i < nacionalidadCombo.getCount(); i++) {
                    if (((NacionalidadDto) nacionalidadCombo.getItemAtPosition(i)).getNacId() == extras.getInt("ciudad")) {
                        nacionalidadCombo.setSelection(i);
                        break;
                    }
                }
                txtLugarNacimiento.setText(extras.getString("lugarnac"));
                txtCorreo.setText(extras.getString("correo"));
                txtTelefono.setText(extras.getString("telefono"));
                txtDomicilio.setText(extras.getString("domicilio"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admision, menu);
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
        if(txtFechaNacimiento.getText()==null || txtFechaNacimiento.getText().toString().isEmpty()) {
            binding.tilFechaNac.setError(getString(R.string.common_mandatory_hint));
            binding.tilFechaNac.requestFocus();
            isValid = false;
        } else {
            binding.tilFechaNac.setError(null);
        }

        return isValid;
    }
}