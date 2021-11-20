package com.sistemascorporativos.miappnueva.consulta.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.consulta.actividades.AsignarCieActivity;
import com.sistemascorporativos.miappnueva.admision.entidades.AdmisionComponent;
import com.sistemascorporativos.miappnueva.consulta.dao.ConsultaDao;
import com.sistemascorporativos.miappnueva.consulta.entidades.ConsultaComponent;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacientePreconsultaDetalle;
import com.sistemascorporativos.miappnueva.consulta.servicios.ConsultaServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormularioConsultaBinding;

public class FormularioConsultaActivity extends AppCompatActivity {
    private ActivityFormularioConsultaBinding binding;
    private SharedPreferences sharedPreferences, sharedPreferencesLogin;
    static final Integer RC_EDIT = 21;
    ConsultaServices consultaServices;
    private Bundle extras;
    // Campos del formulario
    private TextInputLayout tilIdPreconsulta, tilNroIdentificacion, tilNombres, tilApellidos, tilTelefono,tilMotivoConsulta,tilHistorialConsulta,tilEvolucionConsulta;
    private TextInputEditText etNroIdentificacion;
    private TextInputEditText etNombres;
    private TextInputEditText etApellidos;
    private TextInputEditText etTelefono;
    private TextInputEditText etIdPreconsulta;
    private TextInputEditText etMotivoConsulta;
    private TextInputEditText etHistorialConsulta;
    private TextInputEditText etEvolucionConsulta;


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
        // Inicializar elementos del formulario
        tilIdPreconsulta = binding.tilIdPreconsulta;
        tilNroIdentificacion = binding.tilNroIdentificacion;
        tilNombres = binding.tilNombres;
        tilApellidos = binding.tilApellidos;
        tilTelefono = binding.tilTelefono;
        tilMotivoConsulta = binding.tilMotivoConsulta;
        tilHistorialConsulta = binding.tilHistorialConsulta;
        tilEvolucionConsulta = binding.tilEvolucionConsulta;
        //Edit texts
        etNroIdentificacion = binding.etNroIdentificacion;
        etNombres = binding.etNombres;
        etApellidos = binding.etApellidos;
        etTelefono = binding.etTelefono;
        etIdPreconsulta = binding.etIdPreconsulta;
        etMotivoConsulta = binding.etMotivoConsulta;
        etHistorialConsulta = binding.etHistorialConsulta;
        etEvolucionConsulta = binding.etEvolucionConsulta;

        String cedula = sharedPreferences.getString("cedula",null);
        String nombres = sharedPreferences.getString("nombres",null);
        String apellidos = sharedPreferences.getString("apellidos",null);
        String telefono = sharedPreferences.getString("telefono",null);
        String codigo_preconsulta = sharedPreferences.getString("codigo_preconsulta",null);
        String motivo_consulta = sharedPreferences.getString("motivo_consulta",null);
        String historial_consulta = sharedPreferences.getString("historial_consulta",null);
        String evolucion_consulta = sharedPreferences.getString("evolucion_consulta",null);

        etNroIdentificacion.setEnabled(false);
        etNombres.setEnabled(false);
        etApellidos.setEnabled(false);
        etTelefono.setEnabled(false);
        etIdPreconsulta.setEnabled(false);

        etNroIdentificacion.setText(cedula);
        etNombres.setText(nombres);
        etApellidos.setText(apellidos);
        etTelefono.setText(telefono);
        etIdPreconsulta.setText(codigo_preconsulta);
        etMotivoConsulta.setText(motivo_consulta);
        etHistorialConsulta.setText(historial_consulta);
        etEvolucionConsulta.setText(evolucion_consulta);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulta, menu);
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_save));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_detalles:
                if(validarAntes()) {
                    Intent intent = new Intent(this, AsignarCieActivity.class);
                    intent.putExtra("codigo_paciente", etNroIdentificacion.getText().toString());
                    intent.putExtra("codigo_preconsulta", etIdPreconsulta.getText().toString());
                    startActivity(intent);
                }
                break;
            case R.id.action_guardar_consulta:
                guardarFormularioConsulta();
                Intent myIntent = new Intent(FormularioConsultaActivity.this, ListaPacientesPreconsultaActivity.class);
                FormularioConsultaActivity.this.startActivity(myIntent);
                finish();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean guardarFormularioConsulta() {
        String codigoestablecimiento = sharedPreferences.getString("codigo_establecimiento",null);
        String IdPreconsulta = etIdPreconsulta.getText().toString().trim();
        String motivoConsulta = etMotivoConsulta.getText().toString().trim();
        String historialconsulta = etHistorialConsulta.getText().toString().trim();
        String evolucionconsulta = etEvolucionConsulta.getText().toString().trim();

        //actualizar
        ConsultaDao consultaDao = new ConsultaDao(this);
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setCodigoPreconsulta(IdPreconsulta);
        pacienteDto.setCodigoEstablecimiento(codigoestablecimiento);
        if(motivoConsulta != null)
            pacienteDto.setMotivoConsulta(motivoConsulta);
        if(historialconsulta!=null)
            pacienteDto.setHistorialConsulta(historialconsulta);
        if(evolucionconsulta!=null)
            pacienteDto.setEvolucionConsulta(evolucionconsulta);
        pacienteDto = consultaDao.actualizarPacienteOtrosDatos(pacienteDto);
        if(pacienteDto.getOperacion()!=null)
            Toast.makeText(this, "Consulta Guardada", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        return true;
    }


    private boolean validarAntes() {
        Boolean isValid = true;
        String numeroIdentificacion = etNroIdentificacion.getText().toString().trim();
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

        if(etNroIdentificacion.getText()==null || etNroIdentificacion.getText().toString().isEmpty()) {
            binding.tilNroIdentificacion.setError(getString(R.string.common_mandatory_hint));
            binding.tilNroIdentificacion.requestFocus();
            isValid = false;
        } else {
            binding.tilNroIdentificacion.setError(null);
        }
        if(etNombres.getText()==null || etNombres.getText().toString().isEmpty()) {
            binding.tilNombres.setError(getString(R.string.common_mandatory_hint));
            binding.tilNombres.requestFocus();
            isValid = false;
        } else {
            binding.tilNombres.setError(null);
        }
        if(etApellidos.getText()==null || etApellidos.getText().toString().isEmpty()) {
            binding.tilApellidos.setError(getString(R.string.common_mandatory_hint));
            binding.tilApellidos.requestFocus();
            isValid = false;
        } else {
            binding.tilApellidos.setError(null);
        }
        return isValid;
    }
}