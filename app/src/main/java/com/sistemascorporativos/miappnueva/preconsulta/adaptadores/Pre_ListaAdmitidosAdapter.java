package com.sistemascorporativos.miappnueva.preconsulta.adaptadores;

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
import com.sistemascorporativos.miappnueva.preconsulta.actividades.Pre_FormPreconsultaActivity;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.Pre_PacienteAdmitido;

import java.util.ArrayList;

//x
public class Pre_ListaAdmitidosAdapter extends RecyclerView.Adapter<Pre_ListaAdmitidosAdapter.Pre_PacienteDetalleViewHolder>{

    private ArrayList<Pre_PacienteAdmitido> listaPacientes;
    private ArrayList<Pre_PacienteAdmitido> listaOriginalPacientes;
    private SharedPreferences sharedPref;

    public Pre_ListaAdmitidosAdapter(ArrayList<Pre_PacienteAdmitido> listaPacientes) {
        this.listaPacientes = listaPacientes;
        listaOriginalPacientes = new ArrayList<>();
        listaOriginalPacientes.addAll(listaPacientes);
    }

    @NonNull
    @Override
    public Pre_PacienteDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pre_seleccionar_admitido, null, false);
        return new Pre_PacienteDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pre_PacienteDetalleViewHolder holder, int position) {
        holder.viewNombrePaciente.setText(listaPacientes.get(position).getNombrePaciente());
        holder.viewCedula.setText(listaPacientes.get(position).getCedulaPaciente());
        holder.viewConsultado.setText(listaPacientes.get(position).getConsultando());
    }

//    public void filtrado(String txtBuscar) {
//        Integer longitud = txtBuscar.length();
//        if(longitud == 0) {
//            listaPacientes2.clear();
//            listaPacientes2.addAll(listaOriginalPacientes2);
//        } else {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                List<Pre_PacienteAdmitido> lista_filtrada = listaPacientes2.stream()
//                        .filter(e ->
//                                (e.getCedulaPaciente2().contains(txtBuscar) || e.getNombrePaciente().toLowerCase().contains(txtBuscar.toLowerCase()) || e.getConsultando().toLowerCase().contains(txtBuscar.toLowerCase())))
//                        .collect(Collectors.toList());
//                listaPacientes2.clear();
//                listaPacientes2.addAll(lista_filtrada);
//            } else {
//                listaPacientes2.clear();
//                for(Pre_PacienteAdmitido item: listaPacientes2) {
//                    if(item.getCedulaPaciente().contains(txtBuscar) || item.getNombrePaciente().toLowerCase().contains(txtBuscar.toLowerCase()) || item.getConsultando().toLowerCase().contains(txtBuscar.toLowerCase())) {
//                        listaPacientes2.add(item);
//                    }
//                }
//            }
//        }
//        // Notificar los cambios
//        notifyDataSetChanged();
//    }


    @Override
    public int getItemCount() {
        return listaPacientes.size();
    }

    public class Pre_PacienteDetalleViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombrePaciente, viewCedula, viewConsultado;

        public Pre_PacienteDetalleViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombrePaciente = itemView.findViewById(R.id.tvNombrePaciente);
            viewCedula = itemView.findViewById(R.id.tvCedula);
            viewConsultado = itemView.findViewById(R.id.tvConsultando);

            // Agregamos evento a la fila
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    sharedPref = context.getSharedPreferences("preconsulta", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("nombre_paciente", listaPacientes.get(getAbsoluteAdapterPosition()).getNombrePaciente());
                    editor.putString("fechanac", listaPacientes.get(getAbsoluteAdapterPosition()).getFechanac());
                    editor.putString("precon_codigo_establecimiento", listaPacientes.get(getAbsoluteAdapterPosition()).getCodigo_establecimiento());
                    editor.putString("pacasi_codigo_asignacion", listaPacientes.get(getAbsoluteAdapterPosition()).getCodigo_asignacion());
                    editor.putString("precon_temperatura_corporal", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_temperatura_corporal().toString());
                    editor.putString("precon_presion_arterial", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_presion_arterial().toString());
                    editor.putString("precon_frecuencia_respiratoria", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_frecuencia_respiratoria().toString());
                    editor.putString("precon_pulso", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_pulso().toString());
                    editor.putString("precon_peso", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_peso().toString());
                    editor.putString("precon_talla", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_talla().toString());
                    editor.putString("precon_imc", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_imc().toString());
                    editor.putString("precon_saturacion", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_saturacion().toString());
                    editor.putString("precon_circunferencia_abdominal", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_circunferencia_abdominal().toString());
                    editor.putString("precon_motivo_consulta", listaPacientes.get(getAbsoluteAdapterPosition()).getPrecon_motivo_consulta());
                    editor.commit();
                    // ir al formulario
                    context.startActivity(new Intent(context, Pre_FormPreconsultaActivity.class));
                }
            });
        }
    }
}
