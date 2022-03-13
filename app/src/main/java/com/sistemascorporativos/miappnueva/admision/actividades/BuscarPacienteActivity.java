package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.AdmisionComponent;
import com.sistemascorporativos.miappnueva.admision.servicios.AdmisionServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityBuscarPacienteBinding;
import com.sistemascorporativos.miappnueva.referenciales.ListaReferencialesAdapter;
import com.sistemascorporativos.miappnueva.referenciales.ReferencialDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuscarPacienteActivity extends AppCompatActivity {

    private ActivityBuscarPacienteBinding binding;
    private SharedPreferences sharedPreferences;
    private TextInputEditText txtBuscarPaciente;
    private Button btRealizarBusqueda, btPacientesAdmitidos;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuscarPacienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btRealizarBusqueda = binding.btRealizarBusqueda;
        btPacientesAdmitidos = binding.btPacientesAdmitidos;
        txtBuscarPaciente = binding.etBuscarPaciente;

        // Foco al campo y limpiar
        txtBuscarPaciente.setText("");
        txtBuscarPaciente.requestFocus();

        if(savedInstanceState == null) {
            extras = getIntent().getExtras();
        }

        btRealizarBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoPaciente = txtBuscarPaciente.getText().toString().trim();
                // Buscar paciente si existe
                AdmisionServices admisionServices = new AdmisionServices(BuscarPacienteActivity.this);
                if(!codigoPaciente.isEmpty()) {
                    binding.tilBuscarPaciente.setError(null);
                    // GET para buscar paciente
                    String url_buscar_paciente = "http://10.0.2.2:5000/apiv1/admision/get_datos_paciente_by_codigo_paciente/"+codigoPaciente;
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(BuscarPacienteActivity.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_buscar_paciente, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject pacienteEncontrado = new JSONObject(response);
                                    AdmisionComponent paciente = new AdmisionComponent();
                                    if(pacienteEncontrado.isNull("operacion")) {
                                        binding.tilBuscarPaciente.setError(getText(R.string.helper_ci));
                                        AlertDialog.Builder dialogo = new AlertDialog.Builder(BuscarPacienteActivity.this);
                                        dialogo.setTitle("No existe paciente").setMessage("¿Desea agregar un nuevo paciente?")
                                                .setPositiveButton("Si",(dialog, which) -> {
                                                    Intent intent = new Intent(BuscarPacienteActivity.this, FormularioAdmisionActivity.class);
                                                    intent.putExtra("codigo_paciente", codigoPaciente);
                                                    intent.putExtra("operacion", "");
                                                    startActivity(intent);
                                                }).setNegativeButton("No", null).show();
                                    } else {
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
                                        //Existe paciente
                                        Intent intent = new Intent(BuscarPacienteActivity.this, FormularioAdmisionActivity.class);
                                        intent.putExtra("codigo_paciente", paciente.getNroIdentificacion());
                                        intent.putExtra("nombres", paciente.getNombres());
                                        intent.putExtra("apellidos", paciente.getApellidos());
                                        intent.putExtra("sexo", paciente.getSexo());
                                        intent.putExtra("fechanac", paciente.getFechaNacimiento());
                                        intent.putExtra("lugarnac", paciente.getLugarNacimiento());
                                        intent.putExtra("correo", paciente.getCorreo());
                                        intent.putExtra("telefono", paciente.getTelefono());
                                        intent.putExtra("domicilio", paciente.getDireccion());
                                        intent.putExtra("ciudad", paciente.getCiuId());
                                        intent.putExtra("nacionalidad", paciente.getNacId());
                                        intent.putExtra("ciudad", paciente.getCiuId());
                                        intent.putExtra("seguromedico", paciente.getSegId());
                                        intent.putExtra("nrohijos", paciente.getHijos());
                                        intent.putExtra("estadocivil", paciente.getEstado_civil());
                                        intent.putExtra("niveleducativo", paciente.getEduId());
                                        intent.putExtra("situacionlaboral", paciente.getSitlabId());
                                        intent.putExtra("latitud", paciente.getLatitud());
                                        intent.putExtra("longitud", paciente.getLongitud());
                                        intent.putExtra("operacion", paciente.getOperacion());
                                        startActivity(intent);
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
                } else {
                    binding.tilBuscarPaciente.setError(getText(R.string.helper_ci));
                    binding.tilBuscarPaciente.requestFocus();
                    Toast.makeText(BuscarPacienteActivity.this, "Debe ingresar el número de CI", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btPacientesAdmitidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pacienteAdmitidos = new Intent(BuscarPacienteActivity.this, ListaPacientesAdmitidosActivity.class);
                startActivity(pacienteAdmitidos);
            }
        });
    }
}