package com.sistemascorporativos.miappnueva.seguridad.profesional.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityProfesionalBinding;

public class ProfesionalActivity extends AppCompatActivity {

    private ActivityProfesionalBinding binding;
    private RecyclerView rvListaProfesionales;
    private SearchView svBuscarProfesional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        binding = ActivityProfesionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}