package com.sistemascorporativos.miappnueva.seguridad.profesional.adaptadores;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;
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

    }

    /*public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaProfesionalItems.clear();
            listaProfesionalItems.addAll(listaProfesionalOriginalItems);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ProfesionalDto> lista_filtrada = listaProfesionalItems.stream()
                        .filter(e ->
                                (e.getProf_codigo_medico().contains(txtBuscar) || e.getUsuNombres().toLowerCase().contains(txtBuscar.toLowerCase()) || e.getUsuApellidos().toLowerCase().contains(txtBuscar.toLowerCase())))
                        .collect(Collectors.toList());
                listaProfesionalItems.clear();
                listaProfesionalItems.addAll(lista_filtrada);
            } else {
                listaProfesionalItems.clear();
                for(ProfesionalDto e: listaProfesionalItems) {
                    if(e.getUsuCodigoUsuario().contains(txtBuscar) || e.getUsuNombres().toLowerCase().contains(txtBuscar.toLowerCase()) || e.getUsuApellidos().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaProfesionalItems.add(e);
                    }
                }
            }
        }
        // Notificar los cambios
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProfesionalViewHolder extends RecyclerView.ViewHolder {
        public ProfesionalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
