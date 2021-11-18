package com.sistemascorporativos.miappnueva.seguridad.profesional.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.seguridad.profesional.entidades.ProfesionalDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ProfesionalDao extends ConexionDb {

    private Context context;

    public ProfesionalDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<ProfesionalDto> getProfesionales() {
        ArrayList<ProfesionalDto> listaProfesionales = new ArrayList<>();
        String querySQL = "SELECT p.prof_codigo_medico, p.prof_numero_registro, u.usu_nombres ||' '|| u.usu_apellidos usuario, u.usu_rol rol, p.espec_id, e.espec_descripcion especialidad, p.prof_activo FROM profesional p LEFT JOIN usuarios u ON u.usu_codigo_usuario = p.prof_codigo_medico LEFT JOIN especialidad e ON e.espec_id = p.espec_id WHERE p.prof_activo = 't'";
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase(); Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                ProfesionalDto obj = new ProfesionalDto();
                obj.setProf_codigo_medico(cursor.getString(cursor.getColumnIndex("prof_codigo_medico")));
                obj.setProf_numero_registro(cursor.getString(cursor.getColumnIndex("prof_numero_registro")));
                obj.setProfesional_descripcion(cursor.getString(cursor.getColumnIndex("usuario")));
                obj.setRol(cursor.getString(cursor.getColumnIndex("rol")));
                obj.setEspec_id(cursor.getInt(cursor.getColumnIndex("espec_id")));
                obj.setEspecialidad(cursor.getString(cursor.getColumnIndex("especialidad")));
                obj.setProf_activo(cursor.getString(cursor.getColumnIndex("prof_activo")));
                listaProfesionales.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProfesionales;
    }

    public ProfesionalDto getProfesional(String codigo_medico) {
        ProfesionalDto obj = new ProfesionalDto();
        String querySQL = "SELECT p.prof_codigo_medico, p.prof_numero_registro, u.usu_nombres ||' '|| u.usu_apellidos usuario, u.usu_rol rol, p.espec_id, e.espec_descripcion especialidad, p.prof_activo FROM profesional p LEFT JOIN usuarios u ON u.usu_codigo_usuario = p.prof_codigo_medico LEFT JOIN especialidad e ON e.espec_id = p.espec_id  WHERE p.prof_activo = 't' AND p.prof_codigo_medico = " + codigo_medico;
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            if(cursor.moveToFirst()) {
                obj.setProf_codigo_medico(codigo_medico);
                obj.setProf_numero_registro(cursor.getString(cursor.getColumnIndex("prof_numero_registro")));
                obj.setProfesional_descripcion(cursor.getString(cursor.getColumnIndex("usuario")));
                obj.setRol(cursor.getString(cursor.getColumnIndex("rol")));
                obj.setEspec_id(cursor.getInt(cursor.getColumnIndex("espec_id")));
                obj.setEspecialidad(cursor.getString(cursor.getColumnIndex("especialidad")));
                obj.setProf_activo(cursor.getString(cursor.getColumnIndex("prof_activo")));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ProfesionalDto insertarNuevo(ProfesionalDto profesional) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("prof_codigo_medico", profesional.getProf_codigo_medico());
            values.put("prof_numero_registro", profesional.getProf_numero_registro());
            values.put("espec_id", profesional.getEspec_id());
            values.put("prof_activo", profesional.getProf_activo());
            values.put("prof_creacion_usuario", profesional.getCreacion_usuario());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("prof_creacion_fecha", LocalDate.now().toString());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("prof_creacion_hora", LocalTime.now().toString());
            }
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
            values.put("prof_activo", profesional.getProf_activo());
            values.put("prof_modificacion_usuario", profesional.getModificacion_usuario());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("prof_modificacion_fecha", LocalDate.now().toString());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("prof_modificacion_hora", LocalTime.now().toString());
            }
            Integer estado_update = db.update("profesional", values,"prof_codigo_medico = ?", new String[]{ profesional.getProf_codigo_medico() });
            if(estado_update > 0) return profesional;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
