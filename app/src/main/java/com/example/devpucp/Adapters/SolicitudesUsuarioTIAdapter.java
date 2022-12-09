package com.example.devpucp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.devpucp.Entities.Solicitud;
import com.example.devpucp.R;
import com.example.devpucp.UsuarioTI_solicitudReservaDetalles;

import java.util.ArrayList;

public class SolicitudesUsuarioTIAdapter extends RecyclerView.Adapter<SolicitudesUsuarioTIAdapter.ViewHolder> {

    private ArrayList<Solicitud> listaSolicitudes;
    private Context context;


    public SolicitudesUsuarioTIAdapter(Context contexto, ArrayList<Solicitud> dataSet){
        context = contexto;
        listaSolicitudes = dataSet;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_usuarioti_solicitudes_dispositivos, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudesUsuarioTIAdapter.ViewHolder holder, int position) {
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
        Button btnDetallesSol;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.UsuarioTI_imageViewDispSolicitudes) ;
            idDIsp = (TextView) itemView.findViewById(R.id.textView31);
            tipoDisp = (TextView) itemView.findViewById(R.id.UsuarioTI_textViewTipoDispSolicitud);
            marcaDisp =(TextView)  itemView.findViewById(R.id.UsuarioTI_textViewMarcaDispSolicitudes);
            estadoDisp = (TextView) itemView.findViewById(R.id.UsuarioTI_textViewFechaDispSolicitudes);
            btnDetallesSol = (Button) itemView.findViewById(R.id.detallesSolicitudTI);
            btnDetallesSol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), UsuarioTI_solicitudReservaDetalles.class);
                    intent.putExtra("solicitudReservaTI", solicitud);
                    itemView.getContext().startActivity(intent);
                }
            });



        }

        public TextView getTextView(){
            return getTextView();
        }
    }
}
