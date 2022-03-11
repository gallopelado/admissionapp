package com.sistemascorporativos.miappnueva.referenciales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormMainReferencialesBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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

    private Boolean procesarFormulario() throws JSONException {
        if(!validarFormulario()) return null;
        String id = sharedPref.getString("referencial_id", null);
        String descripcion = etDescripcionReferencial.getText().toString().trim().toUpperCase();
        ReferencialDao referencialDao = new ReferencialDao(this);
        ReferencialDto referencialDto = new ReferencialDto();
        referencialDto.setDescripcion(descripcion);
        String url = "http://10.0.2.2:5000/apiv1/";
        if(id == null) {
            // guardar
            switch (sharedPref_menu.getString("menu", null)) {
                case "ciudad":
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("ciu_descripcion", descripcion);
                    final String requestBody = jsonBody.toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            url + "referenciales/ciudad/add_ciudad", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ERROR SAVE CIUDAD", error.toString());
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
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("ciu_id", id);
                    jsonBody.put("ciu_descripcion", descripcion);
                    final String requestBody = jsonBody.toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                            url + "referenciales/ciudad/modify_ciudad", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ERROR MODIFY CIUDAD", error.toString());
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
                try {
                    procesarFormulario();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}