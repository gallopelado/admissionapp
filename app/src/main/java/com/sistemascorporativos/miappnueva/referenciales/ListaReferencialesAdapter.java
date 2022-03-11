package com.sistemascorporativos.miappnueva.referenciales;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sistemascorporativos.miappnueva.R;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAdmitidoDetalle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaReferencialesAdapter extends RecyclerView.Adapter<ListaReferencialesAdapter.ReferencialViewHolder> {

    private ArrayList<ReferencialDto> listaItems;
    private ArrayList<ReferencialDto> listaOriginalItems;
    private SharedPreferences sharedPref, sharedPref_menu;
    private String url = "http://10.0.2.2:5000/apiv1/";

    public ListaReferencialesAdapter(ArrayList<ReferencialDto> listaItems) {
        this.listaItems = listaItems;
        listaOriginalItems = new ArrayList<>();
        listaOriginalItems.addAll(listaItems);
    }

    @NonNull
    @Override
    public ListaReferencialesAdapter.ReferencialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_referencial, null, false);
        return new ReferencialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaReferencialesAdapter.ReferencialViewHolder holder, int position) {
        holder.vieId.setText(listaItems.get(position).getId());
        holder.vieDescripcion.setText(listaItems.get(position).getDescripcion());
    }

    public void filtrado(String txtBuscar) {
        Integer longitud = txtBuscar.length();
        if(longitud == 0) {
            listaItems.clear();
            listaItems.addAll(listaOriginalItems);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ReferencialDto> lista_filtrada = listaItems.stream()
                        .filter(e ->
                                (e.getId().contains(txtBuscar) || e.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())))
                        .collect(Collectors.toList());
                listaItems.clear();
                listaItems.addAll(lista_filtrada);
            } else {
                listaItems.clear();
                for(ReferencialDto e: listaItems) {
                    if(e.getId().contains(txtBuscar) || e.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaItems.add(e);
                    }
                }
            }
        }
        // Notificar los cambios
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ReferencialViewHolder extends RecyclerView.ViewHolder {

        TextView vieId, vieDescripcion;

        public ReferencialViewHolder(@NonNull View itemView) {
            super(itemView);
            vieId = itemView.findViewById(R.id.tvIdReferencial);
            vieDescripcion = itemView.findViewById(R.id.tvDescripcionReferencial);

            // Seleccionar algún registro de la tabla
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    sharedPref = context.getSharedPreferences("referenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("referencial_id", listaItems.get(getAbsoluteAdapterPosition()).getId());
                    editor.putString("referencial_descripcion", listaItems.get(getAbsoluteAdapterPosition()).getDescripcion());
                    editor.commit();
                    // Ir al formulario
                    context.startActivity(new Intent(context, FormMainReferencialesActivity.class));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Context context = itemView.getContext();
                    sharedPref_menu = context.getSharedPreferences("menu", Context.MODE_PRIVATE);
                    String id = listaItems.get(getAbsoluteAdapterPosition()).getId();
                    ReferencialDao referencialDao = new ReferencialDao(context);
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
                    dialogo.setTitle("Eliminar"). setMessage("¿Desea eliminar este registro?")
                            .setPositiveButton("Si", (dialog, which) -> {
                                ReferencialDto referencialDto = new ReferencialDto();
                                referencialDto.setId(id);
                                switch (sharedPref_menu.getString("menu", null)) {
                                    case "ciudad":
                                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                                                url + "referenciales/ciudad/delete_ciudad/"+id, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.e("ERROR MODIFY CIUDAD", error.toString());
                                            }
                                        }) {
                                            @Override
                                            public String getBodyContentType() {
                                                return "application/json; charset=utf-8";
                                            }
                                        };
                                        requestQueue.add(stringRequest);
                                        break;
                                    case "nacionalidad":
                                        referencialDto = referencialDao.eliminar(referencialDto, "nacionalidades");
                                        break;
                                    case "seguro_medico":
                                        referencialDto = referencialDao.eliminar(referencialDto, "seguro_medico");
                                        break;
                                    case "nivel_educativo":
                                        referencialDto = referencialDao.eliminar(referencialDto, "nivel_educativo");
                                        break;
                                    case "situacion_laboral":
                                        referencialDto = referencialDao.eliminar(referencialDto, "situacion_laboral");
                                        break;
                                    case "especialidad":
                                        referencialDto = referencialDao.eliminar(referencialDto, "especialidad");
                                        break;
                                }
                                // eliminar fila
                                int remover = (int) v.getTag();
                                listaItems.remove(remover);
                                // Notificar los cambios
                                notifyDataSetChanged();
                            }).setNegativeButton("No", null).show();

                    return false;
                }
            });
        }
    }
}
