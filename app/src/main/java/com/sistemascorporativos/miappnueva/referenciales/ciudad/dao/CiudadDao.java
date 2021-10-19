package com.sistemascorporativos.miappnueva.referenciales.ciudad.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.ciudad.modelos.CiudadDto;

import java.util.ArrayList;

public class CiudadDao extends ConexionDb {

    private Context context;

    public CiudadDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<CiudadDto> getCiudades() {
        ArrayList<CiudadDto> lista = new ArrayList<>();
        String querySQL = "SELECT ciu_id, ciu_descripcion FROM ciudades;";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                CiudadDto obj = new CiudadDto();
                obj.setCiuId(cursor.getInt(0));
                obj.setCiuDescripcion(cursor.getString(1));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
