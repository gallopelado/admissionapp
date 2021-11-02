package com.sistemascorporativos.miappnueva.seguridad.login.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityLoginBinding;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.login.servicios.LoginServices;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TextInputEditText etUser, etPassword;
    private TextInputLayout tilUser, tilPassword;
    private Button btLogin;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        Listeners();
    }

    private void init() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        etUser = binding.etUser;
        etPassword = binding.etPassword;
        tilUser = binding.tilUser;
        tilPassword = binding.tilPassword;
        btLogin = binding.btLogin;
        sharedPref = getSharedPreferences("login_preferences", Context.MODE_PRIVATE);
    }

    private void Listeners() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarLogin();
            }
        });
    }

    private Boolean validarFormulario() {
        Boolean isValid = true;
        String user = etUser.getText().toString().trim();
        String password = etPassword.getText().toString();
        if(user==null || user.isEmpty()) {
            tilUser.setError(getString(R.string.usuario_hint));
            tilUser.requestFocus();
            isValid=false;
        } else {
            tilUser.setError(null);
        }
        if(password==null || password.isEmpty()) {
            tilPassword.setError(getString(R.string.password_hint));
            tilPassword.requestFocus();
            isValid=false;
        } else {
            tilPassword.setError(null);
        }
        return isValid;
    }

    private void procesarLogin() {
        if(!validarFormulario()) return;
        String user = etUser.getText().toString().trim();
        String password = etPassword.getText().toString();
        LoginServices lgs = new LoginServices(this);
        LoginDto usuario = lgs.checkUserAndPassword(user, password);
        if(usuario.getUsuCodigoUsuario() != null) {
            tilUser.setError(null);
            tilPassword.setError(null);
            //Existe el usuario, guardamos un sharepreferences lo necesario
            editor = sharedPref.edit();
            editor.putString("codigo_usuario", usuario.getUsuCodigoUsuario());
            editor.putString("nombres_usuario", usuario.getUsuNombres());
            editor.putString("apellidos_usuario", usuario.getUsuApellidos());
            editor.putString("descripcion_usuario", usuario.getUsuApellidos());
            editor.putString("rol_usuario", usuario.getUsuRol());
            editor.commit();
            //Es conveniente crear un log de accesos
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();
            //Enviar vista al menú de inicio
            Intent inicio = new Intent(this, InicioMenuPrincipalActivity.class);
            inicio.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inicio);
        } else {
            tilUser.setError(getString(R.string.usuario_hint));
            tilPassword.setError(getString(R.string.password_hint));
            tilUser.requestFocus();
            Toast.makeText(this, "Error al iniciar la sesión", Toast.LENGTH_LONG).show();
        }
    }
}