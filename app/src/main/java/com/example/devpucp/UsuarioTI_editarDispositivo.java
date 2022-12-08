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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Dispositivo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UsuarioTI_editarDispositivo extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    StorageReference storageRef;

    Dispositivo dispositivo = new Dispositivo();
    AutoCompleteTextView tipoTV;
    ImageView imageView;

    TextInputEditText marca ;
    TextInputEditText caracteristicas ;
    TextInputEditText accesorios ;
    TextInputEditText stock ;

    String imageUrl;
    DatabaseReference dispositivosRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_editar_dispositivo);

        Intent intent = getIntent();
        dispositivo = (Dispositivo) intent.getSerializableExtra("dispositivoDetalleTI");

        firebaseDatabase = FirebaseDatabase.getInstance();
        dispositivosRef = firebaseDatabase.getReference().child("dispositivos").child(dispositivo.getKey());
        storageRef = FirebaseStorage.getInstance().getReference().child(dispositivo.getKey());

        marca = findViewById(R.id.UsuarioTI_inputEditarDispMarca);
        caracteristicas = findViewById(R.id.UsuarioTI_inputEditarDispCaracteristicas);
        accesorios = findViewById(R.id.UsuarioTI_inputEditarDispAccesorios);
        stock = findViewById(R.id.UsuarioTI_inputEditarDispStock);
        imageView = findViewById(R.id.imageView2);
        tipoTV = findViewById(R.id.textInputTipoEditarOpciones);

        //TODO SELECTOR INICIO


        String[] dirigidoA = new String[]{"Laptop", "Tableta", "Celular", "Monitor"};
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, R.layout.drop_down_item, dirigidoA);
        tipoTV.setText(dispositivo.getTipo());
        tipoTV.setAdapter(adapterTipo);

        tipoTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(Admin_EventosAnadir.this, dirigidoaTV.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //TODO SELECTOR FIN

        if(dispositivo.getFotoUrl()!=null){
            Glide.with(UsuarioTI_editarDispositivo.this).load(dispositivo.getFotoUrl()).into(imageView);

        }
        marca.setText(dispositivo.getMarca());
        caracteristicas.setText(dispositivo.getCaracteristicas());
        accesorios.setText(dispositivo.getAccesorios());
        stock.setText(dispositivo.getStock());



    }
    public void cambiarFotoDisp(View view) {
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
                    Toast.makeText(UsuarioTI_editarDispositivo.this, "Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                    imageUrl = "";
                    updateImageView();
                }
            }

    );
    public void updateImageView(){
        if(!imageUrl.isEmpty() && entroSubida){
            Glide.with(UsuarioTI_editarDispositivo.this).load(imageUrl).into(imageView);
        }
    }





    public void actualizarDispositivo(View view){

        String marcaNuevaStr = marca.getText().toString();
        String caracteristicasNuevasStr = caracteristicas.getText().toString();
        String accesoriosNuevoStr = accesorios.getText().toString();
        String stockNuevoStr = stock.getText().toString();
        String tipoNuevo = tipoTV.getText().toString();

        if(entroSubida){

            dispositivo.setFotoUrl(imageUrl);
        }

        dispositivo.setMarca(marcaNuevaStr);
        dispositivo.setTipo(tipoNuevo);
        dispositivo.setCaracteristicas(caracteristicasNuevasStr);
        dispositivo.setAccesorios(accesoriosNuevoStr);
        dispositivo.setStock(stockNuevoStr);

        dispositivosRef.setValue(dispositivo).addOnSuccessListener(unused -> {
            Toast.makeText(UsuarioTI_editarDispositivo.this, "Se actualizó dispositivo correctamente.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UsuarioTI_gestionarDispositivos.class);
            startActivity(intent);
        });


    }



}