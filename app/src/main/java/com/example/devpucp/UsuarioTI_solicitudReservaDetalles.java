package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UsuarioTI_solicitudReservaDetalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_solicitud_reserva_detalles);
    }

    public void rechazarSolicitud(View view){
        Intent intent = new Intent(UsuarioTI_solicitudReservaDetalles.this, UsuarioTI_solicitudRechazo.class);
        startActivity(intent);
    }
}