package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.widget.TextView;

import com.example.devpucp.Entities.Solicitud;

public class Cliente_solicitudRechazada extends AppCompatActivity {

    Solicitud solicitud = new Solicitud();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_solicitud_rechazada);

        Intent intent = getIntent();
        solicitud = (Solicitud) intent.getSerializableExtra("historialSolRechazada");
        TextView justificacion = findViewById(R.id.TextViewJustificacionRechazoCLiente);

        justificacion.setText(solicitud.getJustificacionRechazo());


    }
}