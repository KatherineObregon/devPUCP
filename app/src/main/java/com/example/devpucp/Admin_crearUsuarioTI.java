package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Admin_crearUsuarioTI extends AppCompatActivity {

    TextInputEditText nombres,apellidos,codigoPUCP,correo,contraseña1,contraseña2;

    TextInputLayout nombres_textInputLayout,apellidos_textInputLayout,codigoPUCP_textInputLayout,correo_textInputLayout,contraseña1_textInputLayout,contraseña2_textInputLayout;

    Button btn_registrarUsuarioTI;

    //private FirebaseStore mFireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_crear_usuario_ti);

        //mFireStore = FirebaseFirestore.getInstance();

        codigoPUCP = findViewById(R.id.input_Admin_codigoPUCPTI);
        correo = findViewById(R.id.inputAdmin_correoRegistroTI);
        nombres =  findViewById(R.id.inputAdmin_nombreRegistroTI);
        apellidos = findViewById(R.id.inputAdmin_apellidoRegistroTI);
        contraseña1 = findViewById(R.id.inputContrasenaRegistro);
        contraseña2 = findViewById(R.id.inputContrasenaRepetirRegistro);

        codigoPUCP_textInputLayout = findViewById(R.id.Admin_codigoPUCPTILayout);
        correo_textInputLayout = findViewById(R.id.Admin_correoRegistroTILayout);
        nombres_textInputLayout =  findViewById(R.id.Admin_nombreRegistroTILayout);
        apellidos_textInputLayout = findViewById(R.id.Admin_apellidoRegistroTILayout);
        contraseña1_textInputLayout = findViewById(R.id.contrasenaRegistroLayout);
        contraseña2_textInputLayout = findViewById(R.id.contrasenaRepetirRegistroLayout);

        btn_registrarUsuarioTI = findViewById(R.id.registrarUsuarioTI);

        btn_registrarUsuarioTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigoPucpTI = codigoPUCP.getText().toString().trim();
                String correoTI = correo.getText().toString().trim();
                String nombresTI  = nombres.getText().toString().trim();
                String apellidosTI = apellidos.getText().toString().trim();
                String contraseñaTI1 = contraseña1.getText().toString().trim();
                String contraseñaTI2 = contraseña2.getText().toString().trim();




                nombres.setError(null);

                if (codigoPucpTI.isEmpty() && correoTI.isEmpty() && nombresTI.isEmpty() && apellidosTI.isEmpty() && contraseñaTI1.isEmpty() && contraseñaTI2.isEmpty()  ){

                    Toast.makeText(getApplicationContext(), "Ingresar datos", Toast.LENGTH_SHORT).show();
                    //nombres.setError("Introducir un nombre");
                    //nombres.requestFocus();
                    nombres_textInputLayout.setHelperText("Introducir un nombre");
                    nombres_textInputLayout.setError("");


                } else {

                    postUsuarioTI(codigoPucpTI,correoTI,nombresTI,apellidosTI ,contraseñaTI1,contraseñaTI2);


                }

            }
        });



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