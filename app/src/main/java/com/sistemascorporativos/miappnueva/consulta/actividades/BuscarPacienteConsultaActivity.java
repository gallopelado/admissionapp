package com.sistemascorporativos.miappnueva.consulta.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.consulta.entidades.ConsultaComponent;
import com.sistemascorporativos.miappnueva.consulta.servicios.ConsultaServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityBuscarPacienteConsultaBinding;

public class BuscarPacienteConsultaActivity extends AppCompatActivity {

    private ActivityBuscarPacienteConsultaBinding binding;
    private SharedPreferences sharedPreferences;
    private TextInputEditText txtBuscarPaciente;
    private Button btRealizarBusqueda;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuscarPacienteConsultaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btRealizarBusqueda = binding.btRealizarBusqueda;
        txtBuscarPaciente = binding.etBuscarPacienteConsulta;

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
                ConsultaServices consultaServices = new ConsultaServices(BuscarPacienteConsultaActivity.this);
                if(!codigoPaciente.isEmpty()) {
                    binding.tilBuscarPacienteConsulta.setError(null);
                    ConsultaComponent paciente = consultaServices.getPacienteByCodigopaciente(codigoPaciente);
                    //No existe paciente
                    if(paciente.getOperacion()==null) {
                        binding.tilBuscarPacienteConsulta.setError(getText(R.string.helper_ci));
                        AlertDialog.Builder dialogo = new AlertDialog.Builder(BuscarPacienteConsultaActivity.this);
                        dialogo.setTitle("No existe paciente").setMessage("¿Desea agregar un nuevo paciente?")
                                .setPositiveButton("Si",(dialog, which) -> {
                                    Intent intent = new Intent(BuscarPacienteConsultaActivity.this, FormularioConsultaActivity.class);
                                    intent.putExtra("codigo_paciente", codigoPaciente);
                                    intent.putExtra("operacion", "");
                                    startActivity(intent);
                                }).setNegativeButton("No", null).show();
                    } else {
                        //Existe paciente
                        Intent intent = new Intent(BuscarPacienteConsultaActivity.this, FormularioConsultaActivity.class);
                        intent.putExtra("operacion", paciente.getOperacion());
                        startActivity(intent);
                    }
                } else {
                    binding.tilBuscarPacienteConsulta.setError(getText(R.string.helper_ci));
                    binding.tilBuscarPacienteConsulta.requestFocus();
                    Toast.makeText(BuscarPacienteConsultaActivity.this, "Debe ingresar el número de CI", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}