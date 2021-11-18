package com.sistemascorporativos.miappnueva.preconsulta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAdmitidoDetalle;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteAsignacionDto;
import com.sistemascorporativos.miappnueva.admision.entidades.PacienteDto;
import com.sistemascorporativos.miappnueva.admision.entidades.Profesional;
import com.sistemascorporativos.miappnueva.admision.entidades.Usuario;
import com.sistemascorporativos.miappnueva.db.ConexionDb;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.Pre_PacienteAdmitido;
import com.sistemascorporativos.miappnueva.preconsulta.entidades.Pre_PacienteAdmitidoDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PreconsultaDao extends ConexionDb {
    private Context context;

    public PreconsultaDao(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Pre_PacienteAdmitido actualizarPreConsulta(Pre_PacienteAdmitido paciente) {
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            // Revisar luego la creacion fecha se debe actualizar una sola vez
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("precon_creacion_fecha", LocalDate.now().toString());
            }
            values.put("precon_temperatura_corporal", paciente.getPrecon_temperatura_corporal());
            values.put("precon_presion_arterial", paciente.getPrecon_presion_arterial());
            values.put("precon_frecuencia_respiratoria", paciente.getPrecon_frecuencia_respiratoria());
            values.put("precon_pulso", paciente.getPrecon_pulso());
            values.put("precon_peso", paciente.getPrecon_peso());
            values.put("precon_talla", paciente.getPrecon_talla());
            values.put("precon_imc", paciente.getPrecon_imc());
            values.put("precon_saturacion", paciente.getPrecon_saturacion());
            values.put("precon_circunferencia_abdominal", paciente.getPrecon_circunferencia_abdominal());
            values.put("precon_motivo_consulta", paciente.getPrecon_motivo_consulta());
            values.put("precon_modificacion_usuario", paciente.getPrecon_modificacion_usuario());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("precon_modificacion_fecha", LocalDate.now().toString());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("precon_modificacion_hora", LocalTime.now().toString());
            }
            //Actualizar
            Integer id = db.update("preconsulta", values,"precon_codigo_establecimiento = ? AND pacasi_codigo_asignacion = ?", new String[]{ paciente.getCodigo_establecimiento(), paciente.getCodigo_asignacion() });
            if(id != null) {
                paciente.setOperacion("UPDATE-PRECONSULTA");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paciente;
    }


    public ArrayList<Pre_PacienteAdmitido> getPacientesAdmitidos() {
        ArrayList<Pre_PacienteAdmitido> list = new ArrayList<>();
        String querySQL = "SELECT pre.precon_codigo_establecimiento, pre.pacasi_codigo_asignacion, p.pac_codigo_paciente, p.pac_fechanac, p.pac_nombres ||' '|| p.pac_apellidos paciente, pre.precon_creacion_fecha, pre.precon_temperatura_corporal, pre.precon_presion_arterial, pre.precon_frecuencia_respiratoria, pre.precon_pulso, pre.precon_peso, pre.precon_talla, pre.precon_imc, pre.precon_saturacion, pre.precon_circunferencia_abdominal, pre.precon_motivo_consulta, pre.precon_creacion_usuario, pre.precon_creacion_hora, pre.precon_modificacion_usuario, pre.precon_modificacion_fecha, pre.precon_modificacion_hora, usu.usu_nombres || ' ' || usu.usu_apellidos medico FROM preconsulta pre LEFT JOIN paciente_asignacion pa ON pa.pacasi_codigo_establecimiento = pre.precon_codigo_establecimiento AND pa.pacasi_codigo_asignacion = pre.pacasi_codigo_asignacion LEFT JOIN pacientes p ON p.pac_codigo_paciente = pa.pac_codigo_paciente LEFT JOIN profesional prof ON prof.prof_codigo_medico = pa.med_id LEFT JOIN usuarios usu ON usu.usu_codigo_usuario = prof_codigo_medico";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                Pre_PacienteAdmitido obj = new Pre_PacienteAdmitido();
                obj.setCodigo_establecimiento(cursor.getString(cursor.getColumnIndex("precon_codigo_establecimiento")));
                obj.setCodigo_asignacion(cursor.getString(cursor.getColumnIndex("pacasi_codigo_asignacion")));
                obj.setCedulaPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));//cedula
                obj.setFechanac(cursor.getString(cursor.getColumnIndex("pac_fechanac")));
                obj.setNombrePaciente(cursor.getString(cursor.getColumnIndex("paciente")));
                obj.setConsultando("Consultando con el Dr. "+cursor.getString(cursor.getColumnIndex("medico")));
                obj.setPrecon_temperatura_corporal(cursor.getDouble(cursor.getColumnIndex("precon_temperatura_corporal")));
                obj.setPrecon_presion_arterial(cursor.getDouble(cursor.getColumnIndex("precon_presion_arterial")));
                obj.setPrecon_frecuencia_respiratoria(cursor.getDouble(cursor.getColumnIndex("precon_frecuencia_respiratoria")));
                obj.setPrecon_pulso(cursor.getDouble(cursor.getColumnIndex("precon_pulso")));
                obj.setPrecon_peso(cursor.getDouble(cursor.getColumnIndex("precon_peso")));
                obj.setPrecon_talla(cursor.getDouble(cursor.getColumnIndex("precon_talla")));
                obj.setPrecon_imc(cursor.getDouble(cursor.getColumnIndex("precon_imc")));
                obj.setPrecon_saturacion(cursor.getDouble(cursor.getColumnIndex("precon_saturacion")));
                obj.setPrecon_circunferencia_abdominal(cursor.getDouble(cursor.getColumnIndex("precon_circunferencia_abdominal")));
                obj.setPrecon_motivo_consulta(cursor.getString(cursor.getColumnIndex("precon_motivo_consulta")));
                list.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
