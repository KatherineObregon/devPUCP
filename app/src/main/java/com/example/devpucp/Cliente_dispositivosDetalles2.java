package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Dispositivo;
import com.example.devpucp.Entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class Cliente_dispositivosDetalles2 extends AppCompatActivity {

    Dispositivo dispositivo = new Dispositivo();
    ImageView imageView;


    TextView tipo;
    TextView marca;
    TextView caracteristicas;
    TextView accesorios;
    TextView stock;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersRef2;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    Usuario usuarioActual = new Usuario();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_dispositivos_detalles2);

        firebaseDatabase =FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        tipo= findViewById(R.id.textView_Cliente_detalleTipoDisp);
        marca = findViewById(R.id.textView_Cliente_detalleMarcaDisp);
        caracteristicas = findViewById(R.id.textView_Cliente_detalleCaracteristicaDisp);
        accesorios = findViewById(R.id.textView_Cliente_detalleAccesoriosDisp);
        stock = findViewById(R.id.textView_Cliente_detalleStockDisp);
        dispositivo = (Dispositivo) intent.getSerializableExtra("dispositivosDetalleCLiente");
        imageView = findViewById(R.id.imageView10);
        if(dispositivo.getFotoUrl()!=null){
            Glide.with(Cliente_dispositivosDetalles2.this).load(dispositivo.getFotoUrl()).into(imageView);
        }
        tipo.setText(dispositivo.getTipo());
        marca.setText(dispositivo.getMarca());
        caracteristicas.setText(dispositivo.getCaracteristicas());
        accesorios.setText(dispositivo.getAccesorios());
        stock.setText(dispositivo.getStock());


    }

    public void irReservarDetalle(View view){


        Intent intent2 = new Intent(view.getContext(), Cliente_Reservar.class);
        intent2.putExtra("dispositivoReserva", dispositivo);
        startActivity(intent2);

    }
}