package com.sistemascorporativos.miappnueva.referenciales.nacionalidad.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.nacionalidad.modelos.NacionalidadDto;

import java.util.ArrayList;

public class NacionalidadDao extends ConexionDb {

    private Context context;

    public NacionalidadDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<NacionalidadDto> getNacionalidades() {
        ArrayList<NacionalidadDto> lista = new ArrayList<>();
        String querySQL = "SELECT nac_id, nac_descripcion FROM nacionalidades;";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                NacionalidadDto obj = new NacionalidadDto();
                obj.setNacId(cursor.getInt(0));
                obj.setNacDescripcion(cursor.getString(1));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
