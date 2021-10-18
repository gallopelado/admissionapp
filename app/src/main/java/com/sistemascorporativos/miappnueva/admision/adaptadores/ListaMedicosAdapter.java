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

public class ListaMedicosAdapter extends RecyclerView.Adapter<ListaMedicosAdapter.MedicoViewHolder> {

    ArrayList<Profesional> listaMedicos;

    public ListaMedicosAdapter(ArrayList<Profesional> listaMedicos) {
        this.listaMedicos = listaMedicos;
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
