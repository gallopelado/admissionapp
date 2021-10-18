package com.sistemascorporativos.miappnueva.admision.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityFormularioAdmisionBinding;
import com.sistemascorporativos.miappnueva.db.ConexionDb;

public class FormularioAdmisionActivity extends AppCompatActivity {

    private ActivityFormularioAdmisionBinding binding;
    private SharedPreferences sharedPreferences;
    static final Integer RC_EDIT = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormularioAdmisionBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        // Iniciar el sharedpreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Toast.makeText(getApplicationContext(),"El mensajito desde android", Toast.LENGTH_LONG).show();
        //Iniciar la base de datos
        ConexionDb conexionDb = new ConexionDb(FormularioAdmisionActivity.this);
        SQLiteDatabase db = conexionDb.getWritableDatabase();
        if(db != null) {
            Toast.makeText(FormularioAdmisionActivity.this, "Leyendo base de datos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admision, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_opciones:
                Intent intent = new Intent(this, OtrosDatosActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}