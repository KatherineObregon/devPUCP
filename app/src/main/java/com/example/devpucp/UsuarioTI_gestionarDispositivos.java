package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.devpucp.Adapters.ListaDispositivosUsuarioTIAdapter;
import com.example.devpucp.Entities.Dispositivo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsuarioTI_gestionarDispositivos extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    private List<Dispositivo> firebaseDispositivos = new ArrayList<>();
    private ArrayList<Dispositivo> firebaseDispositivosFiltrado = new ArrayList<>();
    ListaDispositivosUsuarioTIAdapter listaDispositivosUsuarioTIAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_gestionar_dispositivos);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDispositivos.clear();
        firebaseDispositivosFiltrado.clear();
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewDispositivosTI);
        Log.d("msg", "despues de cargar y filtra vuelve a on creatr eventos");
        listaDispositivosUsuarioTIAdapter = new ListaDispositivosUsuarioTIAdapter(UsuarioTI_gestionarDispositivos.this,firebaseDispositivosFiltrado);
        recyclerView.setAdapter(listaDispositivosUsuarioTIAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(UsuarioTI_gestionarDispositivos.this));
    }

    @Override
    protected void onResume() {

        cargarDatosdeFirebase();
        super.onResume();
    }


    public void cargarDatosdeFirebase(){
        firebaseDispositivos.clear();
        Log.d("msg", "entra a cargar datos");
        ref= firebaseDatabase.getReference().child("dispositivos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Dispositivo dispositivo = d.getValue(Dispositivo.class);
                    firebaseDispositivos.add(dispositivo);
                }
                filtrarEventos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void filtrarEventos(){
        firebaseDispositivosFiltrado.clear();
        for (Dispositivo d : firebaseDispositivos){
            firebaseDispositivosFiltrado.add(d);
        }
        listaDispositivosUsuarioTIAdapter.notifyDataSetChanged();


    }

    public void anadirDispositivo(View view){
        Intent intent = new Intent(UsuarioTI_gestionarDispositivos.this, UsuarioTI_anadirDispositivo.class);
        startActivity(intent);
    }
}