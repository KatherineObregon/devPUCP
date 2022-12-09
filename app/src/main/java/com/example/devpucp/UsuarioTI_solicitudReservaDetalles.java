package com.example.devpucp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Dispositivo;
import com.example.devpucp.Entities.Solicitud;
import com.example.devpucp.Entities.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class UsuarioTI_solicitudReservaDetalles extends AppCompatActivity {


    StorageReference storageRef;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersRef2;
    DatabaseReference dispRef;
    DatabaseReference solicRef;


    Solicitud solicitud = new Solicitud();
    Usuario usuarioReserva = new Usuario();
    Dispositivo dispositivoReserva = new Dispositivo();

    ImageView imageView1;
    ImageView imageView2;

    TextView id;
    TextView motivo;
    TextView curso;
    TextView programas;
    TextView otros;
    TextView estado;

    TextView nombreCliente;
    TextView codigoCLiente;
    TextView correoCliente;
    TextView rolClienteM;

    TextView tipoDisp;
    TextView marcaDisp;
    TextView caracteristicasDisp;
    TextView accesoriosDisp;
    TextView tiempoReserva;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_ti_solicitud_reserva_detalles);
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        solicitud = (Solicitud) intent.getSerializableExtra("solicitudReservaTI");

        usersRef2= firebaseDatabase.getReference().child("usuario");
        usersRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    usuarioReserva = snapshot1.getValue(Usuario.class);

                    String keyUsuario = usuarioReserva.getKey();
                    String personaUserID= solicitud.getPersonaKey();
                    if(keyUsuario.equalsIgnoreCase(personaUserID)) {
                        break;
                    }

                }
                Log.d("msg", "DESDE USER TI USUARIO: "+usuarioReserva.getNombreApellido());
                nombreCliente.setText(usuarioReserva.getNombreApellido());
                codigoCLiente.setText(usuarioReserva.getCodigo());
                correoCliente.setText(usuarioReserva.getCorreo());
                rolClienteM.setText(usuarioReserva.getRol());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        dispRef= firebaseDatabase.getReference().child("dispositivos");
        dispRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    dispositivoReserva = snapshot1.getValue(Dispositivo.class);

                    String keyDispositivo = dispositivoReserva.getKey();
                    String solDispID= solicitud.getDispositivoKey();
                    if(keyDispositivo.equalsIgnoreCase(solDispID)) {
                        break;
                    }

                }

                Log.d("msg", "DESDE USER TI DISP: "+dispositivoReserva.getKey());
                tipoDisp.setText(dispositivoReserva.getTipo());
                marcaDisp.setText(dispositivoReserva.getMarca());
                caracteristicasDisp.setText(dispositivoReserva.getCaracteristicas());
                accesoriosDisp.setText(dispositivoReserva.getAccesorios());
                tiempoReserva.setText(solicitud.getTiempoReserva());

                if(dispositivoReserva.getFotoUrl()!=null){
                    Glide.with(UsuarioTI_solicitudReservaDetalles.this).load(dispositivoReserva.getFotoUrl()).into(imageView1);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });



        imageView1 =findViewById(R.id.imageView9);
        imageView2 = findViewById(R.id.imageView_UsuarioTISoliDNI);


        id = findViewById(R.id.textView32);
        motivo= findViewById(R.id.textView_UsuarioTIDetalleSoliMotivo);
        curso= findViewById(R.id.textView_UsuarioTIDetalleSoliCurso);
        programas= findViewById(R.id.textView_UsuarioTIDetalleSoliProgramas);
        otros= findViewById(R.id.textView_UsuarioTIDetalleSoliOtros);
        estado= findViewById(R.id.textView_UsuarioTIDetalleSoliEstados);

        nombreCliente= findViewById(R.id.textView_UsuarioTIDetalleSoliNombre);
        codigoCLiente= findViewById(R.id.textView_UsuarioTIDetalleSoliCodigo);
        correoCliente= findViewById(R.id.textView_UsuarioTIDetalleSoliCorreo);
        rolClienteM= findViewById(R.id.textView_UsuarioTIDetalleSoliRol);

        tipoDisp= findViewById(R.id.textView_UsuarioTIDetalleSoliTipo);
        marcaDisp= findViewById(R.id.textView_UsuarioTIDetalleSoliMarca);
        caracteristicasDisp= findViewById(R.id.textView_UsuarioTIDetalleSoliCaracteristicas);
        accesoriosDisp= findViewById(R.id.textView_UsuarioTIDetalleSoliAccesorios);
        tiempoReserva= findViewById(R.id.textView_UsuarioTIDetalleSoliFecha);

        id.setText(solicitud.getKey());
        motivo.setText(solicitud.getMotivo());
        curso.setText(solicitud.getCurso());
        programas.setText(solicitud.getProgramas());
        otros.setText(solicitud.getOtros());
        estado.setText(solicitud.getEstado());





        if(solicitud.getFotoUrl()!=null){
            Glide.with(UsuarioTI_solicitudReservaDetalles.this).load(solicitud.getFotoUrl()).into(imageView2);

        }




    }







    public void rechazarSolicitud(View view){
        Intent intent = new Intent(UsuarioTI_solicitudReservaDetalles.this, UsuarioTI_solicitudRechazo.class);
        startActivity(intent);
    }
}