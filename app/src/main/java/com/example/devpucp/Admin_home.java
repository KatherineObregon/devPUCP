package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class Admin_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
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
                    return true;
                default:
                    return false;

            }

        });
        popupMenu.show();


    }

    public void irReportesAdmin(View view){
        Intent intent = new Intent(Admin_home.this, Admin_reportes.class);
        startActivity(intent);
    }

    public void irUsuariosTI(View view){
        Intent intent = new Intent(Admin_home.this, Admin_UsuariosTI.class);
        startActivity(intent);
    }
}