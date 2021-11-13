package com.sistemascorporativos.miappnueva.seguridad.usuario.adaptadores;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UsuarioViewHolder> {

    private ArrayList<LoginDto> listaUsuarioItems;
    private ArrayList<LoginDto> listaUsuarioOrinalItems;
    private SharedPreferences sharedPref, sharedPref_menu;

    public ListaUsuariosAdapter(ArrayList<LoginDto> listaUsuarioItems) {
        this.listaUsuarioItems = listaUsuarioItems;
        this.listaUsuarioOrinalItems = new ArrayList<>();
        this.listaUsuarioOrinalItems.addAll(listaUsuarioItems);
    }

    @NonNull
    @Override
    public ListaUsuariosAdapter.UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_usuarios, null, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaUsuariosAdapter.UsuarioViewHolder holder, int position) {
        holder.viewIdUsuario.setText(listaUsuarioItems.get(position).getUsuCodigoUsuario());
        String descripcion = listaUsuarioItems.get(position).getUsuNombres() + " " + listaUsuarioItems.get(position).getUsuApellidos();
        holder.viewDescripcionUsuario.setText(descripcion);
    }

    public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaUsuarioItems.clear();
            listaUsuarioItems.addAll(listaUsuarioOrinalItems);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<LoginDto> lista_filtrada = listaUsuarioItems.stream()
                        .filter(e ->
                                (e.getUsuCodigoUsuario().contains(txtBuscar) || e.getUsuNombres().toLowerCase().contains(txtBuscar.toLowerCase()) || e.getUsuApellidos().toLowerCase().contains(txtBuscar.toLowerCase())))
                        .collect(Collectors.toList());
                listaUsuarioItems.clear();
                listaUsuarioItems.addAll(lista_filtrada);
            } else {
                listaUsuarioItems.clear();
                for(LoginDto e: listaUsuarioItems) {
                    if(e.getUsuCodigoUsuario().contains(txtBuscar) || e.getUsuNombres().toLowerCase().contains(txtBuscar.toLowerCase()) || e.getUsuApellidos().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaUsuarioItems.add(e);
                    }
                }
            }
        }
        // Notificar los cambios
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return listaUsuarioItems.size(); }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView viewIdUsuario, viewDescripcionUsuario;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            viewIdUsuario = itemView.findViewById(R.id.tvIdUsuario);
            viewDescripcionUsuario = itemView.findViewById(R.id.tvDescripcionUsuario);
        }
    }
}
