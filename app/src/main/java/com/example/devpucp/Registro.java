package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        String[] rolesList = new String[]{"Alumno", "Docente", "Administrativo"};
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(this, R.layout.drop_down_item, rolesList);

        AutoCompleteTextView roles= findViewById(R.id.textInputRol);
        roles.setAdapter(adapterAreas);

        roles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(Registro.this, roles.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void registrar(View view){
        Intent intent = new Intent(Registro.this, UsuarioTI_home.class);
        startActivity(intent);
    }
}