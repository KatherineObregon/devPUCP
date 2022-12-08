package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Admin_crearUsuarioTI extends AppCompatActivity {

    TextInputEditText nombresApellidos,codigoPUCP,correo,contraseña1,contraseña2;

    TextInputLayout nombres_textInputLayout,apellidos_textInputLayout,codigoPUCP_textInputLayout,correo_textInputLayout,contraseña1_textInputLayout,contraseña2_textInputLayout;

    Button btn_registrarUsuarioTI;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    //private FirebaseStore mFireStore;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_crear_usuario_ti);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //mFireStore = FirebaseFirestore.getInstance();





        btn_registrarUsuarioTI = findViewById(R.id.registrarUsuarioTI);
        mAuth = FirebaseAuth.getInstance();





    }

    public void crearUsuarioTI(View view){
        codigoPUCP = findViewById(R.id.input_Admin_codigoPUCPTI);
        correo = findViewById(R.id.inputAdmin_correoRegistroTI);
        nombresApellidos =  findViewById(R.id.inputAdmin_nombreRegistroTI);
        contraseña1 = findViewById(R.id.inputContrasenaRegistro);

        //DatabaseReference usersRef = firebaseDatabase.getReference().child("usuario").child(currentUser.getUid());


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

        if(correo.getText().toString().equalsIgnoreCase("")|| nombresApellidos.getText().toString().equalsIgnoreCase("")
        || contraseña1.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Debe llenar todos los campos correctamente.", Toast.LENGTH_SHORT).show();
        }else {
            datosllenos =true;
        }

        if(datosllenos&& codigo8Digitos){

            mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Usuario usuario = new Usuario();

                        currentUser= FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference usersRef = firebaseDatabase.getReference().child("usuario").child(currentUser.getUid());
                        usuario.setKey(currentUser.getUid());
                        usuario.setNombreApellido(nombresApellidos.getText().toString());
                        usuario.setCorreo(correo.getText().toString());
                        usuario.setRol("Admin TI");
                        usuario.setCodigo(codigoPUCP.getText().toString());
                        usuario.setTipoUsuario("UsuarioTI");

                        usersRef.setValue(usuario).addOnCompleteListener(task1 -> {
                            System.out.println("usuario creado exitosamente");
                            Intent intent = new Intent(Admin_crearUsuarioTI.this, UsuarioTI_MiPerfil.class);

                            startActivity(intent);
                            finish();
                        });
                    }
                }
            });

        }




    }









    private void postUsuarioTI(String codigoPucpTI, String correoTI, String nombresTI, String apellidosTI, String contraseñaTI1, String contraseñaTI2) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombresTI);
        map.put("codigoPUCP", codigoPucpTI);
        map.put("apellido", apellidosTI);
        map.put("correo", correoTI);
        map.put("contraseña1", contraseñaTI1);
        map.put("contraseña2", contraseñaTI2);



//        mFireStore.collection("UsuarioTI").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(getApplicationContext(), "registro exitoso", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}