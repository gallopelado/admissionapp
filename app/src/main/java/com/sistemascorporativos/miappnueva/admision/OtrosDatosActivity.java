package com.sistemascorporativos.miappnueva.admision;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.dao.AdmisionDao;
import com.sistemascorporativos.miappnueva.databinding.ActivityOtrosDatosBinding;

public class OtrosDatosActivity extends AppCompatActivity {

    private ActivityOtrosDatosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_otros_datos);
        binding = ActivityOtrosDatosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // habilita flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
                //aqui se pone el metodo para recolectar y guardar datos en SQLITE
                Intent intent = new Intent(this, AsignarMedicoActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

        }
    }
}