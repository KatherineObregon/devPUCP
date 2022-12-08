package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Admin_editarUsuarioTI extends AppCompatActivity {

    StorageReference storageRef;
    FirebaseDatabase firebaseDatabase;

    Usuario usuario = new Usuario();
    ImageView imageView;
    DatabaseReference usersRef2;

    TextView correo;
    TextInputEditText codigo;
    TextInputEditText nombreApellido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editar_usuario_ti);

        Intent intent = getIntent();

        usuario = (Usuario) intent.getSerializableExtra("usuarioDetalle");

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef2 = firebaseDatabase.getReference().child("usuario").child(usuario.getKey());
        storageRef = FirebaseStorage.getInstance().getReference().child(usuario.getKey());

        correo = findViewById(R.id.textViewCorreoEditarTI);
        codigo = findViewById(R.id.input_Admin_editarCodigoPUCPTI);
        nombreApellido= findViewById(R.id.inputAdmin_editarNombreRegistroTI);

        imageView = findViewById(R.id.imageView_editarUsuarioTI);



        if(usuario.getFotoUrl()!=null){
            Glide.with(Admin_editarUsuarioTI.this).load(usuario.getFotoUrl()).into(imageView);
        }


        nombreApellido.setText(usuario.getNombreApellido());
        correo.setText(usuario.getCorreo());
        codigo.setText(usuario.getCodigo());



    }

    public void actualizarUsuario(View view){
        String nombreNuevo = nombreApellido.getText().toString();
        String codigoNuevo = codigo.getText().toString();
        usuario.setNombreApellido(nombreNuevo);
        usuario.setCodigo(codigoNuevo);

        usersRef2.setValue(usuario).addOnSuccessListener(unused -> {
            Toast.makeText(Admin_editarUsuarioTI.this, "Se actualizó usuario correctamente.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Admin_UsuariosTI.class);
            startActivity(intent);


        });

    }







}