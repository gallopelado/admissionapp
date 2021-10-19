package com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;
import com.sistemascorporativos.miappnueva.referenciales.situacion_laboral.modelos.SituacionLaboralDto;

import java.util.ArrayList;

public class SituacionLaboralDao extends ConexionDb {

    private Context context;

    public SituacionLaboralDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<SituacionLaboralDto> getSituacionLaboral() {
        ArrayList<SituacionLaboralDto> lista = new ArrayList<>();
        String querySQL = "SELECT sitlab_id, sitlab_descripcion FROM situacion_laboral;";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                SituacionLaboralDto obj = new SituacionLaboralDto();
                obj.setSitlabId(cursor.getInt(0));
                obj.setSitlabDescripcion(cursor.getString(1));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
