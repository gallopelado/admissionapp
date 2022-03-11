package com.sistemascorporativos.miappnueva.seguridad.login.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.hash.Hashing;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityLoginBinding;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.login.servicios.LoginServices;
import com.sistemascorporativos.miappnueva.seguridad.menu_principal.actividades.NavegacionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
        String url = "http://10.0.2.2:5000/apiv1/seguridad/login/check_user_password";
        String password_hash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("codigo_usuario", user);
            jsonBody.put("password", password_hash);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.i("VOLLEY caramba", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(!Objects.equals(jsonObject, null)) {
                            // limpiar campos
                            tilUser.setError(null);
                            tilPassword.setError(null);
                            // guardar en shared
                            editor = sharedPref.edit();
                            editor.putString("codigo_usuario", user);
                            editor.putString("nombres_usuario", jsonObject.getString("usuNombres"));
                            editor.putString("apellidos_usuario", jsonObject.getString("usuApellidos"));
                            editor.putString("descripcion_usuario", jsonObject.getString("usuDescripcion"));
                            editor.putString("rol_usuario", jsonObject.getString("usuRol"));
                            editor.commit();
                            Intent inicio = new Intent(LoginActivity.this, NavegacionActivity.class);
                            inicio.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(inicio);
                        } else {
                            tilUser.setError(getString(R.string.usuario_hint));
                            tilPassword.setError(getString(R.string.password_hint));
                            tilUser.requestFocus();
                            Toast.makeText(LoginActivity.this, "Error al iniciar la sesión", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    if(error.networkResponse.statusCode==404 || error.networkResponse.statusCode==500) {
                        tilUser.setError(getString(R.string.usuario_hint));
                        tilPassword.setError(getString(R.string.password_hint));
                        tilUser.requestFocus();
                        Toast.makeText(LoginActivity.this, "Error al iniciar la sesión", Toast.LENGTH_LONG).show();
                    }
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}