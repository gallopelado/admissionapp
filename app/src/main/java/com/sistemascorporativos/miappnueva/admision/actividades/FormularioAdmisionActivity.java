package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.AdmisionComponent;
import com.sistemascorporativos.miappnueva.admision.servicios.AdmisionServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormularioAdmisionBinding;
import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos.NacionalidadDto;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.modelos.SituacionLaboralDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class FormularioAdmisionActivity extends AppCompatActivity {

    private ActivityFormularioAdmisionBinding binding;
    private SharedPreferences sharedPreferences;
    static final Integer RC_EDIT = 21;
    AdmisionServices admisionServices;
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
        binding = ActivityFormularioAdmisionBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        this.setTitle(getString(R.string.titulo_admision));
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
        admisionServices = new AdmisionServices(this);
        ArrayList<CiudadDto> listaCiudades = admisionServices.getCiudades();
        ArrayList<NacionalidadDto> listaNacionalidades = admisionServices.getNacionalidades();
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

        // Cuando se hace click en un spinner o combo
        /*ciudadCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int ciuId = ((CiudadDto) parent.getSelectedItem()).getCiuId();
                String ciudad = ((CiudadDto) parent.getSelectedItem()).getCiuDescripcion();
                Toast.makeText(FormularioAdmisionActivity.this, ciuId+" - "+ciudad, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_opciones:
                if(validarAntes()) {
                    Intent intent = new Intent(this, OtrosDatosActivity.class);
                    intent.putExtra("codigo_paciente", txtNroIdentificacion.getText().toString());
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
                guardarFormularioAdmision();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validarAntes() {
        final Boolean[] isValid = {false};
        String numeroIdentificacion = txtNroIdentificacion.getText().toString().trim();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        if(numeroIdentificacion.isEmpty() || numeroIdentificacion==null) {
            dialogo.setTitle("Cuidado")
                    .setMessage("Debe registrar primero este formulario")
                    .setPositiveButton("Salir", null).show();
            isValid[0] =false;
            camposValidos();
        } else {
            // GET para buscar paciente
            String url_buscar_paciente = "http://10.0.2.2:5000/apiv1/admision/get_datos_paciente_by_codigo_paciente/"+numeroIdentificacion;
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(FormularioAdmisionActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_buscar_paciente, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject pacienteEncontrado = new JSONObject(response);
                            if(pacienteEncontrado.isNull("operacion")) {
                                dialogo.setTitle("Cuidado")
                                        .setMessage("Este paciente no existe")
                                        .setPositiveButton("Salir", null).show();
                                isValid[0] =false;
                            } else {
                                isValid[0] =true;
                                AdmisionComponent paciente = new AdmisionComponent();
                                paciente.setCiuId(pacienteEncontrado.getInt("ciuId"));
                                paciente.setEduId(pacienteEncontrado.getInt("eduId"));
                                paciente.setNacId(pacienteEncontrado.getInt("nacId"));
                                paciente.setOperacion(pacienteEncontrado.getString("operacion"));
                                paciente.setApellidos(pacienteEncontrado.getString("pacApellidos"));
                                paciente.setNroIdentificacion(pacienteEncontrado.getString("pacCodigoPaciente"));
                                paciente.setCorreo(pacienteEncontrado.getString("pacCorreoElectronico"));
                                paciente.setDireccion(pacienteEncontrado.getString("pacDireccion"));
                                paciente.setEstado_civil(pacienteEncontrado.getString("pacEstadoCivil"));
                                paciente.setFechaNacimiento(pacienteEncontrado.getString("pacFechaNac"));
                                paciente.setHijos(pacienteEncontrado.getInt("pacHijos"));
                                paciente.setLatitud(pacienteEncontrado.getDouble("pacLatitud"));
                                paciente.setLongitud(pacienteEncontrado.getDouble("pacLongitud"));
                                paciente.setLugarNacimiento(pacienteEncontrado.getString("pacLugarNacimiento"));
                                paciente.setNombres(pacienteEncontrado.getString("pacNombres"));
                                paciente.setSexo(pacienteEncontrado.getString("pacSexo"));
                                paciente.setTelefono(pacienteEncontrado.getString("pacTelefono"));
                                paciente.setSegId(pacienteEncontrado.getInt("segId"));
                                paciente.setSitlabId(pacienteEncontrado.getInt("sitlabId"));
                                paciente.setOperacion(pacienteEncontrado.getString("operacion"));
                            }
                        }  catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Ciudad-GET-ERROR", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };
                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isValid[0];
    }

    private void guardarFormularioAdmision() {
        if(camposValidos()) {
            String nroIdentificacion = txtNroIdentificacion.getText().toString().trim();
            String nombres = txtNombres.getText().toString().trim();
            String apellidos = txtApellidos.getText().toString().trim();
            int selectedSexo = rgSexo.getCheckedRadioButtonId();
            txtSexo = findViewById(selectedSexo);
            String sexo = txtSexo.getText().toString();
            String lugarNacimiento = txtLugarNacimiento.getText().toString().trim();
            String fechaNacimiento = txtFechaNacimiento.getText().toString();
            String correo = txtCorreo.getText().toString().trim();
            String telefono = txtTelefono.getText().toString().trim();
            String domicilio = txtDomicilio.getText().toString().trim();
            Integer ciuId = ((CiudadDto)ciudadCombo.getSelectedItem()).getCiuId();
            Integer nacId = ((NacionalidadDto)nacionalidadCombo.getSelectedItem()).getNacId();

            // Una vez recolectados los datos, se procede a guardar
            AdmisionComponent paciente = new AdmisionComponent();
            paciente.setNroIdentificacion(nroIdentificacion);
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);
            paciente.setSexo(sexo);
            paciente.setLugarNacimiento(lugarNacimiento);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setCorreo(correo);
            paciente.setTelefono(telefono);
            paciente.setDireccion(domicilio);
            paciente.setCiuId(ciuId);
            paciente.setNacId(nacId);

            // POST y PUT
            String url_post = "http://10.0.2.2:5000/apiv1/guardar_paciente", url_put = "http://10.0.2.2:5000/apiv1/actualizar_paciente_formulario_principal";
            String url_buscar_paciente = "http://10.0.2.2:5000/apiv1/admision/get_datos_paciente_by_codigo_paciente/"+nroIdentificacion;
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(FormularioAdmisionActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_buscar_paciente, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject pacienteEncontrado = new JSONObject(response);
                            AdmisionComponent paciente = new AdmisionComponent();
                            if(pacienteEncontrado.isNull("operacion")) {
                                //guardar
                                RequestQueue requestQueue = Volley.newRequestQueue(FormularioAdmisionActivity.this);
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("codigo_paciente", paciente.getNroIdentificacion());
                                jsonBody.put("nombres", paciente.getNombres());
                                jsonBody.put("apellidos", paciente.getApellidos());
                                jsonBody.put("tipo_documento", "CI");
                                jsonBody.put("sexo", paciente.getSexo());
                                jsonBody.put("fecha_nac", paciente.getFechaNacimiento());
                                jsonBody.put("lugar_nac", paciente.getLugarNacimiento());
                                jsonBody.put("ciu_id", paciente.getCiuId());
                                jsonBody.put("correo_electronico", paciente.getCorreo());
                                jsonBody.put("nac_id", paciente.getNacId());
                                jsonBody.put("telefono", paciente.getTelefono());
                                jsonBody.put("direccion", paciente.getDireccion());
                                jsonBody.put("seg_id", paciente.getSegId());
                                jsonBody.put("hijos", paciente.getHijos());
                                jsonBody.put("estado_civil", paciente.getEstado_civil());
                                jsonBody.put("edu_id", paciente.getEduId());
                                jsonBody.put("sitlab_id", paciente.getSitlabId());
                                jsonBody.put("latitud", paciente.getLatitud());
                                jsonBody.put("longitud", paciente.getLongitud());
                                jsonBody.put("creacion_usuario", "sysadmin");
                                final String requestBody = jsonBody.toString();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        url_post, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if(!jsonObject.isNull("estado") && jsonObject.getString("estado")=="correcto") {
                                                AlertDialog.Builder dialogo = new AlertDialog.Builder(FormularioAdmisionActivity.this);
                                                dialogo.setTitle("Se guardó correctamente").setMessage("Se han registrado datos de paciente").setPositiveButton("OK", null).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("ERROR SAVE CIUDAD", error.toString());
                                    }
                                }) {
                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }
                                    @Override
                                    public byte[] getBody() throws AuthFailureError {
                                        try {
                                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                                        } catch (UnsupportedEncodingException uee) {
                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                            return null;
                                        }
                                    }
                                };
                                requestQueue.add(stringRequest);
                            } else {
                                //modificar
                                RequestQueue requestQueue = Volley.newRequestQueue(FormularioAdmisionActivity.this);
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("codigo_paciente", paciente.getNroIdentificacion());
                                jsonBody.put("nombres", paciente.getNombres());
                                jsonBody.put("apellidos", paciente.getApellidos());
                                jsonBody.put("sexo", paciente.getSexo());
                                jsonBody.put("fecha_nac", paciente.getFechaNacimiento());
                                jsonBody.put("lugar_nac", paciente.getLugarNacimiento());
                                jsonBody.put("ciu_id", paciente.getCiuId());
                                jsonBody.put("correo_electronico", paciente.getCorreo());
                                jsonBody.put("nac_id", paciente.getNacId());
                                jsonBody.put("telefono", paciente.getTelefono());
                                jsonBody.put("direccion", paciente.getDireccion());
                                jsonBody.put("modificacion_usuario", "sysadmin");
                                final String requestBody = jsonBody.toString();
                                StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                                        url_post, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if(!jsonObject.isNull("estado") && jsonObject.getString("estado")=="correcto") {
                                                AlertDialog.Builder dialogo = new AlertDialog.Builder(FormularioAdmisionActivity.this);
                                                dialogo.setTitle("Se actualizó correctamente").setMessage("Se han actualizado datos de paciente").setPositiveButton("OK", null).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("ERROR MODIFY CIUDAD", error.toString());
                                    }
                                }) {
                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }
                                    @Override
                                    public byte[] getBody() throws AuthFailureError {
                                        try {
                                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                                        } catch (UnsupportedEncodingException uee) {
                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                            return null;
                                        }
                                    }
                                };
                                requestQueue.add(stringRequest);
                            }

                        }  catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Ciudad-GET-ERROR", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };
                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //fin
            /*if(admisionServices.getPacienteByCodigopaciente(paciente.getNroIdentificacion()).getOperacion()==null) {
                //guardar
                paciente = admisionServices.guardarPaciente(paciente);
                if(paciente.getOperacion().contains("GUARDADO")) {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                    dialogo.setTitle("Se guardó correctamente").setMessage("Se han registrado datos de paciente").setPositiveButton("OK", null).show();
                }
            } else {
                //actualizar
                paciente = admisionServices.actualizarPacienteFormularioPrincipal(paciente);
                if(paciente.getOperacion().contains("UPDATE-FORMPRINCIPAL")) {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                    dialogo.setTitle("Se actualizó correctamente").setMessage("Se han actualizado datos de paciente").setPositiveButton("OK", null).show();
                }
            }*/

        }
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