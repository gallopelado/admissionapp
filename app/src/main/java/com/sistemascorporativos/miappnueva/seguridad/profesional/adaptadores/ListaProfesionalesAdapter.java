package com.sistemascorporativos.miappnueva.seguridad.profesional.adaptadores;

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
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
import com.sistemascorporativos.miappnueva.seguridad.profesional.actividades.FormProfesionalActivity;
import com.sistemascorporativos.miappnueva.seguridad.profesional.entidades.ProfesionalDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaProfesionalesAdapter extends RecyclerView.Adapter<ListaProfesionalesAdapter.ProfesionalViewHolder> {

    private ArrayList<ProfesionalDto> listaProfesionalItems;
    private ArrayList<ProfesionalDto> listaProfesionalOriginalItems;
    private SharedPreferences sharedPref, sharedPref_menu;

    public ListaProfesionalesAdapter(ArrayList<ProfesionalDto> listaProfesionalItems) {
        this.listaProfesionalItems = listaProfesionalItems;
        this.listaProfesionalOriginalItems = new ArrayList<>();
        this.listaProfesionalOriginalItems.addAll(listaProfesionalItems);
    }

    @NonNull
    @Override
    public ListaProfesionalesAdapter.ProfesionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_profesionales, null, false);
        return new ProfesionalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProfesionalesAdapter.ProfesionalViewHolder holder, int position) {
        holder.viewIdProfesional.setText(listaProfesionalItems.get(position).getProf_codigo_medico());
        holder.viewDescripcionProfesional.setText(listaProfesionalItems.get(position).getProfesional_descripcion());
        holder.viewEspecialidad.setText(listaProfesionalItems.get(position).getEspecialidad());
    }

    public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaProfesionalItems.clear();
            listaProfesionalItems.addAll(listaProfesionalOriginalItems);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ProfesionalDto> lista_filtrada = listaProfesionalItems.stream()
                        .filter(e ->
                                (e.getProf_codigo_medico().contains(txtBuscar) || e.getProfesional_descripcion().toLowerCase().contains(txtBuscar.toLowerCase())  || e.getEspecialidad().toLowerCase().contains(txtBuscar.toLowerCase()) ))
                        .collect(Collectors.toList());
                listaProfesionalItems.clear();
                listaProfesionalItems.addAll(lista_filtrada);
            } else {
                listaProfesionalItems.clear();
                for(ProfesionalDto e: listaProfesionalItems) {
                    if(e.getProf_codigo_medico().contains(txtBuscar) || e.getProfesional_descripcion().toLowerCase().contains(txtBuscar.toLowerCase()) || e.getEspecialidad().toLowerCase().contains(txtBuscar.toLowerCase()) ) {
                        listaProfesionalItems.add(e);
                    }
                }
            }
        }
        // Notificar los cambios
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaProfesionalItems.size();
    }

    public class ProfesionalViewHolder extends RecyclerView.ViewHolder {
        TextView viewIdProfesional, viewDescripcionProfesional, viewEspecialidad;
        public ProfesionalViewHolder(@NonNull View itemView) {
            super(itemView);
            viewIdProfesional = itemView.findViewById(R.id.tvIdProfesional);
            viewDescripcionProfesional = itemView.findViewById(R.id.tvDescripcionProfesional);
            viewEspecialidad = itemView.findViewById(R.id.tvEspecialidadProfesional);

            // Seleccionar algún registro de la tabla
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    sharedPref = context.getSharedPreferences("profesional_referencial", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("codigo_medico", listaProfesionalItems.get(getAbsoluteAdapterPosition()).getProf_codigo_medico());
                    editor.putString("profesional_descripcion", listaProfesionalItems.get(getAbsoluteAdapterPosition()).getProfesional_descripcion());
                    editor.putString("profesional_rol", listaProfesionalItems.get(getAbsoluteAdapterPosition()).getRol());
                    editor.putString("prof_numero_registro", listaProfesionalItems.get(getAbsoluteAdapterPosition()).getProf_numero_registro());
                    editor.putString("espec_id", listaProfesionalItems.get(getAbsoluteAdapterPosition()).getEspec_id().toString());
                    editor.putString("especialidad", listaProfesionalItems.get(getAbsoluteAdapterPosition()).getEspecialidad());
                    editor.putString("prof_activo", listaProfesionalItems.get(getAbsoluteAdapterPosition()).getProf_activo());
                    editor.putString("operacion", "editar");
                    editor.commit();
                    // Ir al formulario
                    context.startActivity(new Intent(context, FormProfesionalActivity.class));
                }
            });
        }
    }
}
