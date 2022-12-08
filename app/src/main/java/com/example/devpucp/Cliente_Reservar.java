package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.devpucp.Entities.Dispositivo;
import com.example.devpucp.Entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cliente_Reservar extends AppCompatActivity {


    Dispositivo dispositivo = new Dispositivo();
    Usuario usuarioActual = new Usuario();

    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;

    DatabaseReference usersRef2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_reservar);
        firebaseDatabase= FirebaseDatabase.getInstance();

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        usersRef2= firebaseDatabase.getReference().child("usuario");

        Intent intent = getIntent();
        dispositivo = (Dispositivo) intent.getSerializableExtra("dispositivoReserva");

        if(currentUser!=null){
            Log.d("msg", "PRIMER IF "+ currentUser.getDisplayName());
            usersRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        usuarioActual = snapshot1.getValue(Usuario.class);

                        String keyUsuario = usuarioActual.getKey();
                        String currentUserID= currentUser.getUid();
                        if(keyUsuario.equalsIgnoreCase(currentUserID)) {
                            break;
                        }

                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }






    }
    public void reservaCompletada(View view){
        Intent intent = new Intent(Cliente_Reservar.this, HomeCliente.class);
        startActivity(intent);
    }
}