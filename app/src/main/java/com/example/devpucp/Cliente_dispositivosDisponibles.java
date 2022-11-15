package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class Cliente_dispositivosDisponibles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_dispositivos_disponibles);

        String[] tiposList = new String[]{"Laptop", "Monitor", "Tablet"};
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(this, R.layout.drop_down_item, tiposList);

        AutoCompleteTextView tipos= findViewById(R.id.Cliente_filtroTipoDispOpciones);
        tipos.setAdapter(adapterAreas);

        tipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(Cliente_dispositivosDisponibles.this, tipos.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        String[] marcasList = new String[]{"Laptop", "Monitor", "Tablet"};
        ArrayAdapter<String> adapterAreas2 = new ArrayAdapter<>(this, R.layout.drop_down_item, marcasList);

        AutoCompleteTextView marcas= findViewById(R.id.Cliente_filtroTipoDispOpciones);
        marcas.setAdapter(adapterAreas2);

        marcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(Cliente_dispositivosDisponibles.this, marcas.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}