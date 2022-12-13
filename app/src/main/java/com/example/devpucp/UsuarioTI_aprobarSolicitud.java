package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devpucp.Entities.Dispositivo;
import com.example.devpucp.Entities.Solicitud;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioTI_aprobarSolicitud extends AppCompatActivity implements OnMapReadyCallback {

    FirebaseDatabase firebaseDatabase;

    double latitudRecepcion =-12.0690;
    double longitudRecepcion=-77.0781;

    EditText lugarRecepcion;
    Solicitud solicitud = new Solicitud();
    Dispositivo dispositivo = new Dispositivo();

    private GoogleMap mMap;

    DatabaseReference solicitudesRef;

    DatabaseReference dispRef;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_aprobar_solicitud);
        firebaseDatabase = FirebaseDatabase.getInstance();

        lugarRecepcion = findViewById(R.id.editTextLugarRecepcion);

        Intent intent = getIntent();
        solicitud = (Solicitud) intent.getSerializableExtra("solicitudAprobada");
        dispositivo = (Dispositivo) intent.getSerializableExtra("dispositivoAprobado");

        dispRef= firebaseDatabase.getReference().child("dispositivos").child(dispositivo.getKey());
        solicitudesRef = firebaseDatabase.getReference().child("solicitudes").child(solicitud.getKey());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng posicion = new LatLng(latitudRecepcion, longitudRecepcion);
        mMap.addMarker(new MarkerOptions()
                .position(posicion)
                .title("PUCP"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion,17));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" : "+latLng.longitude);

                latitudRecepcion = latLng.latitude;
                longitudRecepcion = latLng.longitude;
                mMap.clear();

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.addMarker(markerOptions);

                Log.d("msg", "latitud:"+latitudRecepcion);
                Log.d("msg", "longitud"+longitudRecepcion);

            }
        });
    }

    public void aprobarSolicitud(View view){


        boolean datolleno=false;

        if(lugarRecepcion.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Debe indicar el nombre del lugar de recepcion", Toast.LENGTH_SHORT).show();
        }else {
            datolleno = true;
        }
        if(datolleno) {
            solicitud.setLatitud(String.valueOf(latitudRecepcion));
            solicitud.setLongitud(String.valueOf(longitudRecepcion));
            solicitud.setLugarRecojo(lugarRecepcion.getText().toString());
            solicitud.setEstado("Aprobado");

            int stockActual = Integer.parseInt(dispositivo.getStock());
            int nuevoStock = stockActual-1;
            dispositivo.setStock(String.valueOf(nuevoStock));
            int stockActualprestados = Integer.parseInt(dispositivo.getStockPrestados());
            int nuevoStockprestados = stockActualprestados+1;
            dispositivo.setStockPrestados(String.valueOf(nuevoStockprestados));
            dispRef.setValue(dispositivo);
            solicitudesRef.setValue(solicitud).addOnCompleteListener(task -> {
                Toast.makeText(this, "Se aprobó la solicitud de préstamo exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(UsuarioTI_aprobarSolicitud.this,UsuarioTI_solicitudesReserva.class);
                startActivity(intent);
            });
        }

    }
}