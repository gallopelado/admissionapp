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
        String querySQL = "SELECT * from preconsulta p \n" +
                "join paciente_asignacion pa on  p.precon_codigo_establecimiento = pa.pacasi_codigo_establecimiento and p.pacasi_codigo_asignacion = pa.pacasi_codigo_asignacion\n" +
                "join pacientes p2 on pa.pac_codigo_paciente = p2.pac_codigo_paciente \n" +
                "join seguro_medico sm  on pa.seg_id = sm.seg_id  WHERE P2.pac_codigo_paciente="+codigo_paciente+";";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            if(cursor.moveToFirst()) {
                obj.setPacCodigoPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));
                obj.setPacTipoDocumento(cursor.getString(cursor.getColumnIndex("pac_tipo_documento")));
                obj.setPacNombres(cursor.getString(cursor.getColumnIndex("pac_nombres")));
                obj.setPacApellidos(cursor.getString(cursor.getColumnIndex("pac_apellidos")));
                obj.setPacCorreoElectronico(cursor.getString(cursor.getColumnIndex("pac_correo_electronico")));
                obj.setPacTelefono(cursor.getInt(cursor.getColumnIndex("pac_telefono")));
                obj.setPacDireccion(cursor.getString(cursor.getColumnIndex("pac_direccion")));
                obj.setPacEstadoCivil(cursor.getString(cursor.getColumnIndex("pac_estado_civil")));
                obj.setOperacion("SELECT");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<PacientePreconsultaDetalle> getPacientesPreconsulta() {
        ArrayList<PacientePreconsultaDetalle> listaPacientes = new ArrayList<>();
        String querySQL = "SELECT p.pac_codigo_paciente, p.pac_nombres||' '||p.pac_apellidos paciente FROM pacientes p";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                PacientePreconsultaDetalle obj = new PacientePreconsultaDetalle();
                obj.setCedulaPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));
                obj.setNombrePaciente(cursor.getString(cursor.getColumnIndex("paciente")));
                listaPacientes.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listaPacientes;
    }
}
