package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devpucp.Entities.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    AutoCompleteTextView roles;


    TextInputEditText codigoPUCP;
    //TextInputEditText nombres,apellidos,codigoPUCP,correo,contraseña1,contraseña2;
    TextInputLayout nombres_textInputLayout,apellidos_textInputLayout,codigoPUCP_textInputLayout,correo_textInputLayout,contraseña1_textInputLayout,contraseña2_textInputLayout;
    Button btn_registrarUsuario;
    private FirebaseFirestore mFireStore;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        firebaseDatabase = FirebaseDatabase.getInstance();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        TextView nombre = findViewById(R.id.textViewNombreRegistro) ;
        nombre.setText(currentUser.getDisplayName());

        //TODO SELECTOR INICIO

        String[] rolesList = new String[]{"Alumno", "Docente", "Administrativo"};
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(this, R.layout.drop_down_item, rolesList);

        roles= findViewById(R.id.textInputRol);
        roles.setAdapter(adapterAreas);

        roles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(Registro.this, roles.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //TODO SELECTOR FIN





        btn_registrarUsuario= findViewById(R.id.registrarUsuario);



    }

    public void guardarRegistro(View view){
        codigoPUCP = findViewById(R.id.inputCodigo_Registro);


        DatabaseReference usersRef = firebaseDatabase.getReference().child("usuario").child(currentUser.getUid());


        boolean codigoEsNumerico = false;
        boolean codigo8Digitos=false;
        boolean datosllenos=false;
        int codigoInt=0;
        try{
            codigoInt = Integer.parseInt(codigoPUCP.getText().toString());
            codigoEsNumerico=true;
        } catch (Exception e) {
            e.printStackTrace();
            codigoPUCP.setError("Debe ser un número de 9 dígitos");
        }
        if (codigoEsNumerico){
            if(codigoInt>=10000000 && codigoInt<=100000000){
                codigo8Digitos=true;
            }else {
                codigoPUCP.setError("Debe ser un número de 9 dígitos");
            }
        }
        if(roles.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Debe llenar todos los campos correctamente.", Toast.LENGTH_SHORT).show();

        }else {
            datosllenos=true;
        }

        if(datosllenos && codigo8Digitos){
            Usuario usuario = new Usuario();
            usuario.setKey(currentUser.getUid());
            usuario.setNombreApellido(currentUser.getDisplayName());
            usuario.setCorreo(currentUser.getEmail());
            usuario.setRol(roles.getText().toString());
            usuario.setCodigo(codigoPUCP.getText().toString());
            usuario.setTipoUsuario("Cliente");

            usersRef.setValue(usuario).addOnCompleteListener(task -> {
                System.out.println("usuario creado exitosamente");
                startActivity(new Intent(Registro.this,HomeCliente.class));
                finish();
            });





        }




    }



}