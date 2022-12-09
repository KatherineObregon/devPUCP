package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.devpucp.Adapters.HistorialClienteAdapter;
import com.example.devpucp.Adapters.SolicitudesPrestamoClienteAdapter;
import com.example.devpucp.Entities.Solicitud;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cliente_historialPrestamos extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    private List<Solicitud> firebaseSolicitudes = new ArrayList<>();
    private ArrayList<Solicitud> firebaseSolicitudesFiltrado= new ArrayList<>();
    HistorialClienteAdapter historialClienteAdapter;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_historial_prestamos);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseSolicitudes.clear();
        firebaseSolicitudesFiltrado.clear();
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewHistorialCliente);
        Log.d("msg", "despues de cargar y filtra vuelve a on creatr eventos");
        historialClienteAdapter = new HistorialClienteAdapter(Cliente_historialPrestamos.this,firebaseSolicitudesFiltrado);
        recyclerView.setAdapter(historialClienteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cliente_historialPrestamos.this));


    }

    @Override
    protected void onResume() {

        cargarDatosdeFirebase();
        super.onResume();
    }


    public void cargarDatosdeFirebase(){
        firebaseSolicitudes.clear();
        Log.d("msg", "entra a cargar datos");
        ref= firebaseDatabase.getReference().child("solicitudes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Solicitud solicitud = d.getValue(Solicitud.class);

                    firebaseSolicitudes.add(solicitud);
                }
                filtrarEventos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void filtrarEventos(){
        firebaseSolicitudesFiltrado.clear();
        for (Solicitud s : firebaseSolicitudes){
            if((s.getEstado().equalsIgnoreCase("Aprobado") || (s.getEstado().equalsIgnoreCase("Rechazado")) )&& s.getPersonaKey().equalsIgnoreCase(currentUser.getUid())) {

                firebaseSolicitudesFiltrado.add(s);
            }
        }
        historialClienteAdapter.notifyDataSetChanged();





    }
}