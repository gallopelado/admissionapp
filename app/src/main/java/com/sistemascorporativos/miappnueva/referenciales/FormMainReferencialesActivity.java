package com.sistemascorporativos.miappnueva.referenciales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormMainReferencialesBinding;

public class FormMainReferencialesActivity extends AppCompatActivity {

    private ActivityFormMainReferencialesBinding binding;
    private Bundle extras;
    private SharedPreferences sharedPref, sharedPref_menu;
    private TextInputLayout tilIdReferencial, tilDescripcionReferencial;
    private TextInputEditText etIdReferencial, etDescripcionReferencial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        traerDatosAlFormulario();
    }

    private void init() {
        binding = ActivityFormMainReferencialesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences("referenciales", Context.MODE_PRIVATE);
        sharedPref_menu = getSharedPreferences("menu", Context.MODE_PRIVATE);
        extras = getIntent().getExtras();
        tilIdReferencial = binding.tilIdReferencial;
        tilDescripcionReferencial = binding.tilDescripcionReferencial;
        etIdReferencial = binding.etIdReferencial;
        etDescripcionReferencial = binding.etDescripcionReferencial;
    }

    private void traerDatosAlFormulario() {
        String id = sharedPref.getString("referencial_id", null);
        String descripcion = sharedPref.getString("referencial_descripcion", null);
        if(id == null) {
            // guardar
            setTitle("Agregar");
            tilIdReferencial.setVisibility(View.INVISIBLE);
            etIdReferencial.setText(null);
            etDescripcionReferencial.setText(null);
        } else {
            // actualizar
            setTitle("Actualizar");
            etIdReferencial.setEnabled(false);
            etIdReferencial.setText(id);
            etDescripcionReferencial.setText(descripcion);
        }
        etDescripcionReferencial.requestFocus();
    }

    private Boolean procesarFormulario() {
        if(!validarFormulario()) return null;
        String id = sharedPref.getString("referencial_id", null);
        String descripcion = etDescripcionReferencial.getText().toString().trim().toUpperCase();
        ReferencialDao referencialDao = new ReferencialDao(this);
        ReferencialDto referencialDto = new ReferencialDto();
        referencialDto.setDescripcion(descripcion);
        if(id == null) {
            // guardar
            switch (sharedPref_menu.getString("menu", null)) {
                case "ciudad":
                    referencialDto = referencialDao.guardar(referencialDto, "ciudades");
                    break;
                case "nacionalidad":
                    referencialDto = referencialDao.guardar(referencialDto, "nacionalidades");
                    break;
                case "seguro_medico":
                    referencialDto = referencialDao.guardar(referencialDto, "seguro_medico");
                    break;
                case "nivel_educativo":
                    referencialDto = referencialDao.guardar(referencialDto, "nivel_educativo");
                    break;
                case "situacion_laboral":
                    referencialDto = referencialDao.guardar(referencialDto, "situacion_laboral");
                    break;
                case "especialidad":
                    referencialDto = referencialDao.guardar(referencialDto, "especialidad");
                    break;
            }
        } else {
            // actualizar
            referencialDto.setId(id);
            switch (sharedPref_menu.getString("menu", null)) {
                case "ciudad":
                    referencialDto = referencialDao.actualizar(referencialDto, "ciudades");
                    break;
                case "nacionalidad":
                    referencialDto = referencialDao.actualizar(referencialDto, "nacionalidades");
                    break;
                case "seguro_medico":
                    referencialDto = referencialDao.actualizar(referencialDto, "seguro_medico");
                    break;
                case "nivel_educativo":
                    referencialDto = referencialDao.actualizar(referencialDto, "nivel_educativo");
                    break;
                case "situacion_laboral":
                    referencialDto = referencialDao.actualizar(referencialDto, "situacion_laboral");
                    break;
                case "especialidad":
                    referencialDto = referencialDao.actualizar(referencialDto, "especialidad");
                    break;
            }
        }
        return referencialDto == null ? false : true;
    }

    private Boolean validarFormulario() {
        Boolean isValid = true;
        String descripcion = etDescripcionReferencial.getText().toString().trim().toUpperCase();
        if(descripcion == null || descripcion.isEmpty()) {
            isValid = false;
            tilDescripcionReferencial.setError(getString(R.string.hint_descripcion_referencial));
            etDescripcionReferencial.requestFocus();
        } else {
            tilDescripcionReferencial.setError(null);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_opt_referencial:
                procesarFormulario();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}