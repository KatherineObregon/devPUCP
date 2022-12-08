package com.example.devpucp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    public void actualizarDispositivo(View view){

        String marcaNuevaStr = marca.getText().toString();
        String caracteristicasNuevasStr = caracteristicas.getText().toString();
        String accesoriosNuevoStr = accesorios.getText().toString();
        String stockNuevoStr = stock.getText().toString();
        String tipoNuevo = tipoTV.getText().toString();

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