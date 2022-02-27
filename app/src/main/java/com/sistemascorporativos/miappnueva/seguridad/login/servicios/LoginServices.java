package com.sistemascorporativos.miappnueva.seguridad.login.servicios;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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
import com.google.common.hash.Hashing;
import com.sistemascorporativos.miappnueva.seguridad.login.dao.LoginDao;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginServices {

    private Context ctx;
    private LoginDao dao;
    public LoginDto usuarioRes = new LoginDto();

    public LoginServices(Context ctx) {
        this.ctx = ctx;
        this.dao = new LoginDao(ctx);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto agregarUsuario(LoginDto login) {
        return dao.agregarUsuario(login);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto modificarPassword(LoginDto login) {
        return dao.modificarPassword(login);
    }

    public LoginDto getUsuario(String codigo_usuario) {
        return dao.getUsuario(codigo_usuario);
    }

    public LoginDto checkUserAndPassword(String user, String password) {
        String password_hash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        LoginDto usuario = dao.getUserAndPassword(user, password_hash);
        return usuario!=null ? usuario : null;
    }

    public LoginDto checkUserAndPasswordRest(String user, String password) {
        //final LoginDto[] usuario = {new LoginDto()};
        //ArrayList<LoginDto> lista = new ArrayList<>();
        //LoginDto usuario = new LoginDto();
        String password_hash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        String url = "http://10.0.2.2:5000/apiv1/seguridad/login/check_user_password";
        /*StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    usuario[0].setUsuPassword(jsonObject.getString("usuPassword"));
                    usuario[0].setUsuNombres(jsonObject.getString("usuNombres"));
                    usuario[0].setUsuApellidos(jsonObject.getString("usuApellidos"));
                    usuario[0].setUsuDescripcion(jsonObject.getString("usuDescripcion"));
                    usuario[0].setUsuRol(jsonObject.getString("usuRol"));
                    usuario[0].setUsuEstado(jsonObject.getString("usuEstado"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        })
        {
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("codigo_usuario", user);
                params.put("password", password_hash);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(postRequest);*/

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            //String URL = "http://...";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("codigo_usuario", user);
            jsonBody.put("password", password_hash);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY caramba", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        usuarioRes.setUsuPassword(jsonObject.getString("usuPassword"));
                        usuarioRes.setUsuNombres(jsonObject.getString("usuNombres"));
                        usuarioRes.setUsuApellidos(jsonObject.getString("usuApellidos"));
                        usuarioRes.setUsuDescripcion(jsonObject.getString("usuDescripcion"));
                        usuarioRes.setUsuRol(jsonObject.getString("usuRol"));
                        usuarioRes.setUsuEstado(jsonObject.getString("usuEstado"));
                        //lista.add(usuario[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
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
        return usuarioRes !=null ? usuarioRes : null;
    }
}
