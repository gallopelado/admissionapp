package com.sistemascorporativos.miappnueva.admision.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.actividades.AsignarMedicoActivity;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAsignacionDto;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;
import com.sistemascorporativos.miappnueva.admision.servicios.AdmisionServices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaMedicosAdapter extends RecyclerView.Adapter<ListaMedicosAdapter.MedicoViewHolder> {

    ArrayList<Profesional> listaMedicos;
    ArrayList<Profesional> listaOriginalMedicos;
    SharedPreferences sharedPref;

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

            // Seleccionar algún registro de nuestra tabla
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    // Guardamos en el store el codigo médico
                    sharedPref = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("codigo_medico", listaMedicos.get(getAbsoluteAdapterPosition()).getUsuario().getCodigoUsuario());
                    editor.commit();

                    AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
                    dialogo.setTitle("Asignar Médico").setMessage("Desear asignar este médico")
                            .setPositiveButton("OK", (dialog, which) -> {
                                System.out.println("ASIGNANDO PACIENTE Y MEDICO= "+sharedPref.getString("codigo_medico", null)+", "+sharedPref.getInt("seguro_medico", 0));
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();

                    //Intent intent = new Intent(context, AsignarMedicoActivity.class);
                    //context.startActivity(intent);
                    String codigo_paciente = sharedPref.getString("codigo_paciente", null);
                    String codigo_medico = sharedPref.getString("codigo_medico", null);
                    Integer seguro_medico = sharedPref.getInt("seguro_medico", 0);
                    asignarMedico(codigo_paciente, codigo_medico, seguro_medico, context);
                }
            });
        }
    }

    private Boolean asignarMedico(String codigo_paciente, String codigo_medico, Integer seguro_medico, Context ctx) {
        String codigo_establecimiento = "001";
        // Guardar en paciente asignacion
        AdmisionServices adms = new AdmisionServices(ctx);
        PacienteAsignacionDto pad = new PacienteAsignacionDto();
        pad.setPacasiCodigoEstablecimiento(codigo_establecimiento);
        pad.setPacCodigoPaciente(codigo_paciente);
        pad.setMedId(codigo_medico);
        pad.setSegId(seguro_medico);
        pad = adms.insertarPacienteAsignacion(pad);
        System.out.println("Paciente asignado= " + pad.getPacasiEstado());
        System.out.println("*** "+pad.toString());
        if(pad.getPacasiEstado() != null) {
            return true;
        }
        return false;
        // Crear registros para preconsulta y consulta
    }
}
