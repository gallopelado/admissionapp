package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.servicios.AdmisionServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityOtrosDatosBinding;
import com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.modelos.NivelEducativoDto;
import com.sistemascorporativos.miappnueva.referenciales.seguro_medico.modelos.SeguroMedicoDto;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.modelos.SituacionLaboralDto;

import java.util.ArrayList;

public class OtrosDatosActivity extends AppCompatActivity {

    private ActivityOtrosDatosBinding binding;
    Spinner comboSeguroMedico, comboNivelEducativo, comboSituacionLaboral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtrosDatosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // habilita flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        comboSeguroMedico = binding.spSeguroMedico;
        comboNivelEducativo = binding.spNivelEducativo;
        comboSituacionLaboral = binding.spSituacionLaboral;
        AdmisionServices adms = new AdmisionServices(this);
        ArrayList<SeguroMedicoDto> listaSeguroMedico = adms.getSeguroMedico();
        ArrayList<NivelEducativoDto> listaNivelEducativo = adms.getNivelEducativo();
        ArrayList<SituacionLaboralDto> listaSituacionLaboral = adms.getSituacionLaboral();
        ArrayAdapter<SeguroMedicoDto> seguroAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaSeguroMedico);
        ArrayAdapter<NivelEducativoDto> nivelEducativoAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaNivelEducativo);
        ArrayAdapter<SituacionLaboralDto> situacionLaboralAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaSituacionLaboral);
        comboSeguroMedico.setAdapter(seguroAdapter);
        comboNivelEducativo.setAdapter(nivelEducativoAdapter);
        comboSituacionLaboral.setAdapter(situacionLaboralAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_otros_datos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_guardar_otros_datos:
                //finish();
                //aqui se pone el metodo para recolectar y guardar datos en SQLITE
                Intent intent = new Intent(this, AsignarMedicoActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

        }
    }
}