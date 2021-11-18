package com.sistemascorporativos.miappnueva.preconsulta.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.actividades.OtrosDatosActivity;
import com.sistemascorporativos.miappnueva.admision.entidades.AdmisionComponent;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormularioPreconsultaBinding;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.PreconsultaComponent;
import com.sistemascorporativos.miappnueva.preconsulta.servicios.PreconsultaService;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos.NacionalidadDto;
import com.sistemascorporativos.miappnueva.seguridad.menu_principal.actividades.NavegacionActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class Pre_FormPreconsultaActivity extends AppCompatActivity {

    private ActivityFormularioPreconsultaBinding binding;
    private PreconsultaService preconsultaServices;
    private SharedPreferences sharedPref;
    // Campos del formulario
    private TextInputEditText txtNombrePac;
    private TextInputEditText txtFechaNac;
    private TextInputEditText txtTemCorp;
    private TextInputEditText txtPrecArt;
    private TextInputEditText txtFrecueRespi;
    private TextInputEditText txtPulso;
    private TextInputEditText txtPeso;
    private TextInputEditText txtTalla;
    private TextInputEditText txtImc;
    private TextInputEditText txtSaturOxige;
    private TextInputEditText txtCircunAbdomi;
    private TextInputEditText txtMotivoConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        traerDatosAlFormulario();
    }

    private void init() {
        binding = ActivityFormularioPreconsultaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle(getString(R.string.titulo_preconsulta));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences("preconsulta", Context.MODE_PRIVATE);

        // SETEAR TODOS LOS ELEMENTOS PARA SU CORRECTA MANIPULACIÓN
        // Campos de texto
        txtNombrePac = binding.etnombrePreconsulta;
        txtFechaNac = binding.etedadPaciente;
        txtTemCorp = binding.etcorporalPreconsulta;
        txtPrecArt = binding.etpresionArtPreconsulta;
        txtFrecueRespi = binding.etfrecuenRespPreconsulta;
        txtPulso = binding.etpulsoPreconsulta;
        txtPeso = binding.etpesoPreconsulta;
        txtTalla = binding.ettallaPreconsulta;
        txtImc = binding.etimcPreconsulta;
        txtSaturOxige = binding.etsaturaOxigPreconsulta;
        txtCircunAbdomi = binding.etcircAbdoPreconsulta;
        txtMotivoConsulta = binding.etmotivoConsulta;
    }

    private void traerDatosAlFormulario() {
        String nombre_paciente = sharedPref.getString("nombre_paciente", null);
        String fechanac = sharedPref.getString("fechanac", null);
        String precon_codigo_establecimiento = sharedPref.getString("precon_codigo_establecimiento", null);
        String pacasi_codigo_asignacion = sharedPref.getString("pacasi_codigo_asignacion", null);
        String precon_temperatura_corporal = sharedPref.getString("precon_temperatura_corporal", null);
        String precon_presion_arterial = sharedPref.getString("precon_presion_arterial", null);
        String precon_frecuencia_respiratoria = sharedPref.getString("precon_frecuencia_respiratoria", null);
        String precon_pulso = sharedPref.getString("precon_pulso", null);
        String precon_peso = sharedPref.getString("precon_peso", null);
        String precon_talla = sharedPref.getString("precon_talla", null);
        String precon_imc = sharedPref.getString("precon_imc", null);
        String precon_saturacion = sharedPref.getString("precon_saturacion", null);
        String precon_circunferencia_abdominal = sharedPref.getString("precon_circunferencia_abdominal", null);
        String precon_motivo_consulta = sharedPref.getString("precon_motivo_consulta", null);

        txtNombrePac.setText(nombre_paciente);
        //procesar fecha de nacimiento para edad
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        }
        LocalDate localdate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localdate = LocalDate.parse(fechanac, formatter);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String edad = calculateAge(localdate, LocalDate.now()).toString();
            txtFechaNac.setText( edad );
        }
        txtTemCorp.setText(precon_temperatura_corporal);
        txtPrecArt.setText(precon_presion_arterial);
        txtFrecueRespi.setText(precon_frecuencia_respiratoria);
        txtPulso.setText(precon_pulso);
        txtPeso.setText(precon_peso);
        txtTalla.setText(precon_talla);
        txtImc.setText(precon_imc);
        txtSaturOxige.setText(precon_saturacion);
        txtCircunAbdomi.setText(precon_circunferencia_abdominal);
        txtMotivoConsulta.setText(precon_motivo_consulta);
    }

    /*
    * Solucion en https://www.baeldung.com/java-get-age
     */
    public static Integer calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Period.between(birthDate, currentDate).getYears();
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preconsulta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_guardar:
                guardarFormularioPreconsulta();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validarAntes() {
        Boolean isValid = true;
        String numeroIdentificacion = txtNombrePac.getText().toString().trim();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        if(numeroIdentificacion.isEmpty() || numeroIdentificacion==null) {
            dialogo.setTitle("Cuidado")
                    .setMessage("Debe registrar primero este formulario")
                    .setPositiveButton("Salir", null).show();
            isValid=false;
            camposValidos();
        } else {
            AdmisionComponent pacienteGuardado = preconsultaServices.getPacienteByCodigopaciente(numeroIdentificacion);
            if(pacienteGuardado.getOperacion()==null) {
                dialogo.setTitle("Cuidado")
                        .setMessage("Este paciente no existe")
                        .setPositiveButton("Salir", null).show();
                isValid=false;
            }
        }
        return isValid;
    }

    private void guardarFormularioPreconsulta() {
        if(camposValidos()) {
            String nroIdentificacion = txtNombrePac.getText().toString().trim();
            String nombres = txtFechaNac.getText().toString().trim();
            String apellidos = txtTemCorp.getText().toString().trim();
            String lugarNacimiento = txtFrecueRespi.getText().toString().trim();
            String fechaNacimiento = txtPrecArt.getText().toString();
            String correo = txtPulso.getText().toString().trim();
            String telefono = txtPeso.getText().toString().trim();
            String domicilio = txtTalla.getText().toString().trim();

            // Una vez recolectados los datos, se procede a guardar
            PreconsultaComponent paciente = new PreconsultaComponent();
            paciente.setNroIdentificacion(nroIdentificacion);
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);
            paciente.setLugarNacimiento(lugarNacimiento);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setCorreo(correo);
            paciente.setTelefono(telefono);
            paciente.setDireccion(domicilio);


//            if(preconsultaServices.getPacienteByCodigopaciente(paciente.getNroIdentificacion()).getOperacion()==null) {
//                //guardar
//                paciente = preconsultaServices.guardarPaciente(paciente);
//                if(paciente.getOperacion().contains("GUARDADO")) {
//                    AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
//                    dialogo.setTitle("Se guardó correctamente").setMessage("Se han registrado datos de paciente").setPositiveButton("OK", null).show();
//                }
//            } else {
//                //actualizar
//                paciente = preconsultaServices.actualizarPacienteFormularioPrincipal(paciente);
//                if(paciente.getOperacion().contains("UPDATE-FORMPRINCIPAL")) {
//                    AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
//                    dialogo.setTitle("Se actualizó correctamente").setMessage("Se han actualizado datos de paciente").setPositiveButton("OK", null).show();
//                }
//            }

            /*if(paciente.getOperacion().contains("GUARDADO")) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                dialogo.setTitle("Se guardó correctamente").setMessage("Se han registrado datos de paciente").setPositiveButton("OK", null).show();
                AdmisionComponent pacienteGuardado = admisionServices.getPacienteByCodigopaciente(paciente.getNroIdentificacion());
                System.out.println("GUARDADO: "+pacienteGuardado.toString());
            }*/
        }
    }

    private Boolean camposValidos() {
        Boolean isValid = true;

        if(txtTemCorp.getText()==null || txtNombrePac.getText().toString().isEmpty()) {
            binding.tilcorporalPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilcorporalPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilcorporalPreconsulta.setError(null);
        }
        if(txtPrecArt.getText()==null || txtFechaNac.getText().toString().isEmpty()) {
            binding.tilpresionArtPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilpresionArtPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilpresionArtPreconsulta.setError(null);
        }
        if(txtFrecueRespi.getText()==null || txtTemCorp.getText().toString().isEmpty()) {
            binding.tilfrecuenRespPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilfrecuenRespPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilfrecuenRespPreconsulta.setError(null);
        }
        if(txtPulso.getText()==null || txtPrecArt.getText().toString().isEmpty()) {
            binding.tilpulsoPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilpulsoPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilpulsoPreconsulta.setError(null);
        }
        if(txtPeso.getText()==null || txtPrecArt.getText().toString().isEmpty()) {
            binding.tilpesoPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilpesoPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilpesoPreconsulta.setError(null);
        }
        if(txtTalla.getText()==null || txtPrecArt.getText().toString().isEmpty()) {
            binding.tiltallaPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tiltallaPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tiltallaPreconsulta.setError(null);
        }
        if(txtImc.getText()==null || txtPrecArt.getText().toString().isEmpty()) {
            binding.tilimcPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilimcPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilimcPreconsulta.setError(null);
        }
        if(txtSaturOxige.getText()==null || txtPrecArt.getText().toString().isEmpty()) {
            binding.tilsaturaOxigPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilsaturaOxigPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilsaturaOxigPreconsulta.setError(null);
        }
        if(txtCircunAbdomi.getText()==null || txtPrecArt.getText().toString().isEmpty()) {
            binding.tilcircAbdoPreconsulta.setError(getString(R.string.common_mandatory_hint));
            binding.tilcircAbdoPreconsulta.requestFocus();
            isValid = false;
        } else {
            binding.tilcircAbdoPreconsulta.setError(null);
        }

        return isValid;
    }
}
