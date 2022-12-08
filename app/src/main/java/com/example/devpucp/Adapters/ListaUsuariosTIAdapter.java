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

public class ListaUsuariosTIAdapter extends RecyclerView.Adapter<ListaUsuariosTIAdapter.ViewHolder> {
    private ArrayList<Usuario> listaUsuarios;
    private Context context;


    FirebaseDatabase firebaseDatabase;

    public ListaUsuariosTIAdapter(Context contexto, ArrayList<Usuario> dataSet){
        context = contexto;
        listaUsuarios = dataSet;
        for (Usuario u : listaUsuarios){
            Log.d("msg","usuarios"+u.getNombreApellido());
        }
    }

    @NonNull
    @Override
    public ListaUsuariosTIAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("msg","Entra a onCreateViewHolder");
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_lista_usuarios_ti, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaUsuariosTIAdapter.ViewHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);
        holder.usuario = usuario;
        ImageView imageView =holder.imageView;
        TextView nombreUsuario = holder.nombrePersona;
        TextView correoUsuario = holder.correoPersona;
        String nombreUsuarioString =usuario.getNombreApellido();
        String correoUsuarioString = usuario.getCorreo();
        if(usuario.getFotoUrl()!=null){
            String urlImage = usuario.getFotoUrl();
            Glide.with(imageView).load(urlImage).into(imageView);

        }
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
        Button btnDetallesPersona;
        Button btnEliminarPersona;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageViewAdmin_ListausuariosTI) ;
            nombrePersona = (TextView) itemView.findViewById(R.id.textView_Admin_nombreUsuarioTIRV);
            correoPersona =(TextView)  itemView.findViewById(R.id.textView_Admin_apellidoUsuarioTIRV);
            btnDetallesPersona = (Button) itemView.findViewById(R.id.btn_editarUsuarioTI);
            btnEliminarPersona = (Button) itemView.findViewById(R.id.btn_EliminarUsuarioTI);
            btnDetallesPersona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("msg","nombre:"+usuario.getNombreApellido());
                    Intent intent = new Intent(view.getContext(), Admin_editarUsuarioTI.class);
                    intent.putExtra("usuarioDetalle", usuario);
                    itemView.getContext().startActivity(intent);
                }
            });
            btnEliminarPersona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference eventosRef = firebaseDatabase.getReference().child("usuario");
                    eventosRef.child(usuario.getKey()).removeValue().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(view.getContext(), "Usuario eliminado con éxito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), Admin_UsuariosTI.class);
                            itemView.getContext().startActivity(intent);
                        }else{
                            Toast.makeText(view.getContext(), "Ha ocurrido un error al eliminar el evento", Toast.LENGTH_SHORT).show();
                        }


                    });



                }
            });

        }
    }
}
