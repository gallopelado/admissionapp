package com.sistemascorporativos.miappnueva.seguridad.profesional.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.seguridad.profesional.entidades.ProfesionalDto;

import java.util.ArrayList;

public class ProfesionalDao extends ConexionDb {

    private Context context;

    public ProfesionalDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<ProfesionalDto> getProfesionales() {
        ArrayList<ProfesionalDto> listaProfesionales = new ArrayList<>();
        String querySQL = "SELECT prof_codigo_medico, prof_numero_registro, espec_id, prof_activo FROM profesional";
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase(); Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                ProfesionalDto obj = new ProfesionalDto();
                obj.setProf_codigo_medico(cursor.getString(cursor.getColumnIndex("prof_codigo_medico")));
                obj.setProf_numero_registro(cursor.getString(cursor.getColumnIndex("prof_numero_registro")));
                obj.setEspec_id(cursor.getInt(cursor.getColumnIndex("espec_id")));
                obj.setProf_activo(cursor.getString(cursor.getColumnIndex("prof_activo")));
                listaProfesionales.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProfesionales;
    }

    public ProfesionalDto insertarNuevo(ProfesionalDto profesional) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("prof_codigo_medico", profesional.getProf_codigo_medico());
            values.put("prof_numero_registro", profesional.getProf_numero_registro());
            values.put("espec_id", profesional.getEspec_id());
            values.put("prof_activo", profesional.getEspec_id());
            Long estado_insert = db.insert("profesional", null, values);
            if(estado_insert > 0) return profesional;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProfesionalDto actualizar(ProfesionalDto profesional) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("prof_codigo_medico", profesional.getProf_codigo_medico());
            values.put("prof_numero_registro", profesional.getProf_numero_registro());
            values.put("espec_id", profesional.getEspec_id());
            values.put("prof_activo", profesional.getEspec_id());
            Integer estado_update = db.update("profesional", values,"prof_codigo_medico = ?", new String[]{ profesional.getProf_codigo_medico() });
            if(estado_update > 0) return profesional;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
