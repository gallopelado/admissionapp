package com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.nivel_educativo.modelos.NivelEducativoDto;

import java.util.ArrayList;

public class NivelEducativoDao extends ConexionDb {

    private Context context;

    public NivelEducativoDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<NivelEducativoDto> getNivelEducativo() {
        ArrayList<NivelEducativoDto> lista = new ArrayList<>();
        String querySQL = "SELECT edu_id, edu_descripcion FROM nivel_educativo;";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                NivelEducativoDto obj = new NivelEducativoDto();
                obj.setEduId(cursor.getInt(0));
                obj.setEduDescripcion(cursor.getString(1));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
