package com.sistemascorporativos.miappnueva.preconsulta.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.actividades.FormularioAdmisionActivity;
import com.sistemascorporativos.miappnueva.admision.actividades.ListaPacientesAdmitidosActivity;
import com.sistemascorporativos.miappnueva.admision.entidades.AdmisionComponent;
import com.sistemascorporativos.miappnueva.admision.servicios.AdmisionServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityBuscarPacienteBinding;
import com.sistemascorporativos.miappnueva.databinding.ActivityInicioMenuPrincipalBinding;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.PreconsultaComponent;
import com.sistemascorporativos.miappnueva.preconsulta.servicios.PreconsultaService;

public class Pre_BuscarAdmitidoActivity extends AppCompatActivity {

    private ActivityInicioMenuPrincipalBinding binding;
    private SharedPreferences sharedPreferences;
    private TextInputEditText txtBuscarPaciente;
    private Button btRealizarBusqueda, btPacientesAdmitidos;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInicioMenuPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        btRealizarBusqueda = binding.btRealizarBusqueda;
//        btPacientesAdmitidos = binding.btPacientesAdmitidos;
//        txtBuscarPaciente = binding.etBuscarPaciente;

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
                PreconsultaService preconsultaService = new PreconsultaService(Pre_BuscarAdmitidoActivity.this);
                if(!codigoPaciente.isEmpty()) {
//                    binding.tilBuscarPaciente.setError(null);
                    AdmisionComponent paciente = preconsultaService.getPacienteByCodigopaciente(codigoPaciente);
                    //No existe paciente
                    if(paciente.getOperacion()==null) {
//                        binding.tilBuscarPaciente.setError(getText(R.string.helper_ci));
                        AlertDialog.Builder dialogo = new AlertDialog.Builder(Pre_BuscarAdmitidoActivity.this);
                        dialogo.setTitle("No existe paciente").setMessage("¿Desea agregar un nuevo paciente?")
                                .setPositiveButton("Si",(dialog, which) -> {
                                    Intent intent = new Intent(Pre_BuscarAdmitidoActivity.this, FormularioAdmisionActivity.class);
                                    intent.putExtra("codigo_paciente", codigoPaciente);
                                    intent.putExtra("operacion", "");
                                    startActivity(intent);
                                }).setNegativeButton("No", null).show();
                    } else {
                        //Existe paciente
                        Intent intent = new Intent(Pre_BuscarAdmitidoActivity.this, Pre_FormPreconsultaActivity.class);
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
                }
//                else {
//                    binding.tilBuscarPaciente.setError(getText(R.string.helper_ci));
//                    binding.tilBuscarPaciente.requestFocus();
//                    Toast.makeText(Pre_BuscarAdmitidoActivity.this, "Debe ingresar el número de CI", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btPacientesAdmitidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pacienteAdmitidos = new Intent(Pre_BuscarAdmitidoActivity.this, Pre_ListaAdmitidosActivity.class);
                startActivity(pacienteAdmitidos);
            }
        });
    }
}