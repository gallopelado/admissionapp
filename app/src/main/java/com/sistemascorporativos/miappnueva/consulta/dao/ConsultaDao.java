package com.sistemascorporativos.miappnueva.consulta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;
import com.sistemascorporativos.miappnueva.admision.entidades.Usuario;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.consulta.entidades.PacientePreconsultaDetalle;
import com.sistemascorporativos.miappnueva.db.ConexionDb;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ConsultaDao extends ConexionDb {

    private Context context;

    public ConsultaDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public PacienteDto getDatosPacienteByCodigoPaciente(String codigo_paciente) {
        PacienteDto obj = new PacienteDto();
        String querySQL = "SELECT pac_codigo_paciente, pac_tipo_documento, pac_nombres, pac_apellidos, pac_sexo, pac_fechanac, pac_lugar_nacimiento, ciu_id, pac_correo_electronico, nac_id, pac_telefono, pac_direccion, seg_id, pac_hijos, pac_estado_civil, edu_id, sitlab_id, pac_latitud, pac_longitud, pac_creacion_usuario, pac_creacion_fecha, pac_creacion_hora, pac_modificacion_usuario, pac_modificacion_fecha, pac_modificacion_hora FROM pacientes WHERE pac_codigo_paciente="+codigo_paciente+";";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            if(cursor.moveToFirst()) {
                obj.setPacCodigoPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));
                obj.setPacTipoDocumento(cursor.getString(cursor.getColumnIndex("pac_tipo_documento")));
                obj.setPacNombres(cursor.getString(cursor.getColumnIndex("pac_nombres")));
                obj.setPacApellidos(cursor.getString(cursor.getColumnIndex("pac_apellidos")));
                obj.setPacSexo(cursor.getString(cursor.getColumnIndex("pac_sexo")));
                obj.setPacFechaNac(cursor.getString(cursor.getColumnIndex("pac_fechanac")));
                obj.setPacLugarNacimiento(cursor.getString(cursor.getColumnIndex("pac_lugar_nacimiento")));
                obj.setCiuId(cursor.getInt(cursor.getColumnIndex("ciu_id")));
                obj.setPacCorreoElectronico(cursor.getString(cursor.getColumnIndex("pac_correo_electronico")));
                obj.setNacId(cursor.getInt(cursor.getColumnIndex("nac_id")));
                obj.setPacTelefono(cursor.getInt(cursor.getColumnIndex("pac_telefono")));
                obj.setPacDireccion(cursor.getString(cursor.getColumnIndex("pac_direccion")));
                obj.setSegId(cursor.getInt(cursor.getColumnIndex("seg_id")));
                obj.setPacHijos(cursor.getInt(cursor.getColumnIndex("pac_hijos")));
                obj.setPacEstadoCivil(cursor.getString(cursor.getColumnIndex("pac_estado_civil")));
                obj.setEduId(cursor.getInt(cursor.getColumnIndex("edu_id")));
                obj.setSitlabId(cursor.getInt(cursor.getColumnIndex("sitlab_id")));
                obj.setPacLatitud(cursor.getDouble(cursor.getColumnIndex("pac_latitud")));
                obj.setPacLongitud(cursor.getDouble(cursor.getColumnIndex("pac_longitud")));
                obj.setOperacion("SELECT");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<PacientePreconsultaDetalle> getPacientesPreconsulta() {
        ArrayList<PacientePreconsultaDetalle> list = new ArrayList<>();
        String querySQL = "SELECT p.pac_codigo_paciente, p.pac_nombres||' '||p.pac_apellidos paciente FROM pacientes p";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                PacientePreconsultaDetalle obj = new PacientePreconsultaDetalle();
                obj.setCedulaPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));
                obj.setNombrePaciente(cursor.getString(cursor.getColumnIndex("paciente")));
                list.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
