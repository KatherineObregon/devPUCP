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
import com.example.devpucp.Adapters.ListaCantidadTotaldeEquiposPrestadosMarcaAdapter.ViewHolder;
import com.example.devpucp.Cliente_dispositivosDetalles2;
import com.example.devpucp.Entities.Dispositivo;
import com.example.devpucp.Entities.Usuario;
import com.example.devpucp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListaCantidadTotaldeEquiposPrestadosMarcaAdapter extends RecyclerView.Adapter<ListaCantidadTotaldeEquiposPrestadosMarcaAdapter.ViewHolder>{



    private ArrayList<Dispositivo> listaDispositivos;
    private Context context;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference  usersRef2;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    Usuario usuarioActual = new Usuario();

    public ListaCantidadTotaldeEquiposPrestadosMarcaAdapter(Context contexto, ArrayList<Dispositivo> dataSet){
        context = contexto;
        listaDispositivos = dataSet;
    }

    @NonNull
    @Override
    public ListaCantidadTotaldeEquiposPrestadosMarcaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_admin_dispositivos_prestados_, parent,false);
        return new ListaCantidadTotaldeEquiposPrestadosMarcaAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaCantidadTotaldeEquiposPrestadosMarcaAdapter.ViewHolder holder, int position) {
        Dispositivo dispositivo = listaDispositivos.get(position);
        holder.dispositivo=dispositivo;
        ImageView imageView = holder.imageView;
        TextView tipoDev = holder.tipoDisp;
        TextView marcaDev =holder.marcaDisp;
        TextView stockDev = holder.stockDisp;

        String tipoDevStr = dispositivo.getTipo();
        String marcaDevStr = dispositivo.getMarca();
        String stockDevStr = dispositivo.getStockPrestados();

        /*try
        {

        }
        catch(NullPointerException e)
      {
        System.out.print("NullPointerException caught");
        }*/
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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageView6marca) ;
            tipoDisp = (TextView) itemView.findViewById(R.id.textViewTipoDispPrestadoRVmarca);
            marcaDisp =(TextView)  itemView.findViewById(R.id.textViewMarcaDispPrestadoRVmarca);
            stockDisp = (TextView) itemView.findViewById(R.id.textViewStockDispPrestadoRVmarca);

        }

        public TextView getTextView(){
            return getTextView();
        }
    }

}
