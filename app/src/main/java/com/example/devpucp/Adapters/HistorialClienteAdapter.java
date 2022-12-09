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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devpucp.Cliente_solicitudAprobada;
import com.example.devpucp.Cliente_solicitudRechazada;
import com.example.devpucp.Entities.Solicitud;
import com.example.devpucp.R;
import com.example.devpucp.UsuarioTI_solicitudReservaDetalles;

import java.util.ArrayList;

public class HistorialClienteAdapter extends RecyclerView.Adapter<HistorialClienteAdapter.ViewHolder> {

    private ArrayList<Solicitud> listaSolicitudes;
    private Context context;


    public HistorialClienteAdapter(Context contexto, ArrayList<Solicitud> dataSet){
        context = contexto;
        listaSolicitudes = dataSet;
    }

    @NonNull
    @Override
    public HistorialClienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_historialcliente, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialClienteAdapter.ViewHolder holder, int position) {
        Solicitud solicitud = listaSolicitudes.get(position);
        holder.solicitud=solicitud;
        ImageView imageView = holder.imageView;
        TextView idDev = holder.idDIsp;
        TextView tipoDev = holder.tipoDisp;
        TextView marcaDev =holder.marcaDisp;
        TextView estadoDev = holder.estadoDisp;

        String tipoDevStr = solicitud.getTipoDisp();
        String marcaDevStr = solicitud.getMarcaDisp();
        String estadoDevStr = solicitud.getEstado();
        String idDevStr = solicitud.getKey();

        if(solicitud.getFotoDispUrl()!=null){
            String urlImage = solicitud.getFotoDispUrl();
            Glide.with(imageView).load(urlImage).into(imageView);

        }

        tipoDev.setText(tipoDevStr);
        marcaDev.setText(marcaDevStr);
        idDev.setText(idDevStr);
        estadoDev.setText(estadoDevStr);
    }

    @Override
    public int getItemCount() {
        return listaSolicitudes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Solicitud solicitud;
        ImageView imageView;
        TextView idDIsp;
        TextView tipoDisp;
        TextView marcaDisp;
        TextView estadoDisp;
        Button btnDetalles;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageDispPrestamo) ;
            idDIsp = (TextView) itemView.findViewById(R.id.textView29);
            tipoDisp = (TextView) itemView.findViewById(R.id.textViewTipoDispPrest);
            marcaDisp =(TextView)  itemView.findViewById(R.id.textViewMarcaDispPrest);
            estadoDisp = (TextView) itemView.findViewById(R.id.textViewEstadokDispPrest);
            btnDetalles =(Button) itemView.findViewById(R.id.btn_detallesHistorial);
            btnDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("msg", "Detalle: "+ solicitud.getKey());
                    if(solicitud.getEstado().equalsIgnoreCase("Aprobado")){
                        Intent intent = new Intent(view.getContext(), Cliente_solicitudAprobada.class);
                        intent.putExtra("historialSolAprobada", solicitud);
                        itemView.getContext().startActivity(intent);
                    }else if(solicitud.getEstado().equalsIgnoreCase("Rechazado")){
                        Intent intent = new Intent(view.getContext(), Cliente_solicitudRechazada.class);
                        intent.putExtra("historialSolRechazada", solicitud);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });


        }

        public TextView getTextView(){
            return getTextView();
        }
    }
}
