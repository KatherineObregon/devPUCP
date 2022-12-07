package com.example.devpucp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.devpucp.Entities.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button empecemos;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.launcher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersRef2= firebaseDatabase.getReference().child("usuario");
        Log.d("msg-fb","esta en on create");
        if(currentUser!=null) {
            usersRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Usuario usuario1 = snapshot1.getValue(Usuario.class);

                        String keyUsuario = usuario1.getKey();
                        String currentUserID = currentUser.getUid();
                        if (keyUsuario.equalsIgnoreCase(currentUserID)) {
                            Log.d("msg-fb", "si entra a key igual");
                            if (usuario1.getTipoUsuario().equalsIgnoreCase("UsuarioTI")) {
                                startActivity(new Intent(MainActivity.this, UsuarioTI_home.class));
                                finish();
                            } else if (usuario1.getTipoUsuario().equalsIgnoreCase("Cliente")) {
                                startActivity(new Intent(MainActivity.this, HomeCliente.class));
                                finish();
                            }else if (usuario1.getTipoUsuario().equalsIgnoreCase("Admin")) {
                                startActivity(new Intent(MainActivity.this, Admin_home.class));
                                finish();
                        }
                    }
                }



            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        empecemos = findViewById(R.id.btn_empecemos);

        empecemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login(){
        Intent fbIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build()
                        )
                )
                .build();
        activityResultLauncher.launch(fbIntent);
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(), this::onSignInOnResult);

    private void onSignInOnResult(FirebaseAuthUIAuthenticationResult result) {
        //IdpResponse idpResponse = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Log.d("msg-fb", currentUser.getUid());
            DatabaseReference usersRef = firebaseDatabase.getReference().child("usuario").child(currentUser.getUid());
            Usuario usuario = new Usuario();
            DatabaseReference  usersRef2= firebaseDatabase.getReference().child("usuario");
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()) {
                        startActivity(new Intent(MainActivity.this,Registro.class));
                        finish();

                    } else{
                        usersRef2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    Usuario usuario1 = snapshot1.getValue(Usuario.class);

                                    String keyUsuario = usuario1.getKey();
                                    String currentUserID= currentUser.getUid();
                                    if(keyUsuario.equalsIgnoreCase(currentUserID)) {
                                        Log.d("msg-fb", "si entra a key igual");
                                        if (usuario1.getTipoUsuario().equalsIgnoreCase("UsuarioTI")) {
                                            startActivity(new Intent(MainActivity.this, UsuarioTI_home.class));
                                            finish();
                                        } else if (usuario1.getTipoUsuario().equalsIgnoreCase("Cliente")) {
                                            startActivity(new Intent(MainActivity.this, HomeCliente.class));
                                            finish();
                                        }else if (usuario1.getTipoUsuario().equalsIgnoreCase("Admin")) {
                                            startActivity(new Intent(MainActivity.this, Admin_home.class));
                                            finish();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });




//
//

        } else {
            Toast.makeText(this, "Ha ocurrido un error al iniciar sesión.", Toast.LENGTH_SHORT).show();
//            Log.d("msg-fb", "error al loguearse");
        }
    }


}