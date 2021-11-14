package com.sistemascorporativos.miappnueva.seguridad.usuario.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.seguridad.login.entidades.LoginDto;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto actualizarUsuario(LoginDto login) {
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            if(login.getUsuPassword()!=null) {
                values.put("usu_password", login.getUsuPassword());
            }
            values.put("usu_nombres", login.getUsuNombres());
            values.put("usu_apellidos", login.getUsuApellidos());
            values.put("usu_descripcion", login.getUsuDescripcion());
            values.put("usu_rol", login.getUsuRol());
            values.put("usu_estado", login.getUsuEstado());
            values.put("usu_modificacion_usuario", login.getUsuCreacion_usuario());
            values.put("usu_modificacion_fecha", LocalDate.now().toString());
            values.put("usu_modificacion_hora", LocalTime.now().toString());
            Integer estado_update = db.update("usuarios", values, "usu_codigo_usuario = ?", new String[]{ login.getUsuCodigoUsuario() });
            if(estado_update>0) {
                login.setOperacion("ACTUALIZADO");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return login;
    }

}
