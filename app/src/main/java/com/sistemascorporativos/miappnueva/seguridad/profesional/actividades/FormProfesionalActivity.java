package com.sistemascorporativos.miappnueva.seguridad.profesional.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormProfesionalBinding;
import com.sistemascorporativos.miappnueva.referenciales.especialidad.dao.EspecialidadDao;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.profesional.entidades.ProfesionalDto;
import com.sistemascorporativos.miappnueva.seguridad.profesional.servicios.ProfesionalServices;
import com.sistemascorporativos.miappnueva.seguridad.usuario.servicios.UsuarioServices;

import java.util.ArrayList;

public class FormProfesionalActivity extends AppCompatActivity {

    private ActivityFormProfesionalBinding binding;
    private Spinner spUsuarioProfesional, spEspecialidadProfesional;
    private CheckBox chkEstadoProfesional;
    private TextInputLayout tilNroRegistroProfesional;
    private TextInputEditText etNroRegistroProfesional;
    private Bundle extras;
    private SharedPreferences sharedPref, sharedPref_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        traerDatosAlFormulario();
    }

    private void init() {
        binding = ActivityFormProfesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extras = getIntent().getExtras();
        sharedPref = getSharedPreferences("profesional_referencial", Context.MODE_PRIVATE);
        sharedPref_sesion = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        spUsuarioProfesional = binding.spUsuarioProfesional;
        spEspecialidadProfesional = binding.spEspecialidadProfesional;
        chkEstadoProfesional = binding.chkEstadoProfesional;
        tilNroRegistroProfesional = binding.tilNroRegistroProfesional;
        etNroRegistroProfesional = binding.etNroRegistroProfesional;

        // Cargar valores para spinners
        // Traer usuarios
        UsuarioServices uss = new UsuarioServices(this);
        ArrayList<LoginDto> listaUsuarios = uss.getUsuarios();
        ArrayAdapter<LoginDto> usuariosAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaUsuarios);
        spUsuarioProfesional.setAdapter(usuariosAdapter);

        //Traer especialidades
        EspecialidadDao espdao = new EspecialidadDao(this);
        ArrayList<Especialidad> listaEspecialidades = espdao.getEspecialidades();
        ArrayAdapter<Especialidad> especialidadesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaEspecialidades);
        spEspecialidadProfesional.setAdapter(especialidadesAdapter);
    }

    private void traerDatosAlFormulario() {
        String codigo_medico = sharedPref.getString("codigo_medico", null);
        String nro_registro = sharedPref.getString("prof_numero_registro", null);
        String especialidad_id = sharedPref.getString("espec_id", null);
        String estado = sharedPref.getString("prof_activo", null);
        String operacion = sharedPref.getString("operacion", null);

        etNroRegistroProfesional.requestFocus();
        if(operacion == null) {
            // guardar
            setTitle("Agregar");
            etNroRegistroProfesional.setText(null);
            chkEstadoProfesional.setChecked(true);
        } else {
            // actualizar
            setTitle("Actualizar");
            spUsuarioProfesional.setEnabled(false);
            // set spinner usuario
            for(int i=0; i < spUsuarioProfesional.getCount(); i++) {
                if( ((LoginDto) spUsuarioProfesional.getItemAtPosition(i)).getUsuCodigoUsuario().contains(codigo_medico) ) {
                    spUsuarioProfesional.setSelection(i);
                    break;
                }
            }
            // set campos
            etNroRegistroProfesional.setText(nro_registro);
            // set spinner especialidad
            for(int i=0; i < spEspecialidadProfesional.getCount(); i++) {
                if( ((Especialidad) spEspecialidadProfesional.getItemAtPosition(i)).getEspecialidadId() == Integer.parseInt(especialidad_id) ) {
                    spEspecialidadProfesional.setSelection(i);
                    break;
                }
            }
            // setear el check
            if(estado != null) {
                switch (estado) {
                    case "t":
                        chkEstadoProfesional.setChecked(true);
                        break;
                    case "f":
                        chkEstadoProfesional.setChecked(false);
                        break;
                }
            } else {
                chkEstadoProfesional.setChecked(false);
            }
        }
    }

    private Boolean procesarFormulario() {
        if(!validarFormulario()) return false;
        //Recolección de datos
        String codigo_medico = ( (LoginDto) spUsuarioProfesional.getSelectedItem() ).getUsuCodigoUsuario();
        String nro_registro = etNroRegistroProfesional.getText().toString();
        Integer especialidad_id = ( (Especialidad) spEspecialidadProfesional.getSelectedItem() ).getEspecialidadId();
        String estado = chkEstadoProfesional.isChecked() ? "t" : "f";
        String operacion = sharedPref.getString("operacion", null);
        ProfesionalServices pfs = new ProfesionalServices(this);
        ProfesionalDto obj = new ProfesionalDto();
        obj.setProf_codigo_medico(codigo_medico);
        obj.setProf_numero_registro(nro_registro);
        obj.setEspec_id(especialidad_id);
        obj.setProf_activo(estado);
        // traer usuario de sesion
        String codigo_usuario_login = sharedPref_sesion.getString("codigo_usuario", null);
        if(operacion == null) {
            // guardar
            obj.setCreacion_usuario(codigo_usuario_login);
            obj = pfs.insertarNuevo(obj);
        } else {
            // actualizar
            obj.setModificacion_usuario(codigo_usuario_login);
            obj = pfs.actualizar(obj);
        }
        return obj != null ? true : false;
    }

    private Boolean validarFormulario() {
        Boolean isValid = true;
        String nro_registro = etNroRegistroProfesional.getText().toString().trim();
        if(nro_registro == null) {
            isValid = false;
            tilNroRegistroProfesional.setError(getString(R.string.hint_nro_registro_profesional));
            etNroRegistroProfesional.requestFocus();
        } else {
            tilNroRegistroProfesional.setError(null);
        }
        return isValid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_referencial, menu);
        // Cambia icono del menu topbar
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_save));
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_opt_referencial:
                if(procesarFormulario()) finish();
                else
                    Toast.makeText(this, "Error en operación", Toast.LENGTH_LONG)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}