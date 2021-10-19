package com.sistemascorporativos.miappnueva.referenciales.seguro_medico.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.referenciales.seguro_medico.modelos.SeguroMedicoDto;

import java.util.ArrayList;

public class SeguroMedicoDao extends ConexionDb {

    private Context context;

    public SeguroMedicoDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<SeguroMedicoDto> getSeguroMedico() {
        ArrayList<SeguroMedicoDto> lista = new ArrayList<>();
        String querySQL = "SELECT seg_id, seg_descripcion FROM seguro_medico;";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                SeguroMedicoDto obj = new SeguroMedicoDto();
                obj.setSegId(cursor.getInt(0));
                obj.setSegDescripcion(cursor.getString(1));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
