package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.adaptadores.ListaPacientesAdmitidosAdapter;
import com.sistemascorporativos.miappnueva.admision.dao.AdmisionDao;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAdmitidoDetalle;
import com.sistemascorporativos.miappnueva.databinding.ActivityListaPacientesAdmitidosBinding;
import com.sistemascorporativos.miappnueva.referenciales.ListaReferencialesAdapter;
import com.sistemascorporativos.miappnueva.referenciales.ReferencialDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaPacientesAdmitidosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityListaPacientesAdmitidosBinding binding;
    private RecyclerView listaPacientes;
    SearchView searchViewBuscar;
    private ArrayList<PacienteAdmitidoDetalle> listaArrayPacientes;
    private ListaPacientesAdmitidosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(binding.getRoot());
        setTitle(getString(R.string.titulo_pacientes_admitidos));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaPacientes.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<PacienteAdmitidoDetalle> lista = new ArrayList<>();
        // GET lista de pacientes admitidos
        String url = "http://10.0.2.2:5000/apiv1/admision/get_pacientes_admitidos";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        ArrayList<PacienteAdmitidoDetalle> lista = new ArrayList<>();
                        JSONArray pacientes = new JSONArray(response);
                        for(int i=0; i < pacientes.length(); i++) {
                            JSONObject paciente = pacientes.getJSONObject(i);
                            PacienteAdmitidoDetalle obj = new PacienteAdmitidoDetalle();
                            obj.setConsultando(paciente.getString("medico"));
                            obj.setCedulaPaciente(paciente.getString("pac_codigo_paciente"));
                            obj.setNombrePaciente(paciente.getString("paciente"));
                            lista.add(obj);
                        }
                        adapter = new ListaPacientesAdmitidosAdapter(lista);
                        listaPacientes.setAdapter(adapter);
                        searchViewBuscar.setOnQueryTextListener(ListaPacientesAdmitidosActivity.this);
                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Ciudad-GET-ERROR", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        binding = ActivityListaPacientesAdmitidosBinding.inflate(getLayoutInflater());
        listaPacientes = binding.rvListaPacientesAdmitidos;
        searchViewBuscar = binding.searchViewBuscarPaciente;
        listaArrayPacientes = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pacientes_admitidos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, BuscarPacienteActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}