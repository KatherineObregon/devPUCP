package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.devpucp.Adapters.ListaUsuariosTIAdapter;
import com.example.devpucp.Entities.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_UsuariosTI extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    private List<Usuario> firebaseUsuarios = new ArrayList<>();
    private ArrayList<Usuario> firebaseUsuariosFiltrado = new ArrayList<>();
    ListaUsuariosTIAdapter listaUsuariosTIAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuarios_ti);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUsuarios.clear();
        firebaseUsuariosFiltrado.clear();
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewUsuariosTI);
        Log.d("msg", "despues de cargar y filtra vuelve a on creatr eventos");
        listaUsuariosTIAdapter = new ListaUsuariosTIAdapter(Admin_UsuariosTI.this,firebaseUsuariosFiltrado);
        recyclerView.setAdapter(listaUsuariosTIAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Admin_UsuariosTI.this));
    }

    @Override
    protected void onResume() {

        cargarDatosdeFirebase();
        super.onResume();
    }


    public void cargarDatosdeFirebase(){
        firebaseUsuarios.clear();
        Log.d("msg", "entra a cargar datos");
        ref= firebaseDatabase.getReference().child("usuario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Usuario usuario = d.getValue(Usuario.class);
                    Log.d("msg", "nombre evento"+usuario.getNombreApellido());
                    firebaseUsuarios.add(usuario);
                }
                filtrarEventos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void filtrarEventos(){
        firebaseUsuariosFiltrado.clear();
        for (Usuario u : firebaseUsuarios){
            if(u.getTipoUsuario().equalsIgnoreCase("UsuarioTI")) {

                firebaseUsuariosFiltrado.add(u);
            }
        }
        listaUsuariosTIAdapter.notifyDataSetChanged();





    }

    public void irAnadirUsuarioTI(View view){
        Intent intent = new Intent(Admin_UsuariosTI.this, Admin_crearUsuarioTI.class);
        startActivity(intent);
    }


}