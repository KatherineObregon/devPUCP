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
import com.example.devpucp.Admin_UsuariosTI;
import com.example.devpucp.Admin_editarUsuarioTI;
import com.example.devpucp.Entities.Usuario;
import com.example.devpucp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.ViewHolder> {
    private ArrayList<Usuario> listaUsuarios;
    private Context context;


    FirebaseDatabase firebaseDatabase;

    public ListaUsuariosAdapter(Context contexto, ArrayList<Usuario> dataSet){
        context = contexto;
        listaUsuarios = dataSet;
        for (Usuario u : listaUsuarios){
            Log.d("msg","usuarios"+u.getNombreApellido());
        }
    }

    @NonNull
    @Override
    public ListaUsuariosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_admin_lista_usuarios, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaUsuariosAdapter.ViewHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);
        holder.usuario = usuario;
        ImageView imageView =holder.imageView;
        TextView nombreUsuario = holder.nombrePersona;
        TextView correoUsuario = holder.correoPersona;
        TextView rolUsuario = holder.rolPersona;
        String  rolUsuarioStr = usuario.getRol();
        String nombreUsuarioString =usuario.getNombreApellido();
        String correoUsuarioString = usuario.getCorreo();
        if(usuario.getFotoUrl()!=null){
            String urlImage = usuario.getFotoUrl();
            Glide.with(imageView).load(urlImage).into(imageView);

        }
        rolUsuario.setText(rolUsuarioStr);
        nombreUsuario.setText(nombreUsuarioString);
        correoUsuario.setText(correoUsuarioString);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Usuario usuario;
        ImageView imageView;
        TextView nombrePersona;
        TextView correoPersona;
        TextView rolPersona;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageViewAdmin_Listausuarios) ;
            nombrePersona = (TextView) itemView.findViewById(R.id.textView_Admin_nombreUsuarioRV);
            correoPersona =(TextView)  itemView.findViewById(R.id.textView_Admin_correoUsuarioRV);
            rolPersona = (TextView) itemView.findViewById(R.id.textView_Admin_rolUsuarioRV);


        }
    }
}
