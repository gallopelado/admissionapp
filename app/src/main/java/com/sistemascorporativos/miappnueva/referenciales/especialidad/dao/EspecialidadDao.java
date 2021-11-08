package com.sistemascorporativos.miappnueva.referenciales.especialidad.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;

import java.util.ArrayList;

public class EspecialidadDao extends ConexionDb {

    private Context context;

    public EspecialidadDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Especialidad> getEspecialidades() {
        ArrayList<Especialidad> lista = new ArrayList<>();
        String querySQL = "SELECT espec_id, espec_descripcion FROM especialidad;";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                Especialidad obj = new Especialidad();
                obj.setEspecialidadId(cursor.getInt(0));
                obj.setEspecialidadDescripcion(cursor.getString(1));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
