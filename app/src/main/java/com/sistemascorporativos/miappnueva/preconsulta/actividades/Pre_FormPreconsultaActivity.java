package com.sistemascorporativos.miappnueva.preconsulta.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class Pre_FormPreconsultaActivity extends AppCompatActivity {

    private ActivityFormularioPreconsultaBinding binding;
    private SharedPreferences sharedPreferences;
    static final Integer RC_EDIT = 21;
    PreconsultaService preconsultaServices;
    private Bundle extras;
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
        binding = ActivityFormularioPreconsultaBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        this.setTitle(getString(R.string.titulo_preconsulta));

        // habilita flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Iniciar el sharedpreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


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
        
        
        // Iniciar una instancia del service
        preconsultaServices = new PreconsultaService(this);
        ArrayList<CiudadDto> listaCiudades = preconsultaServices.getCiudades();
        ArrayList<NacionalidadDto> listaNacionalidades = preconsultaServices.getNacionalidades();
        ArrayAdapter<CiudadDto> ciudadAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaCiudades);
        ArrayAdapter<NacionalidadDto> nacionalidadAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaNacionalidades);

        // Asignar evento click al datepicker de fecha de nacimiento - Despliega el calendario
        txtPrecArt.setOnClickListener(new View.OnClickListener() {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker picker = builder.setTitleText("Seleccionar fecha").build();

            @Override
            public void onClick(View v) {
                picker.addOnPositiveButtonClickListener(timeInMilliseconds -> {
                    // obtener el tiempo en milisegundos y formatear
                    SimpleDateFormat dateStr = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
                    dateStr.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String fecha = dateStr.format(timeInMilliseconds);
                    txtPrecArt.setText(fecha);//setear campo
                });
                picker.show(getSupportFragmentManager(), picker.toString());
            }
        });

        // Cuando se hace click en un spinner, combo o lista
//        ciudadCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int ciuId = ((CiudadDto) parent.getSelectedItem()).getCiuId();
//                String ciudad = ((CiudadDto) parent.getSelectedItem()).getCiuDescripcion();
//                Toast.makeText(Pre_FormPreconsultaActivity.this, ciuId+" - "+ciudad, Toast.LENGTH_LONG).show();
//            }

//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        //Traer extras
        if(savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras.getString("operacion").isEmpty()) {
                txtNombrePac.setEnabled(true);
                txtNombrePac.setText(extras.getString("codigo_paciente"));
            } else {
                txtNombrePac.setEnabled(false);
                txtNombrePac.setText(extras.getString("codigo_paciente"));
                txtFechaNac.setText(extras.getString("nombres"));
                txtTemCorp.setText(extras.getString("apellidos"));


                txtPrecArt.setText(extras.getString("fechanac"));


                txtFrecueRespi.setText(extras.getString("lugarnac"));
                txtPulso.setText(extras.getString("correo"));
                txtPeso.setText(extras.getString("telefono"));
                txtTalla.setText(extras.getString("domicilio"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preconsulta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_opciones:
                if(validarAntes()) {
                    Intent intent = new Intent(this, NavegacionActivity.class);
                    intent.putExtra("codigo_paciente", txtNombrePac.getText().toString());
                    intent.putExtra("seguromedico", extras.getInt("seguromedico"));
                    intent.putExtra("nrohijos", extras.getInt("nrohijos"));
                    intent.putExtra("estadocivil", extras.getString("estadocivil"));
                    intent.putExtra("niveleducativo", extras.getInt("niveleducativo"));
                    intent.putExtra("situacionlaboral", extras.getInt("situacionlaboral"));
                    intent.putExtra("latitud", extras.getDouble("latitud"));
                    intent.putExtra("longitud", extras.getDouble("longitud"));
                    startActivity(intent);
                }
                break;
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