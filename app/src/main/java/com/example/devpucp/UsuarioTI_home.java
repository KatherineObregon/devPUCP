package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.devpucp.Entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsuarioTI_home extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_home);
        firebaseDatabase = FirebaseDatabase.getInstance();
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
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(UsuarioTI_home.this, MainActivity.class));
                    finish();
                    return true;
                default:
                    return false;

            }

        });
        popupMenu.show();


    }

    public void irGestionarDispositivos(View view){
        Intent intent = new Intent(UsuarioTI_home.this, UsuarioTI_gestionarDispositivos.class);
        startActivity(intent);
    }

    public void miPerfil(View view){

        DatabaseReference usersRef2= firebaseDatabase.getReference().child("usuario");
        if(currentUser!=null){
            usersRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Usuario usuario1 = snapshot1.getValue(Usuario.class);

                        String keyUsuario = usuario1.getKey();
                        String currentUserID= currentUser.getUid();
                        if(keyUsuario.equalsIgnoreCase(currentUserID)) {
                            Intent intent = new Intent(UsuarioTI_home.this, UsuarioTI_MiPerfil.class);
                            intent.putExtra("usuarioActual", usuario1);
                            startActivity(intent);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
    }

    public void solicitudesReserva(View view){
        Intent intent = new Intent(UsuarioTI_home.this, UsuarioTI_solicitudesReserva.class);
        startActivity(intent);
    }
}