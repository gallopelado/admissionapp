package com.sistemascorporativos.miappnueva.admision.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaMedicosAdapter extends RecyclerView.Adapter<ListaMedicosAdapter.MedicoViewHolder> {

    ArrayList<Profesional> listaMedicos;
    ArrayList<Profesional> listaOriginalMedicos;

    public ListaMedicosAdapter(ArrayList<Profesional> listaMedicos) {
        this.listaMedicos = listaMedicos;
        listaOriginalMedicos = new ArrayList<>();
        listaOriginalMedicos.addAll(listaMedicos);
    }

    @NonNull
    @Override
    public MedicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_medico, null, false);
        return new MedicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicoViewHolder holder, int position) {
        String medico = "Dr. "+listaMedicos.get(position).getUsuario().getNombreUsuario()+" "+listaMedicos.get(position).getUsuario().getApellidoUsuario();
        holder.viewNombreMedico.setText(medico);
        holder.viewEspecialidadMedico.setText(listaMedicos.get(position).getEspecialidad().getEspecialidadDescripcion());
    }

    public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaMedicos.clear();
            listaMedicos.addAll(listaOriginalMedicos);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Profesional> lista_filtrada = listaMedicos.stream()
                        .filter(e -> (e.getUsuario().getNombreUsuario()+" "+e.getUsuario().getApellidoUsuario()).toLowerCase().contains(txtBuscar.toLowerCase()) || e.getEspecialidad().getEspecialidadDescripcion().toLowerCase().contains(txtBuscar.toLowerCase()) )
                        .collect(Collectors.toList());
                listaMedicos.clear();
                listaMedicos.addAll(lista_filtrada);
            } else {
                listaMedicos.clear();
                for(Profesional item: listaOriginalMedicos) {
                    if((item.getUsuario().getNombreUsuario()+" "+item.getUsuario().getApellidoUsuario()).toLowerCase().contains(txtBuscar.toLowerCase()) || item.getEspecialidad().getEspecialidadDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaMedicos.add(item);
                    }
                }
            }
        }
        // Notificar los cambios
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaMedicos.size();
    }

    public class MedicoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombreMedico, viewEspecialidadMedico;

        public MedicoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombreMedico = itemView.findViewById(R.id.tvNombreMedico);
            viewEspecialidadMedico = itemView.findViewById(R.id.tvEspecialidadMedico);
        }
    }
}
