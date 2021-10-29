package com.sistemascorporativos.miappnueva.admision.adaptadores;

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

public class ListaPacientesAdmitidosAdapter extends RecyclerView.Adapter<ListaPacientesAdmitidosAdapter.PacienteDetalleViewHolder> {

    ArrayList<PacienteAdmitidoDetalle> listaPacientes;
    ArrayList<PacienteAdmitidoDetalle> listaOriginalPacientes;

    public ListaPacientesAdmitidosAdapter(ArrayList<PacienteAdmitidoDetalle> listaPacientes) {
        this.listaPacientes = listaPacientes;
        listaOriginalPacientes = new ArrayList<>();
        listaOriginalPacientes.addAll(listaPacientes);
    }

    @NonNull
    @Override
    public ListaPacientesAdmitidosAdapter.PacienteDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_paciente_admitido, null, false);
        return new PacienteDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPacientesAdmitidosAdapter.PacienteDetalleViewHolder holder, int position) {
        holder.viewNombrePaciente.setText(listaPacientes.get(position).getNombrePaciente());
        holder.viewCedula.setText(listaPacientes.get(position).getCedulaPaciente());
        holder.viewConsultado.setText(listaPacientes.get(position).getConsultando());
    }

    public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaPacientes.clear();
            listaPacientes.addAll(listaOriginalPacientes);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<PacienteAdmitidoDetalle> lista_filtrada = listaPacientes.stream()
                        .filter(e ->
                                (e.getCedulaPaciente().contains(txtBuscar) || e.getNombrePaciente().toLowerCase().contains(txtBuscar.toLowerCase()) || e.getConsultando().toLowerCase().contains(txtBuscar.toLowerCase())))
                        .collect(Collectors.toList());
                listaPacientes.clear();
                listaPacientes.addAll(lista_filtrada);
            } else {
                listaPacientes.clear();
                for(PacienteAdmitidoDetalle item: listaPacientes) {
                    if(item.getCedulaPaciente().contains(txtBuscar) || item.getNombrePaciente().toLowerCase().contains(txtBuscar.toLowerCase()) || item.getConsultando().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaPacientes.add(item);
                    }
                }
            }
        }
        // Notificar los cambios
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaPacientes.size();
    }

    public class PacienteDetalleViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombrePaciente, viewCedula, viewConsultado;

        public PacienteDetalleViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombrePaciente = itemView.findViewById(R.id.tvNombrePaciente);
            viewCedula = itemView.findViewById(R.id.tvCedula);
            viewConsultado = itemView.findViewById(R.id.tvConsultando);
        }
    }
}
