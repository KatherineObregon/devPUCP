package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UsuarioTI_gestionarDispositivos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_gestionar_dispositivos);
    }
    public void anadirDispositivo(View view){
        Intent intent = new Intent(UsuarioTI_gestionarDispositivos.this, UsuarioTI_editarDispositivo.class);
        startActivity(intent);
    }
}