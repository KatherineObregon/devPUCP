package com.example.devpucp.Adapters;

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

public class ListaDispositivosDisponiblesClienteAdapter extends RecyclerView.Adapter<ListaDispositivosDisponiblesClienteAdapter.ViewHolder> {

    private ArrayList<Dispositivo> listaDispositivos;
    private Context context;
    FirebaseDatabase firebaseDatabase;

    public ListaDispositivosDisponiblesClienteAdapter(Context contexto, ArrayList<Dispositivo> dataSet){
        context = contexto;
        listaDispositivos = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_dispositivos, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        Button btnReservar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.Cliente_imageViewDisp) ;
            tipoDisp = (TextView) itemView.findViewById(R.id.textViewTipoDisp);
            marcaDisp =(TextView)  itemView.findViewById(R.id.textViewMarcaDisp);
            stockDisp = (TextView) itemView.findViewById(R.id.textViewStockDisp);
            btnDetallesDisp = (Button) itemView.findViewById(R.id.detalleDispCLiente);
            btnReservar =(Button) itemView.findViewById(R.id.button4);
            btnDetallesDisp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), UsuarioTI_editarDispositivo.class);
                    intent.putExtra("dispositivoDetalleCLiente", dispositivo);
                    itemView.getContext().startActivity(intent);


                }
            });
            btnReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("msg", "Dispositivo key "+ dispositivo.getKey());



                }
            });

        }

        public TextView getTextView(){
            return getTextView();
        }
    }
}
