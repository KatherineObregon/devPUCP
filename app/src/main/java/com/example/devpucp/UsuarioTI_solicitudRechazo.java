package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devpucp.Entities.Solicitud;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioTI_solicitudRechazo extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    EditText justificacion;
    Solicitud solicitud = new Solicitud();
    DatabaseReference solicitudesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_solicitud_rechazo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        justificacion = findViewById(R.id.UsuarioTI_inputJustificacionRechazo);

        Intent intent = getIntent();
        solicitud = (Solicitud) intent.getSerializableExtra("solicitudRechazada");

        solicitudesRef = firebaseDatabase.getReference().child("solicitudes").child(solicitud.getKey());


    }

    public void rechazarSolicitud(View view){

        boolean datolleno=false;

        if(justificacion.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Debe indicar el nombre del lugar de recepcion", Toast.LENGTH_SHORT).show();
        }else {
            datolleno = true;
        }

        if(datolleno){
            solicitud.setEstado("Rechazado");
            solicitud.setJustificacionRechazo(justificacion.getText().toString());
            solicitudesRef.setValue(solicitud).addOnCompleteListener(task -> {
                Toast.makeText(this, "Se aprobó la solicitud de préstamo exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(UsuarioTI_solicitudRechazo.this,UsuarioTI_solicitudesReserva.class);
                startActivity(intent);
            });
        }




    }
}