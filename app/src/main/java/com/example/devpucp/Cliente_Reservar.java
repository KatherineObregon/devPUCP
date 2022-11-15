package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Cliente_Reservar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_reservar);
    }
    public void reservaCompletada(View view){
        Intent intent = new Intent(Cliente_Reservar.this, HomeCliente.class);
        startActivity(intent);
    }
}