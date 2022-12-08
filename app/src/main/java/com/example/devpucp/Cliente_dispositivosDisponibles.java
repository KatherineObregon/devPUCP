package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.devpucp.Adapters.ListaDispositivosDisponiblesClienteAdapter;
import com.example.devpucp.Adapters.ListaDispositivosUsuarioTIAdapter;
import com.example.devpucp.Entities.Dispositivo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cliente_dispositivosDisponibles extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    AutoCompleteTextView tipos;
    AutoCompleteTextView marcas;

    ArrayAdapter<String> adapterAreas;
    ArrayAdapter<String> adapterAreas2;

    String marcaFiltro="";
    String tipoFiltro="";

    private List<Dispositivo> firebaseDispositivos = new ArrayList<>();
    private ArrayList<Dispositivo> firebaseDispositivosFiltrado = new ArrayList<>();
    ListaDispositivosDisponiblesClienteAdapter listaDispositivosDisponiblesClienteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_dispositivos_disponibles);

        //TODO INICIO SELECTOR
        String[] tiposList = new String[]{"Laptop", "Tableta", "Celular", "Monitor"};
        adapterAreas = new ArrayAdapter<>(this, R.layout.drop_down_item, tiposList);

        tipos= findViewById(R.id.Cliente_filtroTipoDispOpciones);
        tipos.setAdapter(adapterAreas);

        tipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tipoFiltro = tipos.getText().toString();
                cargarDatosdeFirebase();

                //Toast.makeText(Cliente_dispositivosDisponibles.this, tipos.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        String[] marcasList = new String[]{"Lenovo", "HP", "Sasung", "Huawei"};
        adapterAreas2 = new ArrayAdapter<>(this, R.layout.drop_down_item, marcasList);

        marcas= findViewById(R.id.Cliente_filtroMarcaDispOpciones);
        marcas.setAdapter(adapterAreas2);

        marcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                marcaFiltro = marcas.getText().toString();
                cargarDatosdeFirebase();
                //Toast.makeText(Cliente_dispositivosDisponibles.this, marcas.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //TODO FIN SELECTOR

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDispositivos.clear();
        firebaseDispositivosFiltrado.clear();
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewDispositivosCliente);
        Log.d("msg", "despues de cargar y filtra vuelve a on creatr eventos");
        listaDispositivosDisponiblesClienteAdapter = new ListaDispositivosDisponiblesClienteAdapter(Cliente_dispositivosDisponibles.this,firebaseDispositivosFiltrado);
        recyclerView.setAdapter(listaDispositivosDisponiblesClienteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cliente_dispositivosDisponibles.this));





    }

    public void borrarFiltros(View view){

        marcaFiltro="";
        tipoFiltro="";
        cargarDatosdeFirebase();
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
                filtrarDispositivos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void filtrarDispositivos(){
        firebaseDispositivosFiltrado.clear();
        if(tipoFiltro==null && marcaFiltro ==null){
            for (Dispositivo d : firebaseDispositivos) {
                firebaseDispositivosFiltrado.add(d);
            }
        }else {
            if (tipoFiltro.equalsIgnoreCase("") && marcaFiltro.equalsIgnoreCase("")) {
                for (Dispositivo d : firebaseDispositivos) {
                    firebaseDispositivosFiltrado.add(d);
                }
            }
            if (!tipoFiltro.equalsIgnoreCase("") || !marcaFiltro.equalsIgnoreCase("")) {
                for (Dispositivo d : firebaseDispositivos) {
                    if (d.getTipo().equalsIgnoreCase(tipoFiltro) || d.getMarca().equalsIgnoreCase(marcaFiltro)) {

                        firebaseDispositivosFiltrado.add(d);
                    }
                }
            }
        }

        listaDispositivosDisponiblesClienteAdapter.notifyDataSetChanged();





    }
}