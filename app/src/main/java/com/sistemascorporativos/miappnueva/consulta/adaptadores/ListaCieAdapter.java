package com.sistemascorporativos.miappnueva.consulta.adaptadores;

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
//import com.sistemascorporativos.miappnueva.consulta.actividades.ListaPacientesAdmitidosActivity;
//import com.sistemascorporativos.miappnueva.consulta.entidades.PacienteAsignacionDto;
import com.sistemascorporativos.miappnueva.admision.actividades.ListaPacientesAdmitidosActivity;
import com.sistemascorporativos.miappnueva.consulta.actividades.FormularioConsultaActivity;
import com.sistemascorporativos.miappnueva.consulta.actividades.ListaPacientesPreconsultaActivity;
import com.sistemascorporativos.miappnueva.consulta.entidades.Cie;
import com.sistemascorporativos.miappnueva.consulta.dao.ConsultaDao;
import com.sistemascorporativos.miappnueva.consulta.servicios.ConsultaServices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaCieAdapter extends RecyclerView.Adapter<ListaCieAdapter.CieViewHolder> {

    ArrayList<Cie> listaCie;
    ArrayList<Cie> listaOriginalCie;
    SharedPreferences sharedPref;

    public ListaCieAdapter(ArrayList<Cie> listaCie) {
        this.listaCie = listaCie;
        listaOriginalCie = new ArrayList<>();
        listaOriginalCie.addAll(listaCie);
    }

    @NonNull
    @Override
    public CieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_cie, null, false);
        return new CieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CieViewHolder holder, int position) {
        holder.viewNombreCie.setText(listaCie.get(position).getCieDescripcion());
        holder.viewTipoCie.setText(listaCie.get(position).getCieTipo());
    }

    @Override
    public int getItemCount() {
        return listaCie.size();
    }

    public class CieViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombreCie, viewTipoCie;

        public CieViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombreCie = itemView.findViewById(R.id.tvNombreCie);
            viewTipoCie = itemView.findViewById(R.id.tvTipo);

            // Seleccionar algún registro de nuestra tabla
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    // Guardamos en el store el codigo médico
                    sharedPref = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("codigo_cie", listaCie.get(getAbsoluteAdapterPosition()).getcieId().toString());
                    editor.putString("descripcion_cie", listaCie.get(getAbsoluteAdapterPosition()).getCieDescripcion().toString());
                    editor.putString("tipo_cie", listaCie.get(getAbsoluteAdapterPosition()).getCieTipo().toString());
                    editor.commit();
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
                    dialogo.setTitle("Asignar Cie")
                            .setMessage("Desear asignar "+listaCie.get(getAbsoluteAdapterPosition()).getCieDescripcion().toString()+" del tipo "+listaCie.get(getAbsoluteAdapterPosition()).getCieTipo().toString()+"?")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                String codigo_cie = sharedPref.getString("codigo_cie", null);
                                String descripcion_cie = sharedPref.getString("descripcion_cie", null);
                                String tipo_cie = sharedPref.getString("descripcion_cie", null);
                                Intent intent = new Intent(context, ListaPacientesPreconsultaActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            })
                            .setNegativeButton("No", null).show();
                }
            });
        }
    }


}
