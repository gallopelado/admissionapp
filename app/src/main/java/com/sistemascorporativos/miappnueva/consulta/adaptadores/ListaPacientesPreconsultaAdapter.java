package com.sistemascorporativos.miappnueva.consulta.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacientePreconsultaDetalle;
import com.sistemascorporativos.miappnueva.consulta.actividades.FormularioConsultaActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaPacientesPreconsultaAdapter extends RecyclerView.Adapter<ListaPacientesPreconsultaAdapter.PacienteDetalleViewHolder> {

    ArrayList<PacientePreconsultaDetalle> listaPacientes;
    ArrayList<PacientePreconsultaDetalle> listaOriginalPacientes;
    private SharedPreferences sharedPref;

    public ListaPacientesPreconsultaAdapter(ArrayList<PacientePreconsultaDetalle> listaPacientes) {
        this.listaPacientes = listaPacientes;
        listaOriginalPacientes = new ArrayList<>();
        listaOriginalPacientes.addAll(listaPacientes);
    }

    @NonNull
    @Override
    public ListaPacientesPreconsultaAdapter.PacienteDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_paciente_preconsulta, null, false);
        return new PacienteDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPacientesPreconsultaAdapter.PacienteDetalleViewHolder holder, int position) {
        holder.viewNombrePaciente.setText(listaPacientes.get(position).getNombrePaciente());
        holder.viewCedula.setText(listaPacientes.get(position).getCedulaPaciente());
    }

    public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaPacientes.clear();
            listaPacientes.addAll(listaOriginalPacientes);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<PacientePreconsultaDetalle> lista_filtrada = listaPacientes.stream()
                        .filter(e ->
                                (e.getCedulaPaciente().contains(txtBuscar) || e.getNombrePaciente().toLowerCase().contains(txtBuscar.toLowerCase())))
                        .collect(Collectors.toList());
                listaPacientes.clear();
                listaPacientes.addAll(lista_filtrada);
            } else {
                listaPacientes.clear();
                for(PacientePreconsultaDetalle item: listaPacientes) {
                    if(item.getCedulaPaciente().contains(txtBuscar) || item.getNombrePaciente().toLowerCase().contains(txtBuscar.toLowerCase())) {
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

        TextView viewNombrePaciente, viewCedula;

        public PacienteDetalleViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombrePaciente = itemView.findViewById(R.id.tvNombrePaciente);
            viewCedula = itemView.findViewById(R.id.tvCedula);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    sharedPref = context.getSharedPreferences("lista_paciente", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("cedula", listaPacientes.get(getAbsoluteAdapterPosition()).getCedulaPaciente());
                    editor.putString("nombres", listaPacientes.get(getAbsoluteAdapterPosition()).getNombrePaciente());
                    editor.putString("apellidos", listaPacientes.get(getAbsoluteAdapterPosition()).getApellidoPaciente());
                    editor.putString("tipodoc", listaPacientes.get(getAbsoluteAdapterPosition()).getTipodocPaciente());
                    editor.putString("telefono", listaPacientes.get(getAbsoluteAdapterPosition()).getTelefonoPaciente());
                    editor.putString("estado_civil", listaPacientes.get(getAbsoluteAdapterPosition()).getEstadoPaciente());
                    editor.putString("codigo_preconsulta", listaPacientes.get(getAbsoluteAdapterPosition()).getCodigoPreconsulta());
                    editor.putString("codigo_asignacion", listaPacientes.get(getAbsoluteAdapterPosition()).getCodigoAsignacion());
                    editor.putString("operacion", "agregar");
                    editor.commit();
                    // Ir al formulario
                    context.startActivity(new Intent(context, FormularioConsultaActivity.class));
                }
            });
        }
    }

//    public class PacienteDetalleViewHolder extends RecyclerView.ViewHolder {
//
//        TextView viewNombrePaciente, viewCedula;
//
//        public PacienteDetalleViewHolder(@NonNull View itemView) {
//            super(itemView);
//            viewNombrePaciente = itemView.findViewById(R.id.tvNombrePaciente);
//            viewCedula = itemView.findViewById(R.id.tvCedula);
//        }
//    }

}
