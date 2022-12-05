package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    TextInputEditText email,contraseña1;
    TextInputLayout email_textInputLayout,contraseña1_textInputLayout;
    Button btn_iniciar_sesion;
    private FirebaseFirestore mFireStore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.fondo_verde);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.inputEmailLogin);
        contraseña1 = findViewById(R.id.inputPasswordLogin);

        email_textInputLayout= findViewById(R.id.inputLoginEmailLayout);
        contraseña1_textInputLayout = findViewById(R.id.inputLoginPasswordLayout);


        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailUser = email.getText().toString().trim();
                String contraseñaUser = contraseña1.getText().toString().trim();

                if (emailUser.isEmpty() && contraseñaUser.isEmpty()){

                    Toast.makeText(Login.this, "ingrese los datos", Toast.LENGTH_SHORT).show();

                }else {
                    loginUser(emailUser,contraseñaUser);
                }


               }
        });





    }

    private void loginUser(String emailUser, String contraseñaUser) {

        mAuth.signInWithEmailAndPassword(emailUser,contraseñaUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    finish();
                    startActivity(new Intent( Login.this,HomeCliente.class));

                    Toast.makeText(Login.this, "Lograste iniciar sesion", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(Login.this, "error al conectar", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void irAregistrarme(View view){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
}
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            startActivity(new Intent(Login.this, HomeCliente.class));
            finish();