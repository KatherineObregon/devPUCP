package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.devpucp.Entities.Solicitud;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Cliente_solicitudAprobada extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Solicitud solicitud = new Solicitud();


    double latitudPUCP =-12.0690;
    double longitudPUCP=-77.0781;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_solicitud_aprobada);


        Intent intent = getIntent();
        solicitud = (Solicitud) intent.getSerializableExtra("historialSolAprobada");

        TextView lugar = findViewById(R.id.textViewLugarRecojoCliente);
        lugar.setText(solicitud.getLugarRecojo());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        if(solicitud.getLatitud()!=null && solicitud.getLongitud()!=null) {
            // Add a marker in Sydney and move the camera
            LatLng posicion = new LatLng(Double.parseDouble(solicitud.getLatitud()), Double.parseDouble(solicitud.getLongitud()));
            mMap.addMarker(new MarkerOptions()
                    .position(posicion)
                    .title("Punto de recepción"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 17));
        }else{
            LatLng posicion = new LatLng(latitudPUCP, longitudPUCP);
            mMap.addMarker(new MarkerOptions()
                    .position(posicion)
                    .title("Punto de recepcion " ));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 17));
        }



    }
}