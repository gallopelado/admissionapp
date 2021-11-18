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
import com.sistemascorporativos.miappnueva.preconsulta.dao.PreconsultaDao;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.Pre_PacienteAdmitido;
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
    private SharedPreferences sharedPref, sharedPref_sesion;
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
        sharedPref_sesion = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);

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

    private Boolean guardarFormularioPreconsulta() {
        String precon_codigo_establecimiento = sharedPref.getString("precon_codigo_establecimiento", null);
        String pacasi_codigo_asignacion = sharedPref.getString("pacasi_codigo_asignacion", null);
        String precon_temperatura_corporal = txtTemCorp.getText().toString();
        String precon_presion_arterial = txtPrecArt.getText().toString();
        String precon_frecuencia_respiratoria = txtFrecueRespi.getText().toString();
        String precon_pulso = txtPulso.getText().toString();
        String precon_peso = txtPeso.getText().toString();
        String precon_talla = txtTalla.getText().toString();
        String precon_imc = txtImc.getText().toString();
        String precon_saturacion = txtSaturOxige.getText().toString();
        String precon_circunferencia_abdominal = txtCircunAbdomi.getText().toString();
        String precon_motivo_consulta = txtMotivoConsulta.getText().toString();

        // Luego mover este metodo al service
        PreconsultaDao pdao = new PreconsultaDao(this);
        Pre_PacienteAdmitido obj = new Pre_PacienteAdmitido();
        obj.setCodigo_establecimiento(precon_codigo_establecimiento);
        obj.setCodigo_asignacion(pacasi_codigo_asignacion);
        if(precon_temperatura_corporal != null)
            obj.setPrecon_temperatura_corporal(Double.parseDouble(precon_temperatura_corporal));
        if(precon_presion_arterial!=null)
            obj.setPrecon_presion_arterial(Double.parseDouble(precon_presion_arterial));
        if(precon_frecuencia_respiratoria!=null)
            obj.setPrecon_frecuencia_respiratoria(Double.parseDouble(precon_frecuencia_respiratoria));
        if(precon_pulso!=null)
            obj.setPrecon_pulso(Double.parseDouble(precon_pulso));
        if(precon_peso!=null)
            obj.setPrecon_peso(Double.parseDouble(precon_peso));
        if(precon_talla!=null)
            obj.setPrecon_talla(Double.parseDouble(precon_talla));
        if(precon_imc!=null)
            obj.setPrecon_imc(Double.parseDouble(precon_imc));
        if(precon_saturacion!=null)
            obj.setPrecon_saturacion(Double.parseDouble(precon_saturacion));
        if(precon_circunferencia_abdominal!=null)
            obj.setPrecon_circunferencia_abdominal(Double.parseDouble(precon_circunferencia_abdominal));
        obj.setPrecon_motivo_consulta(precon_motivo_consulta);
        obj = pdao.actualizarPreConsulta(obj);
        if(obj.getOperacion()!=null)
            Toast.makeText(this, "Preconsulta guardada", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        return true;
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
            case R.id.action_guardar_preconsulta:
                guardarFormularioPreconsulta();
                finish();
                break;
            case android.R.id.home:
                sharedPref.edit().clear().commit();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
