package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UsuarioTI_MiPerfil extends AppCompatActivity {
    StorageReference storageRef;
    FirebaseDatabase firebaseDatabase;
    String imageUrl;
    boolean cambioImagen=false;
    Usuario usuario = new Usuario();
    ImageView imageView;
    DatabaseReference usersRef2;
    String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_mi_perfil);

        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersRef2 = firebaseDatabase.getReference().child("usuario").child(currentUser.getUid());
        storageRef = FirebaseStorage.getInstance().getReference().child(currentUser.getUid());

        Intent intent = getIntent();
        TextView miNombre= findViewById(R.id.textView_UsuarioTIMiPerfilNombre);
        TextView miCodigo= findViewById(R.id.textView_UsuarioTIMiPerfilCodigo);
        TextView miCorreo= findViewById(R.id.textView_UsuarioTIMiPerfilCorreo);
        TextView miRol=findViewById(R.id.textView_UsuarioTIMiPerfiRol);
        usuario = (Usuario) intent.getSerializableExtra("usuarioActual");
        if(usuario.getFotoUrl()!=null){
            Glide.with(UsuarioTI_MiPerfil.this).load(usuario.getFotoUrl()).into(imageView);
        }

        miNombre.setText(usuario.getNombreApellido());
        miCodigo.setText(usuario.getCodigo());
        miCorreo.setText(usuario.getCorreo());
        miRol.setText(usuario.getRol());
    }
}