package com.sistemascorporativos.miappnueva.seguridad.usuario.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;

import java.util.ArrayList;

public class UsuarioDao extends ConexionDb {

    private Context context;

    public UsuarioDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Método getUsuarios()
     *
     * Este método trae todos los registros de la tabla usuarios
     * de SQLITE, la verificación es que estos registros tengan el
     * campo de la base de datos usu_estado = 't', esto significa usuarios
     * válidos o activos.
     *
     * @return {ArrayList<LoginDto>} listaUsuarios
     * @autor Juan José González Ramírez <juanftp100@gmail.com>
     */
    public ArrayList<LoginDto> getUsuarios() {
        ArrayList<LoginDto> listaUsuarios = new ArrayList<>();
        String querySQL = "SELECT usu_codigo_usuario, usu_password, usu_nombres, usu_apellidos, usu_descripcion, usu_rol, usu_estado FROM usuarios WHERE usu_estado = 't'";
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase(); Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                LoginDto obj = new LoginDto();
                obj.setUsuCodigoUsuario(cursor.getString(cursor.getColumnIndex("usu_codigo_usuario")));
                obj.setUsuPassword(cursor.getString(cursor.getColumnIndex("usu_password")));
                obj.setUsuNombres(cursor.getString(cursor.getColumnIndex("usu_nombres")));
                obj.setUsuApellidos(cursor.getString(cursor.getColumnIndex("usu_apellidos")));
                obj.setUsuDescripcion(cursor.getString(cursor.getColumnIndex("usu_descripcion")));
                obj.setUsuRol(cursor.getString(cursor.getColumnIndex("usu_rol")));
                obj.setUsuEstado(cursor.getString(cursor.getColumnIndex("usu_estado")));
                listaUsuarios.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }
}
