package com.sistemascorporativos.miappnueva.seguridad.usuario.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.hash.Hashing;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormUsuarioBinding;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.usuario.servicios.UsuarioServices;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class FormUsuarioActivity extends AppCompatActivity {

    private ActivityFormUsuarioBinding binding;
    private Bundle extras;
    private SharedPreferences sharedPref, sharedPref_sesion;
    private TextInputLayout tilCodigoUsuario, tilPassword, tilNombresUsuario, tilApellidosUsuario, tilDescripcionUsuario;
    private TextInputEditText etICodigoUsuario, etPassword,etNombresUsuario, etApellidosUsuario, etDescripcionUsuario;
    private Spinner spRolUsuario;
    private CheckBox chkEstadoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        traerDatosAlFormulario();
    }

    private void init() {
        binding = ActivityFormUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = getSharedPreferences("usuario_referencial", Context.MODE_PRIVATE);
        sharedPref_sesion = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
        extras = getIntent().getExtras();

        // Inicializar elementos del formulario
        tilCodigoUsuario = binding.tilCodigoUsuario;
        tilPassword = binding.tilPassword;
        tilNombresUsuario = binding.tilNombresUsuario;
        tilApellidosUsuario = binding.tilApellidosUsuario;
        tilDescripcionUsuario = binding.tilDescripcionUsuario;
        //Edit texts
        etICodigoUsuario = binding.etICodigoUsuario;
        etPassword = binding.etPassword;
        etNombresUsuario = binding.etNombresUsuario;
        etApellidosUsuario = binding.etApellidosUsuario;
        etDescripcionUsuario = binding.etDescripcionUsuario;
        // Combo spinner
        spRolUsuario = binding.spRolUsuario;
        // Checkbox
        chkEstadoUsuario = binding.chkEstadoUsuario;

        // Cargar valores para roles
        ArrayList<String> listaRoles = new ArrayList<>();
        listaRoles.add("ADMINISTRADOR");
        listaRoles.add("ADMISION");
        listaRoles.add("PRECONSULTA");
        listaRoles.add("CONSULTA");

        // establecemos el adaptador para roles
        ArrayAdapter<String> rolesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaRoles);
        spRolUsuario.setAdapter(rolesAdapter);
    }

    private void traerDatosAlFormulario() {
        String codigo_usuario = sharedPref.getString("codigo_usuario", null);
        String nombres_usuario = sharedPref.getString("nombres_usuario", null);
        String apellidos_usuario = sharedPref.getString("apellidos_usuario", null);
        String descripcion_usuario = sharedPref.getString("descripcion_usuario", null);
        String rol_usuario = sharedPref.getString("rol_usuario", null);
        String estado_usuario = sharedPref.getString("estado_usuario", null);

        if(codigo_usuario== null || codigo_usuario.isEmpty()) {
            // guardar
            setTitle("Agregar");
            etICodigoUsuario.setText(null);
            etPassword.setText(null);
            etNombresUsuario.setText(null);
            etApellidosUsuario.setText(null);
            etDescripcionUsuario.setText(null);
        } else {
            // actualizar
            setTitle("Actualizar");
            etICodigoUsuario.setEnabled(false);
            etICodigoUsuario.setText(codigo_usuario);
            etNombresUsuario.setText(nombres_usuario);
            etApellidosUsuario.setText(apellidos_usuario);
            etDescripcionUsuario.setText(descripcion_usuario);

            // setear el rol
            if(rol_usuario != null) {
                for(int i = 0; i < spRolUsuario.getCount(); i++) {
                    if(((String) spRolUsuario.getItemAtPosition(i)).contains(rol_usuario)) {
                        spRolUsuario.setSelection(i);
                        break;
                    }
                }
            }

            // setear el check
            if(estado_usuario != null) {
                switch (estado_usuario) {
                    case "t":
                        chkEstadoUsuario.setChecked(true);
                        break;
                    case "f":
                        chkEstadoUsuario.setChecked(false);
                        break;
                }
            } else {
                chkEstadoUsuario.setChecked(true);
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Boolean procesarFormulario() {
        if(!validarFormulario()) return false;
        // Recolección de datos
        String codigo_usuario = etICodigoUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String nombres = etNombresUsuario.getText().toString().trim();
        String apellidos = etApellidosUsuario.getText().toString().trim();
        String descripcion = etDescripcionUsuario.getText().toString().trim();
        Boolean estado = chkEstadoUsuario.isChecked();
        String rol = ((String)spRolUsuario.getSelectedItem()).toString();
        LoginDto usuario = new LoginDto();
        UsuarioServices usus = new UsuarioServices(this);

        // Set al modelo de usuario
        usuario.setUsuCodigoUsuario(codigo_usuario);

        if(password != null && password.length() > 0) {
            // el usuario ha escrito su clave y necesitamos cifrarlo
            password = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();
            usuario.setUsuPassword(password);
        }

        usuario.setUsuNombres(nombres);
        usuario.setUsuApellidos(apellidos);
        usuario.setUsuDescripcion(descripcion);
        usuario.setUsuEstado( estado!= null && estado ? "t" : estado!= null && estado==false ? "f" : "f");
        usuario.setUsuRol(rol);

        String operacion_usuario = sharedPref.getString("operacion_usuario", null);

        // traer usuario de sesion
        String codigo_usuario_login = sharedPref_sesion.getString("codigo_usuario", null);
        usuario.setUsuCreacion_usuario(codigo_usuario_login);

        if(operacion_usuario == null) {
            //guardar
            if(usuario.getUsuPassword().isEmpty()) {
                tilPassword.setError(getString(R.string.hint_password));
                etPassword.requestFocus();
                return false;
            }
            usuario = usus.agregarUsuario(usuario);
            if(usuario.getOperacion()!=null) {
                Toast.makeText(this, "Se guardó exitoso", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se ha guardado", Toast.LENGTH_LONG).show();
            }

        } else {
            //modificar
            usus.actualizarUsuarioNoPassword(usuario);
        }

        return true;
    }

    private Boolean validarFormulario() {
        AtomicReference<Boolean> isValid = new AtomicReference<>(true);
        String codigo_usuario = etICodigoUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String nombres = etNombresUsuario.getText().toString().trim();
        String apellidos = etApellidosUsuario.getText().toString().trim();
        String descripcion = etDescripcionUsuario.getText().toString().trim();
        Boolean estado = chkEstadoUsuario.isChecked();

        if(codigo_usuario == null) {
            isValid.set(false);
            tilCodigoUsuario.setError(getString(R.string.hint_codigo_usuario));
            etICodigoUsuario.requestFocus();
        } else {
            tilCodigoUsuario.setError(null);
        }

        if(nombres == null) {
            isValid.set(false);
            tilNombresUsuario.setError(getString(R.string.hint_nombres));
            etNombresUsuario.requestFocus();
        } else {
            tilNombresUsuario.setError(null);
        }

        if(apellidos == null) {
            isValid.set(false);
            tilApellidosUsuario.setError(getString(R.string.hint_apellidos));
            etApellidosUsuario.requestFocus();
        } else {
            tilApellidosUsuario.setError(null);
        }

        if(descripcion == null) {
            isValid.set(false);
            tilDescripcionUsuario.setError(getString(R.string.hint_descripcion_referencial));
            etDescripcionUsuario.requestFocus();
        } else {
            tilDescripcionUsuario.setError(null);
        }

        /*if(estado == false) {
            isValid.set(false);
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("¡Cuidado!").setTitle("Estado del usuario")
                    .setMessage("¿Desea desactivar este usuario?")
                    .setPositiveButton("SI", (dialog, which) -> {
                        isValid.set(true);
                    })
                    .setNegativeButton("NO", (dialog, which) -> {
                        chkEstadoUsuario.setChecked(true);
                    }).show();
        }*/
        return isValid.get();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_referencial, menu);
        // Cambia icono del menu topbar
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_save));
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_opt_referencial:
                if(procesarFormulario()) finish();
                else Toast.makeText(this, "No se ha realizado la operacion", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}