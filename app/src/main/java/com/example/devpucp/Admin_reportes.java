package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_reportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reportes);
    }

    public void irReportesTotal(View view){
        Intent intent = new Intent(Admin_reportes.this, Admin_reporteEquiposPrestados.class);
        startActivity(intent);
    }
    public void irReportesMarca(View view){
        Intent intent = new Intent(Admin_reportes.this, Admin_reporteEquiposPrestMarca.class);
        startActivity(intent);
    }

    public void irReportesMasPrestado(View view){
        Intent intent = new Intent(Admin_reportes.this, Admin_reporteEquipoMasPrestado.class);
        startActivity(intent);
    }

    public void irListaUsuarios(View view){
        Intent intent = new Intent(Admin_reportes.this, Admin_reporteListaUsuarios.class);
        startActivity(intent);
    }
}