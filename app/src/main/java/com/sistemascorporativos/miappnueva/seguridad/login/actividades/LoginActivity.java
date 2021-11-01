package com.sistemascorporativos.miappnueva.seguridad.login.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
    }
}