package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.devpucp.Adapters.ListaCantidadTotaldeEquiposPrestadosAdapter;
import com.example.devpucp.Adapters.ListaCantidadTotaldeEquiposPrestadosMasAdapter;
import com.example.devpucp.Entities.Dispositivo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_reporteEquipoMasPrestado extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    AutoCompleteTextView tipos;
    AutoCompleteTextView marcas;

    ArrayAdapter<String> adapterAreas;
    ArrayAdapter<String> adapterAreas2;

    String marcaFiltro="";
    String tipoFiltro="";

    String[] marcasDinamicasList ;

    TextView textViewTotalPrestados;
    int totalprestados;


    private List<Dispositivo> firebaseDispositivos = new ArrayList<>();
    private ArrayList<Dispositivo> firebaseDispositivosFiltrado = new ArrayList<>();
    private ArrayList<String> marcasDinamicasArray = new ArrayList<>();
    ListaCantidadTotaldeEquiposPrestadosMasAdapter ListaCantidadTotaldeEquiposPrestadosMasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reporte_equipo_mas_prestado);
        //textViewTotalPrestados = findViewById(R.id.textView_Admin_TotalPrestadosMas);



        firebaseDatabase = FirebaseDatabase.getInstance();



        /*TODO INICIO SELECTOR
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
        });*/

        ref= firebaseDatabase.getReference().child("dispositivos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Dispositivo dispositivo = d.getValue(Dispositivo.class);
                    if(!marcasDinamicasArray.contains(dispositivo.getMarca())) {
                        marcasDinamicasArray.add(dispositivo.getMarca());
                    }
                }
                int i=0;
                marcasDinamicasList = new String[marcasDinamicasArray.size()];
                for(String marca : marcasDinamicasArray){
                    marcasDinamicasList[i]=marca;
                    Log.d("msg", "en marcas dinamicas list:"+ marca);
                    i++;
                }
                /*adapterAreas2 = new ArrayAdapter<>(Admin_reporteEquiposPrestados.this, R.layout.drop_down_item, marcasDinamicasList);

                marcas= findViewById(R.id.Cliente_filtroMarcaDispOpciones);
                marcas.setAdapter(adapterAreas2);

                marcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        marcaFiltro = marcas.getText().toString();
                        cargarDatosdeFirebase();
                        //Toast.makeText(Cliente_dispositivosDisponibles.this, marcas.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        String[] marcasList = new String[]{"Lenovo", "HP", "Sasung", "Huawei"};
//        adapterAreas2 = new ArrayAdapter<>(this, R.layout.drop_down_item, marcasList);
//
//        marcas= findViewById(R.id.Cliente_filtroMarcaDispOpciones);
//        marcas.setAdapter(adapterAreas2);
//
//        marcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                marcaFiltro = marcas.getText().toString();
//                cargarDatosdeFirebase();
//                //Toast.makeText(Cliente_dispositivosDisponibles.this, marcas.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //TODO FIN SELECTOR


        firebaseDispositivos.clear();
        firebaseDispositivosFiltrado.clear();
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewDispositivosPrestadosAdminMas);
        Log.d("msg", "despues de cargar y filtra vuelve a on creatr eventos");
        ListaCantidadTotaldeEquiposPrestadosMasAdapter = new ListaCantidadTotaldeEquiposPrestadosMasAdapter(Admin_reporteEquipoMasPrestado.this,firebaseDispositivosFiltrado);
        recyclerView.setAdapter(ListaCantidadTotaldeEquiposPrestadosMasAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Admin_reporteEquipoMasPrestado.this));




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


        ref= firebaseDatabase.getReference().child("dispositivos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Dispositivo dispositivo = d.getValue(Dispositivo.class);
                    firebaseDispositivos.add(dispositivo);
                    totalprestados += Integer.parseInt(dispositivo.getStockPrestados());
                    Log.d("contar", String.valueOf(totalprestados));
                    //textViewTotalPrestados.setText(String.valueOf(totalprestados));




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
                if(Integer.parseInt(d.getStock())>0) {
                    firebaseDispositivosFiltrado.add(d);
                }
            }
        }else {
            if (tipoFiltro.equalsIgnoreCase("") && marcaFiltro.equalsIgnoreCase("")) {
                for (Dispositivo d : firebaseDispositivos) {
                    if(Integer.parseInt(d.getStock())>0) {
                        firebaseDispositivosFiltrado.add(d);
                    }
                }
            }
            if (!tipoFiltro.equalsIgnoreCase("") || !marcaFiltro.equalsIgnoreCase("")) {
                for (Dispositivo d : firebaseDispositivos) {
                    if (d.getTipo().equalsIgnoreCase(tipoFiltro) || d.getMarca().equalsIgnoreCase(marcaFiltro)) {

                        if(Integer.parseInt(d.getStock())>0) {
                            firebaseDispositivosFiltrado.add(d);
                        }
                    }
                }
            }
        }

        ListaCantidadTotaldeEquiposPrestadosMasAdapter.notifyDataSetChanged();





    }
}