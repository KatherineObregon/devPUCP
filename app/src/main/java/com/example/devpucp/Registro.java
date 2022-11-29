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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    TextInputEditText nombres,apellidos,codigoPUCP,correo,contraseña1,contraseña2;
    TextInputLayout nombres_textInputLayout,apellidos_textInputLayout,codigoPUCP_textInputLayout,correo_textInputLayout,contraseña1_textInputLayout,contraseña2_textInputLayout;
    Button btn_registrarUsuario;
    private FirebaseFirestore mFireStore;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.vista_clara);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mFireStore = FirebaseFirestore.getInstance();

        String[] rolesList = new String[]{"Alumno", "Docente", "Administrativo"};
        ArrayAdapter<String> adapterAreas = new ArrayAdapter<>(this, R.layout.drop_down_item, rolesList);

        AutoCompleteTextView roles= findViewById(R.id.textInputRol);
        roles.setAdapter(adapterAreas);

        roles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(Registro.this, roles.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        codigoPUCP = findViewById(R.id.inputCodigo_Registro);
        correo = findViewById(R.id.inputCorreo_Registro);
        nombres =  findViewById(R.id.inputNombre_Registro);
        apellidos = findViewById(R.id.inputApellido_Registro);
        contraseña1 = findViewById(R.id.inputContraseña_Registro);
        contraseña2 = findViewById(R.id.inputContrasenaRepetir_Registro);

        codigoPUCP_textInputLayout = findViewById(R.id.layoutCodigo_Registro);
        correo_textInputLayout = findViewById(R.id.layoutCorreo_Registro);
        nombres_textInputLayout =  findViewById(R.id.layoutNombre_Registro);
        apellidos_textInputLayout = findViewById(R.id.layoutApellido_Registro);
        contraseña1_textInputLayout = findViewById(R.id.layoutContraseña_Registro);
        contraseña2_textInputLayout = findViewById(R.id.layoutContraseñaRepetir_Registro);

        btn_registrarUsuario= findViewById(R.id.registrarUsuario);

        btn_registrarUsuario.setOnClickListener(new View.OnClickListener() {


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

                    postUsuario(codigoPucpTI,correoTI,nombresTI,apellidosTI ,contraseñaTI1,contraseñaTI2);

                }

            }
        });

    }

    private void postUsuario(String codigoPucpTI, String correoTI, String nombresTI, String apellidosTI, String contraseñaTI1, String contraseñaTI2) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombresTI);
        map.put("codigoPUCP", codigoPucpTI);
        map.put("apellido", apellidosTI);
        map.put("correo", correoTI);
        map.put("contraseña1", contraseñaTI1);
        map.put("contraseña2", contraseñaTI2);


        mFireStore.collection("Cliente").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

}