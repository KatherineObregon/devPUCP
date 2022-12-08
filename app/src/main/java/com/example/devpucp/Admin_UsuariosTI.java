package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_UsuariosTI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuarios_ti);
    }

    public void irAnadirUsuarioTI(View view){
        Intent intent = new Intent(Admin_UsuariosTI.this, Admin_crearUsuarioTI.class);
        startActivity(intent);
    }


}