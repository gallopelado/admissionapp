package com.sistemascorporativos.miappnueva.admision.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;
import com.sistemascorporativos.miappnueva.admision.entidades.Usuario;
import com.sistemascorporativos.miappnueva.db.ConexionDb;

import java.util.ArrayList;

public class AdmisionDao extends ConexionDb {

    private Context context;

    public AdmisionDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Profesional> getProfesionales() {
        ArrayList<Profesional> listaProfesionales = new ArrayList<>();
        String querySQL = "SELECT p.prof_codigo_medico, e.espec_id, e.espec_descripcion, u.usu_nombres, u.usu_apellidos FROM profesional p LEFT JOIN usuarios u ON u.usu_codigo_usuario = p.prof_codigo_medico LEFT JOIN especialidad e ON e.espec_id = p.espec_id WHERE p.prof_activo = 't';";
        try(
                ConexionDb conexionDb = new ConexionDb(context);
                SQLiteDatabase db = conexionDb.getWritableDatabase();
                Cursor cursorProfesional =  db.rawQuery(querySQL, null);

                ) {

            while(cursorProfesional.moveToNext()) {
                Profesional obj = new Profesional();
                Usuario usuario = new Usuario();
                usuario.setCodigoUsuario(cursorProfesional.getString(0));
                usuario.setNombreUsuario(cursorProfesional.getString(3));
                usuario.setApellidoUsuario(cursorProfesional.getString(4));
                obj.setUsuario(usuario);
                Especialidad espe = new Especialidad();
                espe.setEspecialidadId(cursorProfesional.getInt(1));
                espe.setEspecialidadDescripcion(cursorProfesional.getString(2));
                obj.setEspecialidad(espe);
                listaProfesionales.add(obj);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
        return listaProfesionales;
    }

}
