package com.example.devpucp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class UsuarioTI_anadirDispositivo extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    StorageReference storageRef;
    AutoCompleteTextView tipoTV;
    ImageView imageView;

    TextInputEditText marca ;
    TextInputEditText caracteristicas ;
    TextInputEditText accesorios ;
    TextInputEditText stock ;

    String imageUrl;
    DatabaseReference dispositivosRef;
    String rutaImagen;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_anadir_dispositivo);

        //TODO SELECTOR INICIO
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        dispositivosRef = firebaseDatabase.getReference().child("dispositivos");
        storageRef = FirebaseStorage.getInstance().getReference().child(currentUser.getUid());

        imageView = findViewById(R.id.UserTI_imageViewDispAnadir);

        String[] dirigidoA = new String[]{"Laptop", "Tableta", "Celular", "Monitor"};
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, R.layout.drop_down_item, dirigidoA);

        tipoTV= findViewById(R.id.textInputTipoOpciones);
        tipoTV.setAdapter(adapterTipo);

        tipoTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(Admin_EventosAnadir.this, dirigidoaTV.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //TODO SELECTOR FIN

    }
    public void subirFotoDispositivo(View view) {
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
                    Toast.makeText(UsuarioTI_anadirDispositivo.this, "Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                    imageUrl = "";
                    updateImageView();
                }
            }

    );

    public void updateImageView(){
        if(!imageUrl.isEmpty() && entroSubida){
            Glide.with(UsuarioTI_anadirDispositivo.this).load(imageUrl).into(imageView);
        }
    }

    public void tomarFoto(View view){

        abrirCamara();

    }
    boolean entroTomarFoto =false;
    public void abrirCamara(){
        entroTomarFoto=true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            File imagenArchivo =null;

            try {
                imagenArchivo = crearImagen();
            } catch (IOException e) {
                Log.e("Error", e.toString());
            }

            if(imagenArchivo!=null){
                Uri fotoUri = FileProvider.getUriForFile(this, "com.example.devpucp.fileprovider", imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent,1);
            }

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(entroTomarFoto) {
            StorageReference imgRef = storageRef.child(rutaImagen);
            if (requestCode == 1 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imgBitmap = (Bitmap) extras.get("data");
                Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
                imageView.setImageBitmap(imgBitmap);

                // Get the data from an ImageView as bytes

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data2 = baos.toByteArray();

                UploadTask uploadTask = imgRef.putBytes(data2);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Log.d("msg", "se susbio a storage cloud");
                        imgRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                            entroSubida = true;
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
                });


            }
        }
    }
    private File crearImagen() throws IOException {
        String nombreImagen="foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);

        rutaImagen = imagen.getAbsolutePath();
        return imagen;

    }







    public void guardarDispositivo(View view){
        marca = findViewById(R.id.UsuarioTI_inputAnadirDispMarca);
        caracteristicas = findViewById(R.id.UsuarioTI_inputAnadirDispCaracteristicas);
        accesorios = findViewById(R.id.UsuarioTI_inputAnadirDispAccesorios);
        stock = findViewById(R.id.UsuarioTI_inputAnadirDispStock);

        boolean datosllenos=false;
        boolean stockEsNumerico = false;

        int stockInt=0;
        try{
            stockInt = Integer.parseInt(stock.getText().toString());
            stockEsNumerico=true;
        } catch (Exception e) {
            e.printStackTrace();
            stock.setError("Debe ser un número");
        }

        if(marca.getText().toString().equalsIgnoreCase("") || caracteristicas.getText().toString().equalsIgnoreCase("")
                || accesorios.getText().toString().equalsIgnoreCase("") || (stock.getText().toString().equalsIgnoreCase(""))||
                tipoTV.getText().toString().equalsIgnoreCase("") || (!entroSubida && !entroTomarFoto)
        ){
            Toast.makeText(this,"Debe llenar todos los campos correctamente.", Toast.LENGTH_SHORT).show();
        }else {
            datosllenos=true;
        }

        if(stockEsNumerico && datosllenos){
            DatabaseReference newDispositivoRef = dispositivosRef.push();
            Dispositivo dispositivo = new Dispositivo();
            dispositivo.setKey(newDispositivoRef.getKey());
            dispositivo.setTipo(tipoTV.getText().toString());
            dispositivo.setCaracteristicas(caracteristicas.getText().toString());
            dispositivo.setAccesorios(accesorios.getText().toString());
            dispositivo.setMarca(marca.getText().toString());
            dispositivo.setStock(stock.getText().toString());
            dispositivo.setStockPrestados("0");
            if(entroSubida || entroTomarFoto){
                dispositivo.setFotoUrl(imageUrl);
            }


            newDispositivoRef.setValue(dispositivo).addOnCompleteListener(task -> {
                Toast.makeText(this, "Dispositivo creado exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
            });

        }




    }

}