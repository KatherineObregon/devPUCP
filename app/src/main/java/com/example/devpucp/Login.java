package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.fondo_verde);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void irAregistrarme(View view){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
    }
}