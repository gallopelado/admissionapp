package com.sistemascorporativos.miappnueva.consulta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sistemascorporativos.miappnueva.admision.entidades.Especialidad;
import com.sistemascorporativos.miappnueva.consulta.entidades.Cie;
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

    public PacienteDto actualizarPacienteOtrosDatos(PacienteDto pacienteDto){
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();) {
            ContentValues values = new ContentValues();
            values.put("con_motivo_consulta", pacienteDto.getMotivoConsulta());
            values.put("con_historial_actual", pacienteDto.getHistorialConsulta());
            values.put("con_evolucion", pacienteDto.getEvolucionConsulta());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("con_modificacion_fecha", LocalDate.now().toString());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                values.put("con_modificacion_hora", LocalTime.now().toString());
            }
            //Actualizar
            Integer id = db.update("consulta", values,"con_codigo_establecimiento = ? AND pacasi_codigo_asignacion = ?", new String[]{ pacienteDto.getCodigoEstablecimiento(), pacienteDto.getCodigoPreconsulta() });
            if(id != null) {
                pacienteDto.setOperacion("DATOS GUARDADOS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacienteDto;
    }

    public PacienteDto getDatosPacienteByCodigoPaciente(String codigo_paciente) {
        PacienteDto obj = new PacienteDto();
        String querySQL = "SELECT *,p.pacasi_codigo_asignacion,p.precon_codigo_establecimiento,p2.pac_codigo_paciente, p2.pac_nombres||' '||p2.pac_apellidos paciente \n" +
                "from consulta c \n" +
                "join preconsulta p on p.pacasi_codigo_asignacion = c.pacasi_codigo_asignacion and p.precon_codigo_establecimiento  = c.con_codigo_establecimiento \n" +
                "left join paciente_asignacion pa on  p.precon_codigo_establecimiento = pa.pacasi_codigo_establecimiento and p.pacasi_codigo_asignacion = pa.pacasi_codigo_asignacion\n" +
                "left join pacientes p2 on pa.pac_codigo_paciente = p2.pac_codigo_paciente\n" +
                "left join seguro_medico sm  on pa.seg_id = sm.seg_id  WHERE P2.pac_codigo_paciente="+codigo_paciente+" limit 1;";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            if(cursor.moveToFirst()) {
                obj.setMotivoConsulta(cursor.getString(cursor.getColumnIndex("con_motivo_consulta")));
                obj.setHistorialConsulta(cursor.getString(cursor.getColumnIndex("historial_actual")));
                obj.setEvolucionConsulta(cursor.getString(cursor.getColumnIndex("con_evolucion")));
                obj.setOperacion("SELECT");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<Cie> getCie() {
        ArrayList<Cie> listaCie = new ArrayList<>();
        String querySQL = "SELECT cie_id, cie_descripcion, cie_tipo from cie";
        try(
                ConexionDb conexionDb = new ConexionDb(context);
                SQLiteDatabase db = conexionDb.getWritableDatabase();
                Cursor cursorCie =  db.rawQuery(querySQL, null);

        ) {
            while(cursorCie.moveToNext()) {
                Cie obj = new Cie();
                obj.setCieId(cursorCie.getColumnIndex("cie_id"));
                obj.setCieDescripcion(cursorCie.getString(cursorCie.getColumnIndex("cie_descripcion")));
                obj.setCieTipo(cursorCie.getString(cursorCie.getColumnIndex("cie_tipo")));
                listaCie.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCie;
    }

    public ArrayList<PacientePreconsultaDetalle> getPacientesPreconsulta() {
        ArrayList<PacientePreconsultaDetalle> listaPacientes = new ArrayList<>();
        String querySQL = "SELECT *,p2.pac_telefono,p.pacasi_codigo_asignacion,p.precon_codigo_establecimiento,p2.pac_codigo_paciente, p2.pac_nombres||' '||p2.pac_apellidos paciente " +
                "from consulta c \n" +
                "join preconsulta p on p.pacasi_codigo_asignacion = c.pacasi_codigo_asignacion and p.precon_codigo_establecimiento  = c.con_codigo_establecimiento\n" +
                "join paciente_asignacion pa on  p.precon_codigo_establecimiento = pa.pacasi_codigo_establecimiento and p.pacasi_codigo_asignacion = pa.pacasi_codigo_asignacion\n" +
                "join pacientes p2 on pa.pac_codigo_paciente = p2.pac_codigo_paciente \n" +
                "left join seguro_medico sm  on pa.seg_id = sm.seg_id";
        try(ConexionDb conexionDb = new ConexionDb(context);SQLiteDatabase db = conexionDb.getWritableDatabase();Cursor cursor =  db.rawQuery(querySQL, null);) {
            while(cursor.moveToNext()) {
                PacientePreconsultaDetalle obj = new PacientePreconsultaDetalle();
                obj.setCedulaPaciente(cursor.getString(cursor.getColumnIndex("pac_codigo_paciente")));
                obj.setNombrePaciente(cursor.getString(cursor.getColumnIndex("paciente")));
                obj.setApellidoPaciente(cursor.getString(cursor.getColumnIndex("pac_apellidos")));
                obj.setCodigoPreconsulta(cursor.getString(cursor.getColumnIndex("pacasi_codigo_asignacion")));
                obj.setCodigoEstablecimiento(cursor.getString(cursor.getColumnIndex("precon_codigo_establecimiento")));
                obj.setMotivoConsulta(cursor.getString(cursor.getColumnIndex("con_motivo_consulta")));
                obj.setHistorialConsulta(cursor.getString(cursor.getColumnIndex("con_historial_actual")));
                obj.setEvolucionConsulta(cursor.getString(cursor.getColumnIndex("con_evolucion")));
                listaPacientes.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listaPacientes;
    }


}
