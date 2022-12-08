package com.example.devpucp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Dispositivo;
import com.example.devpucp.Entities.Solicitud;
import com.example.devpucp.Entities.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Cliente_Reservar extends AppCompatActivity {


    Dispositivo dispositivo = new Dispositivo();
    Usuario usuarioActual = new Usuario();

    FirebaseDatabase firebaseDatabase;
    StorageReference storageRef;
    FirebaseUser currentUser;

    DatabaseReference usersRef2;
    DatabaseReference solicitudesRef;

    TextInputEditText motivo;
    TextInputEditText curso;
    TextInputEditText tiempoReserva;
    TextInputEditText programas;
    TextInputEditText otros;

    ImageView imageView;
    String imageUrl;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_reservar);
        firebaseDatabase= FirebaseDatabase.getInstance();

        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        solicitudesRef= firebaseDatabase.getReference().child("solicitudes");
        storageRef = FirebaseStorage.getInstance().getReference().child(currentUser.getUid());
        usersRef2= firebaseDatabase.getReference().child("usuario");
        imageView = findViewById(R.id.imageView7);

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

    public void subirFotoDni(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        launcherPhotos.launch(intent);
    }

    boolean entroSubida = false;
    ActivityResultLauncher<Intent> launcherPhotos = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    StorageReference child = storageRef.child(uri.getLastPathSegment());

                    child.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            child.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                entroSubida=true;
                                imageUrl = uri1.toString();
                                //usuario.setFotoUrl(imageUrl);
                                Log.d("msg-test", "ruta archivo: " + imageUrl);
                                updateImageView();
                                //Glide.with(Membresia_MiPerfil.this).load(imageUrl).into(imageView);
                                //usersRef2.setValue(usuario);
                            }).addOnFailureListener(e -> {
                                imageUrl = "";
                                updateImageView();
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("msg", "Fallo subida",e);
                            imageUrl = "";
                            updateImageView();
                        }
                    });
                } else {
                    Toast.makeText(Cliente_Reservar.this, "Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                    imageUrl = "";
                    updateImageView();
                }
            }

    );

    public void updateImageView(){
        if(!imageUrl.isEmpty() && entroSubida){
            Glide.with(Cliente_Reservar.this).load(imageUrl).into(imageView);
        }
    }

    public void crearReserva(View view){

        motivo = findViewById(R.id.input_Cliente_reservaMotivo);
        curso= findViewById(R.id.input_Cliente_reservaCurso);
        tiempoReserva = findViewById(R.id.input_Cliente_reservaTIempo);
        programas = findViewById(R.id.input_Cliente_reservaProgramas);
        otros = findViewById(R.id.input_Cliente_reservaOtros);
        boolean datosllenos=false;
        boolean tiempoEsNumerico = false;
        boolean tiempoMenor30 = false;
        int tiempoInt=0;

        try{
            tiempoInt = Integer.parseInt(tiempoReserva.getText().toString());
            tiempoEsNumerico=true;
        } catch (Exception e) {
            e.printStackTrace();
            tiempoReserva.setError("Debe ser un número entre 1 - 30");
        }
        if (tiempoEsNumerico){
            if(tiempoInt>=1 && tiempoInt<=30){
                tiempoMenor30=true;
            }else {
                tiempoReserva.setError("Debe ser un número entre 1 - 30");
            }
        }


        if(motivo.getText().toString().equalsIgnoreCase("") || curso.getText().toString().equalsIgnoreCase("")
                || tiempoReserva.getText().toString().equalsIgnoreCase("") || (programas.getText().toString().equalsIgnoreCase(""))||
                 !entroSubida
        ){
            Toast.makeText(this,"Debe llenar todos los campos correctamente.", Toast.LENGTH_SHORT).show();
        }else {
            datosllenos=true;
        }

        if(datosllenos && tiempoMenor30 ){

            DatabaseReference newSolicitudRef = solicitudesRef.push();

            Solicitud solicitud = new Solicitud();
            solicitud.setMotivo(motivo.getText().toString());
            solicitud.setCurso(curso.getText().toString());
            solicitud.setTiempoReserva(tiempoReserva.getText().toString());
            solicitud.setProgramas(programas.getText().toString());
            solicitud.setPersonaKey(usuarioActual.getKey());
            solicitud.setDispositivoKey(dispositivo.getKey());
            solicitud.setEstado("Pendiente");
            if(otros.getText().toString().equalsIgnoreCase("")){
                solicitud.setOtros("-");
            }

            if(entroSubida){

                solicitud.setFotoUrl(imageUrl);
            }


            newSolicitudRef.setValue(solicitud).addOnCompleteListener(task -> {
                Toast.makeText(this, "Solicitud creada exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
            });

        }





    }









    public void reservaCompletada(View view){
        Intent intent = new Intent(Cliente_Reservar.this, HomeCliente.class);
        startActivity(intent);
    }
}