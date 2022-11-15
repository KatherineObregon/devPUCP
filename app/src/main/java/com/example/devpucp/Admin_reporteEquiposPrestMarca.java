package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class Admin_reporteEquiposPrestMarca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reporte_equipos_prest_marca);

        String[] marcaList = new String[]{"Laptop", "Monitor", "Tablet"};
        ArrayAdapter<String> adapterMarca = new ArrayAdapter<>(this, R.layout.drop_down_item, marcaList);

        AutoCompleteTextView marcas= findViewById(R.id.Admin_reporteMarcaOpciones);
        marcas.setAdapter(adapterMarca);

        marcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(Admin_reporteEquiposPrestMarca.this, marcas.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}