package com.sistemascorporativos.miappnueva.seguridad.login.dao;

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

public class LoginDao extends ConexionDb {

    private Context context;

    public LoginDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto agregarUsuario(LoginDto login) {
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("usu_codigo_usuario", login.getUsuCodigoUsuario());
            values.put("usu_password", login.getUsuPassword());
            values.put("usu_nombres", login.getUsuNombres());
            values.put("usu_apellidos", login.getUsuApellidos());
            values.put("usu_descripcion", login.getUsuApellidos());
            values.put("usu_rol", login.getUsuRol());
            values.put("usu_estado", login.getUsuEstado());
            values.put("usu_creacion_usuario", login.getUsuCreacion_usuario());
            values.put("usu_creacion_fecha", LocalDate.now().toString());
            values.put("usu_creacion_hora", LocalTime.now().toString());
            Long estado_insert = db.insert("usuarios", null, values);
            if(estado_insert>0) {
                login.setOperacion("INSERTADO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LoginDto modificarPassword(LoginDto login) {
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("usu_password", login.getUsuPassword());
            values.put("usu_modificacion_usuario", login.getUsuCreacion_usuario());
            values.put("usu_modificacion_fecha", LocalDate.now().toString());
            values.put("usu_modificacion_hora", LocalTime.now().toString());
            Integer estado_update = db.update("usuarios", values, "usu_codigo_usuario = ?", new String[]{ login.getUsuCodigoUsuario() });
            if(estado_update>0) {
                login.setOperacion("ACTUALIZADO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    public LoginDto getUsuario(String codigo_usuario) {
        LoginDto login = new LoginDto();
        String querySQL = "SELECT usu_codigo_usuario, usu_password, usu_nombres, usu_apellidos, usu_descripcion, usu_rol, usu_estado, usu_creacion_usuario, usu_creacion_fecha, usu_creacion_hora, usu_modificacion_usuario, usu_modificacion_fecha, usu_modificacion_hora FROM usuarios WHERE usu_estado='t' AND usu_codigo_usuario="+codigo_usuario;
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase(); Cursor cursor =  db.rawQuery(querySQL, null);) {
           if(cursor.moveToFirst()) {
               login.setUsuCodigoUsuario(codigo_usuario);
               login.setUsuPassword(cursor.getString(cursor.getColumnIndex("usu_password")));
               login.setUsuNombres(cursor.getString(cursor.getColumnIndex("usu_nombres")));
               login.setUsuApellidos(cursor.getString(cursor.getColumnIndex("usu_apellidos")));
               login.setUsuDescripcion(cursor.getString(cursor.getColumnIndex("usu_descripcion")));
               login.setUsuRol(cursor.getString(cursor.getColumnIndex("usu_rol")));
               login.setUsuEstado(cursor.getString(cursor.getColumnIndex("usu_estado")));
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    public LoginDto getUserAndPassword(String user, String password) {
        LoginDto login = new LoginDto();
        String querySQL = "SELECT usu_codigo_usuario, usu_password, usu_nombres, usu_apellidos, usu_descripcion, usu_rol, usu_estado, usu_creacion_usuario, usu_creacion_fecha, usu_creacion_hora, usu_modificacion_usuario, usu_modificacion_fecha, usu_modificacion_hora FROM usuarios WHERE usu_estado='t' AND usu_codigo_usuario='"+user+"' AND usu_password='"+password+"'";
        try(ConexionDb conexionDb = new ConexionDb(context); SQLiteDatabase db = conexionDb.getWritableDatabase(); Cursor cursor =  db.rawQuery(querySQL, null);) {
            if(cursor.moveToFirst()) {
                login.setUsuCodigoUsuario(user);
                login.setUsuPassword(cursor.getString(cursor.getColumnIndex("usu_password")));
                login.setUsuNombres(cursor.getString(cursor.getColumnIndex("usu_nombres")));
                login.setUsuApellidos(cursor.getString(cursor.getColumnIndex("usu_apellidos")));
                login.setUsuDescripcion(cursor.getString(cursor.getColumnIndex("usu_descripcion")));
                login.setUsuRol(cursor.getString(cursor.getColumnIndex("usu_rol")));
                login.setUsuEstado(cursor.getString(cursor.getColumnIndex("usu_estado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

}
