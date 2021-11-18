package com.sistemascorporativos.miappnueva.preconsulta.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.actividades.ListaPacientesAdmitidosActivity;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAdmitidoDetalle;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.Pre_PacienteAdmitido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//x
public class Pre_ListaAdmitidosAdapter extends RecyclerView.Adapter<Pre_ListaAdmitidosAdapter.Pre_PacienteDetalleViewHolder>{

    ArrayList<Pre_PacienteAdmitido> listaPacientes2;
    ArrayList<Pre_PacienteAdmitido> listaOriginalPacientes2;
    SharedPreferences sharedPref;

    public Pre_ListaAdmitidosAdapter(ArrayList<Pre_PacienteAdmitido> listaPacientes) {
        this.listaPacientes2 = listaPacientes;
        listaOriginalPacientes2 = new ArrayList<>();
        listaOriginalPacientes2.addAll(listaPacientes);
    }

    @NonNull
    @Override
    public Pre_PacienteDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pre_seleccionar_admitido, null, false);
        return new Pre_PacienteDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pre_PacienteDetalleViewHolder holder, int position) {
        holder.viewNombrePaciente.setText(listaPacientes2.get(position).getNombrePaciente());
        holder.viewCedula.setText(listaPacientes2.get(position).getCedulaPaciente());
        holder.viewConsultado.setText(listaPacientes2.get(position).getConsultando());
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
        return listaPacientes2.size();
    }

    public class Pre_PacienteDetalleViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombrePaciente, viewCedula, viewConsultado;

        public Pre_PacienteDetalleViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombrePaciente = itemView.findViewById(R.id.tvNombrePaciente);
            viewCedula = itemView.findViewById(R.id.tvCedula);
            viewConsultado = itemView.findViewById(R.id.tvConsultando);
        }
    }



//    @Override
//    public int getItemCount() {
//        return listaPacientes2.size();
//    }
//
//    public class MedicoViewHolder extends RecyclerView.ViewHolder {
//
//        TextView viewNombreMedico, viewEspecialidadMedico;
//
//        public MedicoViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            viewNombreMedico = itemView.findViewById(R.id.tvNombreMedico);
//            viewEspecialidadMedico = itemView.findViewById(R.id.tvConsultando);
//
//            // Seleccionar algún registro de nuestra tabla
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = itemView.getContext();
//                    // Guardamos en el store el codigo médico
//                    sharedPref = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString("codigo_medico", listaMedicos2.get(getAbsoluteAdapterPosition()).getUsuario().getCodigoUsuario());
//                    editor.commit();
//
//                    String medico = "Dr. "+listaMedicos2.get(getAbsoluteAdapterPosition()).getUsuario().getNombreUsuario()+" "+listaMedicos2.get(getAbsoluteAdapterPosition()).getUsuario().getApellidoUsuario();
//                    AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
//                    dialogo.setTitle("Asignar Médico")
//                            .setMessage("Desear asignar al "+ medico)
//                            .setPositiveButton("Sí", (dialog, which) -> {
//                                String codigo_paciente = sharedPref.getString("codigo_paciente", null);
//                                String codigo_medico = sharedPref.getString("codigo_medico", null);
//                                Integer seguro_medico = sharedPref.getInt("seguro_medico", 0);
//                                Boolean res = asignarMedico2(codigo_paciente, codigo_medico, seguro_medico, context);
//                                if(res==true) {
//                                    irAbuscarPaciente(context);
//                                }
//                            })
//                            .setNegativeButton("No", null).show();
//                }
//            });
//        }
//    }
//
//    public void irAbuscarPaciente(Context context) {
//        Intent intent = new Intent(context, ListaPacientesAdmitidosActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
}
