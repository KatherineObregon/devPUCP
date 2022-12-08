package com.example.devpucp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Dispositivo;
import com.example.devpucp.R;
import com.example.devpucp.UsuarioTI_editarDispositivo;
import com.example.devpucp.UsuarioTI_gestionarDispositivos;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListaDispositivosUsuarioTIAdapter extends RecyclerView.Adapter<ListaDispositivosUsuarioTIAdapter.ViewHolder> {

    private ArrayList<Dispositivo> listaDispositivos;
    private Context context;
    FirebaseDatabase firebaseDatabase;

    public ListaDispositivosUsuarioTIAdapter(Context contexto, ArrayList<Dispositivo> dataSet){
        context = contexto;
        listaDispositivos = dataSet;
    }


    @NonNull
    @Override
    public ListaDispositivosUsuarioTIAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_usuarioti_dispositivos, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaDispositivosUsuarioTIAdapter.ViewHolder holder, int position) {
        Dispositivo dispositivo = listaDispositivos.get(position);
        holder.dispositivo=dispositivo;
        ImageView imageView = holder.imageView;
        TextView tipoDev = holder.tipoDisp;
        TextView marcaDev =holder.marcaDisp;
        TextView stockDev = holder.stockDisp;

        String tipoDevStr = dispositivo.getTipo();
        String marcaDevStr = dispositivo.getMarca();
        String stockDevStr = dispositivo.getStock();

        if(dispositivo.getFotoUrl()!=null){
            String urlImage = dispositivo.getFotoUrl();
            Glide.with(imageView).load(urlImage).into(imageView);

        }

        tipoDev.setText(tipoDevStr);
        marcaDev.setText(marcaDevStr);
        stockDev.setText(stockDevStr);
    }

    @Override
    public int getItemCount() {
        return listaDispositivos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Dispositivo dispositivo;
        ImageView imageView;
        TextView tipoDisp;
        TextView marcaDisp;
        TextView stockDisp;
        Button btnDetallesDisp;
        Button btnEliminarDisp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.UsuarioTI_imageViewDispReserva) ;
            tipoDisp = (TextView) itemView.findViewById(R.id.UsuarioTI_textViewTipoDispGestion);
            marcaDisp =(TextView)  itemView.findViewById(R.id.UsuarioTI_textViewMarcaDispGestion);
            stockDisp = (TextView) itemView.findViewById(R.id.UsuarioTI_textViewStockDispGestion);
            btnDetallesDisp = (Button) itemView.findViewById(R.id.detallesDispositivo);
            btnEliminarDisp =(Button) itemView.findViewById(R.id.button12);
            btnDetallesDisp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), UsuarioTI_editarDispositivo.class);
                    intent.putExtra("dispositivoDetalleTI", dispositivo);
                    itemView.getContext().startActivity(intent);


                }
            });
            btnEliminarDisp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference dispRef = firebaseDatabase.getReference().child("dispositivos");
                    dispRef.child(dispositivo.getKey()).removeValue().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(view.getContext(), "Dispositivo eliminado con éxito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), UsuarioTI_gestionarDispositivos.class);
                            itemView.getContext().startActivity(intent);
                        }else{
                            Toast.makeText(view.getContext(), "Ha ocurrido un error al eliminar el evento", Toast.LENGTH_SHORT).show();
                        }


                    });



                }
            });

        }

        public TextView getTextView(){
            return getTextView();
        }
    }
}
