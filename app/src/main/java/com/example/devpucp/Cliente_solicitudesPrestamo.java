package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.devpucp.Adapters.ListaUsuariosTIAdapter;
import com.example.devpucp.Adapters.SolicitudesPrestamoClienteAdapter;
import com.example.devpucp.Entities.Solicitud;
import com.example.devpucp.Entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cliente_solicitudesPrestamo extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    private List<Solicitud> firebaseSolicitudes = new ArrayList<>();
    private ArrayList<Solicitud> firebaseSolicitudesFiltrado= new ArrayList<>();
    SolicitudesPrestamoClienteAdapter solicitudesPrestamoClienteAdapter;


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_solicitudes_prestamo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseSolicitudes.clear();
        firebaseSolicitudesFiltrado.clear();
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewSolicitudesCliente);
        Log.d("msg", "despues de cargar y filtra vuelve a on creatr eventos");
        solicitudesPrestamoClienteAdapter = new SolicitudesPrestamoClienteAdapter(Cliente_solicitudesPrestamo.this,firebaseSolicitudesFiltrado);
        recyclerView.setAdapter(solicitudesPrestamoClienteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cliente_solicitudesPrestamo.this));

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
            if(s.getEstado().equalsIgnoreCase("Pendiente") && s.getPersonaKey().equalsIgnoreCase(currentUser.getUid())) {

                firebaseSolicitudesFiltrado.add(s);
            }
        }
        solicitudesPrestamoClienteAdapter.notifyDataSetChanged();





    }







}