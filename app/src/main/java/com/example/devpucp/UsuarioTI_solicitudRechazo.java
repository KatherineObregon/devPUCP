package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UsuarioTI_solicitudRechazo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_solicitud_rechazo);
    }

    public void confirmarRechazo(View view){
        Intent intent = new Intent(UsuarioTI_solicitudRechazo.this, UsuarioTI_solicitudesReserva.class);
        startActivity(intent);
    }
}