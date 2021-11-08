package com.sistemascorporativos.miappnueva.referenciales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAdmitidoDetalle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaReferencialesAdapter extends RecyclerView.Adapter<ListaReferencialesAdapter.ReferencialViewHolder> {

    private ArrayList<ReferencialDto> listaItems;
    private ArrayList<ReferencialDto> listaOriginalItems;

    public ListaReferencialesAdapter(ArrayList<ReferencialDto> listaItems) {
        this.listaItems = listaItems;
        listaOriginalItems = new ArrayList<>();
        listaOriginalItems.addAll(listaItems);
    }

    @NonNull
    @Override
    public ListaReferencialesAdapter.ReferencialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_referencial, null, false);
        return new ReferencialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaReferencialesAdapter.ReferencialViewHolder holder, int position) {
        holder.vieId.setText(listaItems.get(position).getId());
        holder.vieDescripcion.setText(listaItems.get(position).getDescripcion());
    }

    public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaItems.clear();
            listaItems.addAll(listaOriginalItems);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ReferencialDto> lista_filtrada = listaItems.stream()
                        .filter(e ->
                                (e.getId().contains(txtBuscar) || e.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())))
                        .collect(Collectors.toList());
                listaItems.clear();
                listaItems.addAll(lista_filtrada);
            } else {
                listaItems.clear();
                for(ReferencialDto e: listaItems) {
                    if(e.getId().contains(txtBuscar) || e.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaItems.add(e);
                    }
                }
            }
        }
        // Notificar los cambios
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ReferencialViewHolder extends RecyclerView.ViewHolder {

        TextView vieId, vieDescripcion;

        public ReferencialViewHolder(@NonNull View itemView) {
            super(itemView);
            vieId = itemView.findViewById(R.id.tvIdReferencial);
            vieDescripcion = itemView.findViewById(R.id.tvDescripcionReferencial);
        }
    }
}
