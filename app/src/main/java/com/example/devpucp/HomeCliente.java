package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class HomeCliente extends AppCompatActivity {
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cerrar_sesion, menu);
        return true;
    }
    public void btnMenuCerrarsesion(MenuItem menuItem){
        Log.d("msg", "btn cerrar sesion");
        View view = findViewById(R.id.btn_cerrarsesion);
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.sub_menu_logout,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem1 -> {
            switch (menuItem1.getItemId()){
                case R.id.btn_menu_logout:

                    Log.d("msg", "btn_cerrar sesion presionado");

                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(HomeCliente.this, MainActivity.class));

                    return true;
                default:
                    return false;

            }

        });
        popupMenu.show();


    }

    public void dispositivosDisponibles(View view){
        Intent intent = new Intent(HomeCliente.this, Cliente_dispositivosDisponibles.class);
        startActivity(intent);
    }

    public void historialPrestamos(View view){
        Intent intent = new Intent(HomeCliente.this, Cliente_historialPrestamos.class);
        startActivity(intent);
    }

    public void solicitudesPrestamos(View view){
        Intent intent = new Intent(HomeCliente.this, Cliente_solicitudesPrestamo.class);
        startActivity(intent);
    }


}