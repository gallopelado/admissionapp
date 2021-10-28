package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.AdmisionComponent;
import com.sistemascorporativos.miappnueva.admision.servicios.AdmisionServices;
import com.sistemascorporativos.miappnueva.databinding.ActivityOtrosDatosBinding;
import com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.modelos.NivelEducativoDto;
import com.sistemascorporativos.miappnueva.referenciales.seguro_medico.modelos.SeguroMedicoDto;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.modelos.SituacionLaboralDto;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class OtrosDatosActivity extends AppCompatActivity {

    private ActivityOtrosDatosBinding binding;
    private SharedPreferences sharedPref;
    private MapView myOpenMapView;
    private MapController myMapController;
    Spinner comboSeguroMedico, comboNivelEducativo, comboSituacionLaboral;
    AutoCompleteTextView txtestadocivil;
    TextInputEditText txtNroHijos, txtLatitud, txtLongitud;
    String codigo_paciente = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtrosDatosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // iniciar sharepreferences
        sharedPref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        // habilita flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(getString(R.string.titulo_otros_datos));

        txtNroHijos = binding.etNroHijos;
        txtLatitud = binding.etLatitud;
        txtLongitud = binding.etLongitud;

        //Poblar estado civil
        txtestadocivil = binding.etEstadoCivil;
        ArrayAdapter<String> ecivilAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ECIVIL);
        txtestadocivil.setAdapter(ecivilAdapter);

        // Llenar combos
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

        // Recuperar valores del otro activity
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null){
                codigo_paciente = "";
            } else {
                codigo_paciente = extras.getString("codigo_paciente");
                txtestadocivil.setText(extras.getString("estadocivil"), false);
                for(int i=0; i<comboSeguroMedico.getCount();i++) {
                    if(((SeguroMedicoDto)comboSeguroMedico.getItemAtPosition(i)).getSegId() == extras.getInt("seguromedico")) {
                        comboSeguroMedico.setSelection(i);
                        break;
                    }
                }
                txtNroHijos.setText(String.valueOf(extras.getInt("nrohijos")));
                for(int i=0; i<comboNivelEducativo.getCount();i++) {
                    if(((NivelEducativoDto)comboNivelEducativo.getItemAtPosition(i)).getEduId() == extras.getInt("niveleducativo")) {
                        comboNivelEducativo.setSelection(i);
                        break;
                    }
                }
                for(int i=0; i<comboSituacionLaboral.getCount();i++) {
                    if(((SituacionLaboralDto)comboSituacionLaboral.getItemAtPosition(i)).getSitlabId() == extras.getInt("situacionlaboral")) {
                        comboSituacionLaboral.setSelection(i);
                        break;
                    }
                }
                Double latitud = extras.getDouble("latitud");
                Double longitud = extras.getDouble("longitud");
                if(latitud!=null) {
                    txtLatitud.setText(String.valueOf(latitud));
                }
                if(longitud!=null) {
                    txtLongitud.setText(String.valueOf(longitud));
                }
                if(latitud!=null && longitud!=null) {
                    // Localizacion en el mapa
                    //load/initialize the osmdroid configuration, this can be done
                    Context ctx = getApplicationContext();
                    Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
                    GeoPoint myLocation = new GeoPoint(latitud, longitud);
                    myOpenMapView = (MapView) binding.mapPaciente;
                    myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
                    myOpenMapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
                    myOpenMapView.setMultiTouchControls(true);
                    myMapController = (MapController) myOpenMapView.getController();
                    myMapController.setCenter(myLocation);
                    myMapController.setZoom(19);
                    Marker startMarker = new Marker(myOpenMapView);
                    startMarker.setPosition(myLocation);
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    myOpenMapView.getOverlays().add(startMarker);
                }
            }
        }
    }

    private static final String[] ECIVIL = new String[] {
            "SOLTERO/A", "CASADO/A", "CONCUBINATO"
    };

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
                //Intent intent = new Intent(this, AsignarMedicoActivity.class);
                //startActivity(intent);
                guardarFormularioOtrosDatos();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarFormularioOtrosDatos() {
        Integer seguroMedico = ((SeguroMedicoDto)comboSeguroMedico.getSelectedItem()).getSegId();
        Integer nroHijos = null;
        if(!txtNroHijos.getText().toString().isEmpty()) {
            nroHijos = Integer.parseInt(txtNroHijos.getText().toString());
        }
        String estadoCivil = txtestadocivil.getText().toString();
        Integer nivelEducativo = ((NivelEducativoDto)comboNivelEducativo.getSelectedItem()).getEduId();
        Integer situacionLaboral = ((SituacionLaboralDto)comboSituacionLaboral.getSelectedItem()).getSitlabId();
        Double latitud = null;
        Double longitud = null;
        if(!txtLatitud.getText().toString().isEmpty()) {
            latitud = Double.parseDouble(txtLatitud.getText().toString());
        }
        if(!txtLongitud.getText().toString().isEmpty()) {
            longitud = Double.parseDouble(txtLongitud.getText().toString());
        }
        AdmisionServices admisionServices = new AdmisionServices(this);
        AdmisionComponent paciente = new AdmisionComponent();
        paciente.setNroIdentificacion(codigo_paciente);
        paciente.setSegId(seguroMedico);
        paciente.setHijos(nroHijos);
        paciente.setEstado_civil(estadoCivil);
        paciente.setEduId(nivelEducativo);
        paciente.setSitlabId(situacionLaboral);
        paciente.setLatitud(latitud);
        paciente.setLongitud(longitud);
        paciente = admisionServices.actualizarPacienteOtrosDatos(paciente);
        if(!paciente.getOperacion().isEmpty()) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Se actualizó correctamente").setMessage("Se han actualizado datos de paciente").setPositiveButton("OK", null).show();
            //AdmisionComponent pacienteGuardado = admisionServices.getPacienteByCodigopaciente(paciente.getNroIdentificacion());
            //System.out.println("UPDATE: "+pacienteGuardado.toString());
            irAmedicoAsignacion(paciente.getNroIdentificacion(), paciente.getSegId());
        }
    }

    private void irAmedicoAsignacion(String codigo_paciente, Integer seguro_medico) {
        // Guardamos antes en el store para combinar con el codigo medico de la tabla.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("codigo_paciente", codigo_paciente);
        editor.putInt("seguro_medico", seguro_medico);
        editor.commit();

        Intent medicoAsignacionView = new Intent(this, AsignarMedicoActivity.class);
        startActivity(medicoAsignacionView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

        }
    }
}